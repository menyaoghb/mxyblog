package com.mxy.security.qq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mxy.common.core.constant.Constants;
import com.mxy.common.core.entity.SelfUserEntity;
import com.mxy.common.core.entity.SysRole;
import com.mxy.common.core.entity.SysUser;
import com.mxy.common.core.entity.SysUserRole;
import com.mxy.common.core.utils.RedisUtil;
import com.mxy.common.log.enums.OperType;
import com.mxy.security.entity.QQUserInfo;
import com.mxy.security.security.service.SelfUserDetailsService;
import com.mxy.system.service.SysUserService;
import com.mxy.system.utils.HttpClientUtils;
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
public class QQAuthenticationProvider implements AuthenticationProvider {

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
     * QQ认证逻辑
     *
     * @param authentication 封装的 QQAuthenticationToken
     * @return
     * @throws
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        QQAuthenticationToken token = (QQAuthenticationToken) authentication;
        // token
        String accessToken = (String) authentication.getPrincipal();
        // openId
        String openId = (String) authentication.getCredentials();
        if (StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(openId)) {
            throw new BadCredentialsException("token或openId不可为空哦");
        }

        // 拼接url
        StringBuilder url = new StringBuilder("https://graph.qq.com/user/get_user_info?");
        url.append("access_token=").append(accessToken);
        // appid
        url.append("&oauth_consumer_key=" + "102031428");
        url.append("&openid=").append(openId);

        // 获取qq相关数据
        String result = null;
        try {
            result = HttpClientUtils.get(url.toString(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadCredentialsException("接口异常");
        }
        QQUserInfo json = JSON.parseObject(result, QQUserInfo.class);
        System.out.println("qq登录用户：" + String.valueOf(json));
        if (json.getRet() != 0) {
            throw new LockedException(json.getMsg());
        }
        // 根据 openId 查询用户信息
        SelfUserEntity userInfo = selfUserDetailsService.loadUserByUsername(openId);
        if (userInfo == null) {
            // 自动注册
            userInfo = doRegister(json);
        }
        if (Constants.USER_STATE_TWO.equals(userInfo.getStatus())) {
            LogUtil.saveLog("用户已冻结[" + openId + "]", 99);
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

        // 把用户封装到 QQAuthenticationToken 中，
        // 后面就可以使用 SecurityContextHolder.getContext().getAuthentication(); 获取当前登陆用户信息
        QQAuthenticationToken authenticationResult = new QQAuthenticationToken(userInfo, openId, userInfo.getAuthorities());
        authenticationResult.setDetails(token.getDetails());
        return authenticationResult;
    }

    /**
     * @description: 登录注册
     * @author: meng.yao
     * @date: 2022/9/3 13:59
     **/
    public SelfUserEntity doRegister(QQUserInfo userInfo) {
        SelfUserEntity selfUser = new SelfUserEntity();
        SysUser sysUser = new SysUser();
        sysUser.setNickName(userInfo.getNickname());
        sysUser.setUsername(userInfo.getOpenId());
        String password = String.valueOf(random.nextInt(899999) + 100000);
        sysUser.setPassword(bCryptPasswordEncoder.encode(password));
        sysUser.setAvatar(userInfo.getFigureurl_qq_1());
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
        LogUtil.saveNoLoginLog("账号注册（QQ）", JSONObject.toJSONString(sysUser), OperType.REGISTRATION.ordinal());
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
     * Authentication 是个接口，实现类有很多，目前我们最熟悉的就是 QQAuthenticationToken、UsernamePasswordAuthenticationToken
     * 很明显，我们只支持 QQAuthenticationToken，因为它封装的是TOKEN OPENID
     *
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        // 如果参数是 QQAuthenticationToken 类型，返回true
        return (QQAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
