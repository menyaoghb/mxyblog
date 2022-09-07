package com.mxy.security.email;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 模仿 UsernamePasswordAuthenticationToken
 * 用来封装前端传过来的邮箱、验证码
 */
public class EmailNumAuthenticationToken extends AbstractAuthenticationToken {

    /**
     * 手机号
     */
    private final Object email;

    /**
     * 验证码
     */
    private final Object code;


    public EmailNumAuthenticationToken(Object email, Object code) {
        super(null);
        this.email = email;
        this.code = code;
        setAuthenticated(false);
    }

    public EmailNumAuthenticationToken(Object email, Object code, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.email = email;
        this.code = code;
        // must use super, as we override
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return code;
    }

    @Override
    public Object getPrincipal() {
        return email;
    }
}