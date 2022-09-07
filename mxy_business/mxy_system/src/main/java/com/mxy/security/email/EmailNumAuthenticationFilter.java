package com.mxy.security.email;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 模仿 UsernamePasswordAuthenticationFilter 获取前端传递的 邮箱、验证码
 * <p>
 * 之前的 UsernamePasswordAuthenticationFilter 拦截的是 /user/login 请求，从json中获取用户名、密码
 * 参考 UsernamePasswordAuthenticationFilter 写一个过滤器，拦截邮箱登录接口/email/login
 */
public class EmailNumAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private String emailParameter = "email";
    private String codeParameter = "code";
    private boolean postOnly = true;


    /**
     * 表示这个 Filter 拦截 /email/login 接口
     */
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/api/email/emailLogin", "POST");

    public EmailNumAuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !"POST".equals(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String email = this.obtainEmail(request);
            String code = this.obtainCode(request);
            if (email == null) {
                email = "";
            }

            if (code == null) {
                code = "";
            }

            email = email.trim();
            EmailNumAuthenticationToken authRequest = new EmailNumAuthenticationToken(email, code);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    protected void setDetails(HttpServletRequest request, EmailNumAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }


    protected String obtainCode(HttpServletRequest request) {
        return request.getParameter(this.codeParameter);
    }

    protected String obtainEmail(HttpServletRequest request) {
        return request.getParameter(this.emailParameter);
    }

    public void setEmailParameter(String emailParameter) {
        Assert.hasText(emailParameter, "Username parameter must not be empty or null");
        this.emailParameter = emailParameter;
    }

    public void setCodeParameter(String codeParameter) {
        Assert.hasText(codeParameter, "Password parameter must not be empty or null");
        this.codeParameter = codeParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getEmailParameter() {
        return this.emailParameter;
    }

    public final String getCodeParameter() {
        return this.codeParameter;
    }
}
