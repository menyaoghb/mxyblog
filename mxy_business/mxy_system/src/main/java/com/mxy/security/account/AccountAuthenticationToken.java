package com.mxy.security.account;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 模仿 UsernamePasswordAuthenticationToken
 * 用来封装前端传过来的账号、密码、key、验证码
 */
public class AccountAuthenticationToken extends AbstractAuthenticationToken {

    /**
     * 账号
     */
    private final Object username;

    /**
     * 密码
     */
    private final Object password;

    /**
     * redis验证码key
     */
    private final Object key;

    /**
     * 验证码
     */
    private final Object captcha;


    public AccountAuthenticationToken(Object username, Object password, Object key, Object captcha) {
        super(null);
        this.username = username;
        this.password = password;
        this.key = key;
        this.captcha = captcha;
        setAuthenticated(false);
    }

    public AccountAuthenticationToken(Object username, Object password, Object key, Object captcha, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.username = username;
        this.password = password;
        this.key = key;
        this.captcha = captcha;
        // must use super, as we override
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return username;
    }

    @Override
    public Object getPrincipal() {
        return password;
    }

    public Object getKey() {
        return key;
    }

    public Object getCaptcha() {
        return captcha;
    }
}