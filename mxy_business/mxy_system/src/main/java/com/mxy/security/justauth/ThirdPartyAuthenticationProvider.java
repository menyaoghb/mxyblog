package com.mxy.security.justauth;

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
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
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
 * 第三方统一登录认证逻辑
 */
@Slf4j
@Component
public class ThirdPartyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private SelfUserDetailsService selfUserDetailsService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisUtil redisUtil;
    @Resource
    private AuthUtil authUtil;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Random random = new Random();


    /**
     * 第三方统一登录认证逻辑
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ThirdPartyAuthenticationToken token = (ThirdPartyAuthenticationToken) authentication;
        String source = (String) token.getSource();
        AuthCallback callback = (AuthCallback) token.getCallback();
        log.info("------------进入" + source + "认证逻辑， callback params：" + JSONObject.toJSONString(callback));
        AuthRequest authRequest = authUtil.getAuthRequest(source);
        AuthResponse response = authRequest.login(callback);
        if (response.getCode() == 5000) {
            // 认证失败
            throw new BadCredentialsException(source + "认证失败");
        }
        AuthUser authUser = (AuthUser) response.getData();
        log.info("------------认证用户：{}", authUser);
        // 根据 uuid 查询用户信息
        SelfUserEntity userInfo = selfUserDetailsService.getUserInfoByUuid(authUser.getUuid());
        if (userInfo == null) {
            // 自动注册
            userInfo = doRegister(authUser);
        }
        if (Constants.USER_STATE_TWO.equals(userInfo.getStatus())) {
            LogUtil.saveLog("该账号已冻结[" + userInfo.getRelName() + "]", 99);
            throw new LockedException("该账号已冻结");
        }
        // 角色集合
        Set<GrantedAuthority> authorities = new HashSet<>();
        // 查询用户角色
        List<SysRole> sysRoleList = sysUserService.selectSysRoleByUserId(userInfo.getUserId());
        for (SysRole sysRole : sysRoleList) {
            authorities.add(new SimpleGrantedAuthority(sysRole.getRoleKey()));
        }
        userInfo.setAuthorities(authorities);
        ThirdPartyAuthenticationToken authenticationResult = new ThirdPartyAuthenticationToken(userInfo, userInfo.getUserId(), userInfo.getAuthorities());
        authenticationResult.setDetails(token.getDetails());
        return authenticationResult;
    }

    /**
     * 账号注册
     **/
    public SelfUserEntity doRegister(AuthUser authUser) {
        SelfUserEntity selfUser = new SelfUserEntity();
        SysUser sysUser = new SysUser();
        sysUser.setNickName(authUser.getNickname());
        sysUser.setUsername(authUser.getSource() + (random.nextInt(89999999) + 10000000));
        String password = String.valueOf(random.nextInt(899999) + 100000);
        sysUser.setPassword(bCryptPasswordEncoder.encode(password));
        sysUser.setAvatar(authUser.getAvatar());
        sysUser.setRegistrationType(authUser.getSource());
        sysUser.setUuid(authUser.getUuid());
        sysUser.setLoginCount(0);
        sysUser.setIpSource(IPUtils.getClientIp(Objects.requireNonNull(ServletUtils.getRequest())));
        // 2-男
        sysUser.setSex("2".equals(authUser.getRawUserInfo().getString("gender_type")) ? "0" : "1");
        sysUser.setCreateUser("system");
        sysUser.setRemark(authUser.getSource() + "首次注册默认密码为：" + password);
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
        LogUtil.saveNoLoginLog("账号注册（" + authUser.getSource() + "）", JSONObject.toJSONString(sysUser), OperType.REGISTRATION.ordinal());
        return selfUser;
    }


    /**
     * 判断是上面 authenticate 方法的 authentication 参数，是哪种类型
     * Authentication 是个接口，实现类有很多，目前我们最熟悉的就是 ThirdPartyAuthenticationToken、UsernamePasswordAuthenticationToken
     * 很明显，我们只支持 ThirdPartyAuthenticationToken，因为它封装的是TOKEN OPENID
     *
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        // 如果参数是 QQAuthenticationToken 类型，返回true
        return (ThirdPartyAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
