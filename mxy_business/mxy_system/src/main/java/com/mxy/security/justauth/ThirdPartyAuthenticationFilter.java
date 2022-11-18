package com.mxy.security.justauth;

import me.zhyd.oauth.model.AuthCallback;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 第三方登录-拦截器
 * 拼接入参
 */
public class ThirdPartyAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static String EXTEND_LOGIN_URL = "/api/login/callback/**";

    private boolean getOnly = true;

    /**
     * 表示这个 Filter 拦截 /api/login/callback/** 接口
     */
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher(EXTEND_LOGIN_URL, "GET");

    public ThirdPartyAuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.getOnly && !"GET".equals(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            ThirdPartyAuthenticationToken authRequest = new ThirdPartyAuthenticationToken(getSourceType(request), getCallback(request));
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    protected void setDetails(HttpServletRequest request, ThirdPartyAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    /**
     * 组装请求
     */
    private AuthCallback getCallback(HttpServletRequest request) {
        return AuthCallback.builder()
                .code(request.getParameter("code"))
                .auth_code(request.getParameter("auth_code"))
                .authorization_code(request.getParameter("authorization_code"))
                .oauth_token(request.getParameter("oauth_token"))
                .state(request.getParameter("state"))
                .oauth_verifier(request.getParameter("oauth_verifier"))
                .build();
    }

    /**
     * 判断-登录系统类型
     */
    private String getSourceType(HttpServletRequest request) {
        String uri = request.getRequestURI();
        int common = EXTEND_LOGIN_URL.length() - 2;
        int start = uri.indexOf(EXTEND_LOGIN_URL.substring(0, common));
        return uri.substring(start + common);
    }

}
