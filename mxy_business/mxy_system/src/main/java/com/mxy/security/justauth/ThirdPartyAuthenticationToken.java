package com.mxy.security.justauth;

import me.zhyd.oauth.model.AuthCallback;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 模仿 UsernamePasswordAuthenticationToken
 * 封装用户信息
 */
public class ThirdPartyAuthenticationToken extends AbstractAuthenticationToken {

    /**
     * 认证返回
     */
    private AuthCallback callback;
    /**
     * 登录类型
     */
    private String source;
    /**
     * 用户实体
     */
    private Object principal;
    /**
     * 用户id
     */
    private Object credentials;

    public ThirdPartyAuthenticationToken(String source, AuthCallback callback) {
        super(null);
        this.source = source;
        this.callback = callback;
        setAuthenticated(false);
    }

    public ThirdPartyAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    public Object getCallback() {
        return callback;
    }

    public Object getSource() {
        return source;
    }
}