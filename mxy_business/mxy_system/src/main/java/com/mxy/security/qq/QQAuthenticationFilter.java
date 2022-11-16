package com.mxy.security.qq;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 模仿 UsernamePasswordAuthenticationFilter 获取前端传递的 token、openid
 * <p>
 * 之前的 UsernamePasswordAuthenticationFilter 拦截的是 /user/login 请求，从json中获取用户名、密码
 * 参考 UsernamePasswordAuthenticationFilter 写一个过滤器，拦截QQ登录接口
 */
public class QQAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private String accessTokenParameter = "access_token";
    private String openIdParameter = "openid";
    private boolean getOnly = true;


    /**
     * 表示这个 Filter 拦截 /api/qqLogin/callback 接口
     */
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/api/qqLogin/callback", "GET");

    public QQAuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.getOnly && !"GET".equals(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String token = this.obtainToken(request);
            String openId = this.obtainOpenId(request);
            if (token == null) {
                token = "";
            }

            if (openId == null) {
                openId = "";
            }

            token = token.trim();
            QQAuthenticationToken authRequest = new QQAuthenticationToken(token, openId);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    protected void setDetails(HttpServletRequest request, QQAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }


    protected String obtainOpenId(HttpServletRequest request) {
        return request.getParameter(this.openIdParameter);
    }

    protected String obtainToken(HttpServletRequest request) {
        return request.getParameter(this.accessTokenParameter);
    }

    public void setAccessTokenParameter(String accessTokenParameter) {
        Assert.hasText(accessTokenParameter, "access_token parameter must not be empty or null");
        this.accessTokenParameter = accessTokenParameter;
    }

    public void setOpenIdParameter(String openIdParameter) {
        Assert.hasText(openIdParameter, "openid parameter must not be empty or null");
        this.openIdParameter = openIdParameter;
    }

    public void setGetOnly(boolean getOnly) {
        this.getOnly = getOnly;
    }

    public final String getAccessTokenParameter() {
        return this.accessTokenParameter;
    }

    public final String getOpenIdParameter() {
        return this.openIdParameter;
    }
}
