package com.mxy.security.sms;

import com.mxy.common.core.constant.Constants;
import com.mxy.common.core.entity.SelfUserEntity;
import com.mxy.common.core.entity.SysRole;
import com.mxy.common.core.entity.SysUser;
import com.mxy.common.core.entity.SysUserRole;
import com.mxy.common.core.utils.RedisUtil;
import com.mxy.security.security.service.SelfUserDetailsService;
import com.mxy.system.service.SysUserService;
import com.mxy.system.utils.LogUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * 主要实现 authenticate 方法，编写认证逻辑
 */
@Component
public class PhoneAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private SelfUserDetailsService selfUserDetailsService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisUtil redisUtil;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Random random = new Random();

    private static final String LONG_ERROR_COUNT_KEY = "error:login_error_count:";

    private static final String MESSAGE_LIMIT = "messager_limit:";
    private static final String SEND_LIMIT = "sendmsg_limit_count:";
    private static final String PHONE_NO = "message_phone_no:";

    /**
     * 手机号、验证码的认证逻辑
     *
     * @param authentication 其实就是我们封装的 PhoneNumAuthenticationToken
     * @return
     * @throws
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PhoneNumAuthenticationToken token = (PhoneNumAuthenticationToken) authentication;
        // 获取手机号
        String phoneNo = (String) authentication.getPrincipal();
        // 获取输入的验证码
        String code = (String) authentication.getCredentials();

        // 电话号码不能为空
        if (StringUtils.isEmpty(phoneNo)) {
            recordErrorNum(phoneNo);
            throw new BadCredentialsException("手机号不能为空");
        }
        // 电话长度校验
        if (!checkStringLength(phoneNo, 11)) {
            recordErrorNum(phoneNo);
            throw new BadCredentialsException("手机号长度不对");
        }
        String errorKey = LONG_ERROR_COUNT_KEY + phoneNo;
        // 失败次数的限制
        if (redisUtil.hasKey(errorKey) && (int) redisUtil.get(errorKey) >= 5) {
            throw new BadCredentialsException("错误次数超过5次，请10分钟后再登录");
        }
        // 手机验证码长度不对
        if (!checkStringLength(code, 6)) {
            recordErrorNum(phoneNo);
            throw new BadCredentialsException("短信验证码长度不对");
        }
        // 手机验证码已过期
        if (!redisUtil.hasKey(PHONE_NO + phoneNo)) {
            recordErrorNum(phoneNo);
            throw new BadCredentialsException("短信验证码已过期");
        }
        // 短信验证码不正确
        if (!code.equals(redisUtil.get(PHONE_NO + phoneNo))) {
            recordErrorNum(phoneNo);
            throw new BadCredentialsException("短信验证码不正确");
        }

        // 根据手机号查询用户信息
        SelfUserEntity userInfo = selfUserDetailsService.loadUserByUsername(phoneNo);
        if (userInfo == null) {
            // 自动注册
            userInfo = doRegister(phoneNo);
        }
        if (Constants.USER_STATE_TWO.equals(userInfo.getStatus())) {
            recordErrorNum(phoneNo);
            LogUtil.saveLog("用户已冻结[" + phoneNo + "]", 99);
            throw new LockedException("用户已冻结");
        }
        // 角色集合
        Set<GrantedAuthority> authorities = new HashSet<>();
        // 查询用户角色
        List<SysRole> sysRoleList = sysUserService.selectSysRoleByUserId(userInfo.getUserId());
        for (SysRole sysRole : sysRoleList) {
            authorities.add(new SimpleGrantedAuthority(sysRole.getRoleKey()));
        }
        userInfo.setAuthorities(authorities);
        redisUtil.del(PHONE_NO + phoneNo);

        // 把用户封装到 PhoneNumAuthenticationToken 中，
        // 后面就可以使用 SecurityContextHolder.getContext().getAuthentication(); 获取当前登陆用户信息
        PhoneNumAuthenticationToken authenticationResult = new PhoneNumAuthenticationToken(userInfo, code, userInfo.getAuthorities());
        authenticationResult.setDetails(token.getDetails());
        return authenticationResult;
    }

    /**
     * @description: 登录注册
     * @author: meng.yao
     * @date: 2022/9/3 13:59
     **/
    public SelfUserEntity doRegister(String phoneNo) {
        SelfUserEntity selfUser = new SelfUserEntity();
        SysUser sysUser = new SysUser();
        sysUser.setNickName(phoneNo);
        sysUser.setUsername(phoneNo);
        sysUser.setPhoneNumber(phoneNo);
        String password = String.valueOf(random.nextInt(899999) + 100000);
        sysUser.setPassword(bCryptPasswordEncoder.encode(password));
        sysUser.setAvatar("http://mxy.mxyit.com/4c91b381-c107-4359-909a-e0b40b960b74");
        sysUser.setCreateUser("system");
        sysUser.setRemark("默认密码为：" + password);
        sysUser.setLoginDate(new Date());
        sysUser.insert();

        // 新增用户角色关系
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(sysUser.getUserId());
        // 游客
        sysUserRole.setRoleId("1483686913015730177");
        sysUserRole.insert();
        BeanUtils.copyProperties(sysUser, selfUser);
        selfUser.setRelName(sysUser.getNickName());
        return selfUser;
    }

    /**
     * 判断字符串长度是否大于等于指定值
     *
     * @return Boolean true:通过验证 false:未通过验证
     */
    public static Boolean checkStringLength(String param, int length) {
        return param.length() >= length;
    }


    /**
     * 记录失败次数做拦截
     *
     * @param phone
     */
    public void recordErrorNum(String phone) {
        String errorKey = LONG_ERROR_COUNT_KEY + phone;
        int count = (int) (redisUtil.get(errorKey) == null ? 0 : redisUtil.get(errorKey));
        // 失败5次 过期时间为10 分钟
        redisUtil.set(errorKey, count + 1, 600);
    }

    /**
     * 判断是上面 authenticate 方法的 authentication 参数，是哪种类型
     * Authentication 是个接口，实现类有很多，目前我们最熟悉的就是 PhoneNumAuthenticationToken、UsernamePasswordAuthenticationToken
     * 很明显，我们只支持 PhoneNumAuthenticationToken，因为它封装的是手机号、验证码
     *
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        // 如果参数是 PhoneNumAuthenticationToken 类型，返回true
        return (PhoneNumAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
