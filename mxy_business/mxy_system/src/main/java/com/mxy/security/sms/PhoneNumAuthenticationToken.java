package com.mxy.security.sms;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 模仿 UsernamePasswordAuthenticationToken
 * 用来封装前端传过来的手机号、验证码
 */
public class PhoneNumAuthenticationToken extends AbstractAuthenticationToken {

    /**
     * 手机号
     */
    private final Object phone;

    /**
     * 验证码
     */
    private final Object code;


    public PhoneNumAuthenticationToken(Object phone, Object code) {
        super(null);
        this.phone = phone;
        this.code = code;
        setAuthenticated(false);
    }

    public PhoneNumAuthenticationToken(Object phone, Object code, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.phone = phone;
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
        return phone;
    }
}