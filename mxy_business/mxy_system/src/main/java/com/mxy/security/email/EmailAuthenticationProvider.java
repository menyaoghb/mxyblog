package com.mxy.security.email;

import com.alibaba.fastjson.JSONObject;
import com.mxy.common.core.constant.Constants;
import com.mxy.common.core.entity.SelfUserEntity;
import com.mxy.common.core.entity.SysRole;
import com.mxy.common.core.entity.SysUser;
import com.mxy.common.core.entity.SysUserRole;
import com.mxy.common.core.utils.IPUtils;
import com.mxy.common.core.utils.RedisUtil;
import com.mxy.common.core.utils.ServletUtils;
import com.mxy.common.log.enums.OperType;
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
public class EmailAuthenticationProvider implements AuthenticationProvider {

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

    private static final String EMAIL_MESSAGE_LIMIT = "email_messager_limit:";
    private static final String EMAIL_SEND_LIMIT = "email_sendmsg_limit_count:";
    private static final String EMAIL_NO = "message_email_no:";

    /**
     * 手机号、验证码的认证逻辑
     *
     * @param authentication 其实就是我们封装的 EmailNumAuthenticationToken
     * @return
     * @throws
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        EmailNumAuthenticationToken token = (EmailNumAuthenticationToken) authentication;
        // 获取邮箱
        String email = (String) authentication.getPrincipal();
        // 获取输入的验证码
        String code = (String) authentication.getCredentials();

        // 电话号码不能为空
        if (StringUtils.isEmpty(email)) {
            recordErrorNum(email);
            throw new BadCredentialsException("邮箱不能为空哦");
        }
//        // 电话长度校验
//        if (!checkStringLength(email, 11)) {
//            recordErrorNum(email);
//            throw new BadCredentialsException("手机号长度不对");
//        }
        String errorKey = LONG_ERROR_COUNT_KEY + email;
        // 失败次数的限制
        if (redisUtil.hasKey(errorKey) && (int) redisUtil.get(errorKey) >= 5) {
            throw new BadCredentialsException("错误次数超过5次，请10分钟后再登录哦");
        }
        // 手机验证码长度不对
        if (!checkStringLength(code, 6)) {
            recordErrorNum(email);
            throw new BadCredentialsException("短信验证码长度不对");
        }
        // 手机验证码已过期
        if (!redisUtil.hasKey(EMAIL_NO + email)) {
            recordErrorNum(email);
            throw new BadCredentialsException("短信验证码已过期");
        }
        // 短信验证码不正确
        if (!code.equals(redisUtil.get(EMAIL_NO + email))) {
            recordErrorNum(email);
            throw new BadCredentialsException("短信验证码不正确");
        }

        // 根据手机号查询用户信息
        SelfUserEntity userInfo = selfUserDetailsService.loadUserByUsername(email);
        if (userInfo == null) {
            // 自动注册
            userInfo = doRegister(email);
        }
        if (Constants.USER_STATE_TWO.equals(userInfo.getStatus())) {
            recordErrorNum(email);
            LogUtil.saveLog("用户已冻结[" + email + "]", 99);
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
        redisUtil.del(EMAIL_NO + email);

        // 把用户封装到 EmailNumAuthenticationToken 中，
        // 后面就可以使用 SecurityContextHolder.getContext().getAuthentication(); 获取当前登陆用户信息
        EmailNumAuthenticationToken authenticationResult = new EmailNumAuthenticationToken(userInfo, code, userInfo.getAuthorities());
        authenticationResult.setDetails(token.getDetails());
        return authenticationResult;
    }

    /**
     * @description: 登录注册
     * @author: meng.yao
     * @date: 2022/9/3 13:59
     **/
    public SelfUserEntity doRegister(String email) {
        SelfUserEntity selfUser = new SelfUserEntity();
        SysUser sysUser = new SysUser();
        sysUser.setNickName(email);
        sysUser.setUsername(email);
        sysUser.setEmail(email);
        String password = String.valueOf(random.nextInt(899999) + 100000);
        sysUser.setPassword(bCryptPasswordEncoder.encode(password));
        sysUser.setAvatar(Constants.DEFAULT_USER_AVATAR);
        sysUser.setRegistrationType(Constants.EMAIL);
        sysUser.setLoginCount(0);
        sysUser.setIpSource(IPUtils.getClientIp(Objects.requireNonNull(ServletUtils.getRequest())));
        sysUser.setCreateUser("system");
        sysUser.setRemark("首次注册默认密码为：" + password);
        sysUser.setLoginDate(new Date());
        sysUser.setUserType("2");
        sysUser.insert();

        // 新增用户角色关系
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(sysUser.getUserId());
        // 游客
        sysUserRole.setRoleId("2");
        sysUserRole.insert();
        BeanUtils.copyProperties(sysUser, selfUser);
        selfUser.setRelName(sysUser.getNickName());
        LogUtil.saveNoLoginLog("账号注册（邮箱）", JSONObject.toJSONString(sysUser), OperType.REGISTRATION.ordinal());
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
     * @param email
     */
    public void recordErrorNum(String email) {
        String errorKey = LONG_ERROR_COUNT_KEY + email;
        int count = (int) (redisUtil.get(errorKey) == null ? 0 : redisUtil.get(errorKey));
        // 失败5次 过期时间为10 分钟
        redisUtil.set(errorKey, count + 1, 600);
    }

    /**
     * 判断是上面 authenticate 方法的 authentication 参数，是哪种类型
     * Authentication 是个接口，实现类有很多，目前我们最熟悉的就是 EmailNumAuthenticationToken、UsernamePasswordAuthenticationToken
     * 很明显，我们只支持 EmailNumAuthenticationToken，因为它封装的是邮箱、验证码
     *
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        // 如果参数是 EmailNumAuthenticationToken 类型，返回true
        return (EmailNumAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
