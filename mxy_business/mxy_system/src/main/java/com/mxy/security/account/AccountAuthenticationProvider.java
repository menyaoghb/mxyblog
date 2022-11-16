package com.mxy.security.account;

import com.mxy.common.core.constant.Constants;
import com.mxy.common.core.entity.SelfUserEntity;
import com.mxy.common.core.entity.SysRole;
import com.mxy.common.core.utils.RedisUtil;
import com.mxy.security.security.service.SelfUserDetailsService;
import com.mxy.system.service.SysUserService;
import com.mxy.system.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自定义登录验证
 *
 * @Author Mxy
 * @CreateTime 2022/01/1 19:11
 */
@Component
public class AccountAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private SelfUserDetailsService selfUserDetailsService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisUtil redisUtil;

    private static final String CAPTCHA_NO = "message_captcha_no:";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AccountAuthenticationToken info = (AccountAuthenticationToken) authentication;
        // 获取表单输入中返回的用户名
        String userName = (String) info.getCredentials();
        // 获取表单中输入的密码
        String password = (String) info.getPrincipal();

        String key = (String) info.getKey();

        String captcha = (String) info.getCaptcha();

        // 校验验证码
        if (!redisUtil.hasKey(CAPTCHA_NO + key)) {
            throw new BadCredentialsException("验证码已过期");
        }

        String verCode = (String) redisUtil.get(CAPTCHA_NO + key);
        // 验证码不正确
        if (!captcha.equals(verCode.trim().toLowerCase())) {
            throw new BadCredentialsException("验证码不正确");
        }

        // 查询用户是否存在
        SelfUserEntity userInfo = selfUserDetailsService.loadUserByUsername(userName);
        if (userInfo == null) {
            LogUtil.saveLog("用户名不存在[" + userName + "]", 99);
            throw new UsernameNotFoundException("用户名不存在");
        }
        // 我们还要判断密码是否正确，这里我们的密码使用BCryptPasswordEncoder进行加密的
        if (!new BCryptPasswordEncoder().matches(password, userInfo.getPassword())) {
            LogUtil.saveLog("密码不正确[u:" + userName + ",p:" + password + "]", 99);
            throw new BadCredentialsException("密码不正确");
        }
        // 还可以加一些其他信息的判断，比如用户账号已停用等判断
        if (Constants.USER_STATE_TWO.equals(userInfo.getStatus())) {
            LogUtil.saveLog("用户已冻结[" + userName + "]", 99);
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
        // 进行登录
        return new UsernamePasswordAuthenticationToken(userInfo, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 如果参数是 AccountAuthenticationToken 类型，返回true
        return (AccountAuthenticationToken.class.isAssignableFrom(authentication));
    }
}