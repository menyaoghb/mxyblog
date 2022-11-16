package com.mxy.security.qq;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 模仿 UsernamePasswordAuthenticationToken
 * 用来封装前端传过来的token、openid
 */
public class QQAuthenticationToken extends AbstractAuthenticationToken {

    /**
     * TOKEN
     */
    private final Object access_token;

    /**
     * openid
     */
    private final Object openid;


    public QQAuthenticationToken(Object access_token, Object openid) {
        super(null);
        this.access_token = access_token;
        this.openid = openid;
        setAuthenticated(false);
    }

    public QQAuthenticationToken(Object access_token, Object openid, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.access_token = access_token;
        this.openid = openid;
        // must use super, as we override
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return openid;
    }

    @Override
    public Object getPrincipal() {
        return access_token;
    }
}