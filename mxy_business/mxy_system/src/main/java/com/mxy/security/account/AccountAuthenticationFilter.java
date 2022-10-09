package com.mxy.security.account;

import com.mxy.security.sms.PhoneNumAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 模仿 UsernamePasswordAuthenticationFilter 获取前端传递的 账号、密码、key、验证码
 * <p>
 * 之前的 UsernamePasswordAuthenticationFilter 拦截的是 /user/login 请求，从json中获取用户名、密码
 * 参考 UsernamePasswordAuthenticationFilter 写一个过滤器，拦截账号登录接口/as/accountLogin
 */
public class AccountAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private String usernameParameter = "username";
    private String passwordParameter = "password";
    private String keyParameter = "key";
    private String captchaParameter = "captcha";
    private boolean postOnly = true;


    /**
     * 表示这个 Filter 拦截 /as/accountLogin 接口
     */
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/api/as/accountLogin", "POST");

    public AccountAuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !"POST".equals(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String username = this.obtainUsername(request);
            String password = this.obtainPassword(request);
            String key = this.obtainKey(request);
            String captcha = this.obtainCaptcha(request);
            if (username == null) {
                username = "";
            }
            if (password == null) {
                password = "";
            }
            if (key == null) {
                key = "";
            }
            if (captcha == null) {
                captcha = "";
            }

            username = username.trim();
            captcha = captcha.trim();
            AccountAuthenticationToken authRequest = new AccountAuthenticationToken(username, password, key, captcha);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    protected void setDetails(HttpServletRequest request, AccountAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }


    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(this.usernameParameter);
    }

    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(this.passwordParameter);
    }

    protected String obtainKey(HttpServletRequest request) {
        return request.getParameter(this.keyParameter);
    }

    protected String obtainCaptcha(HttpServletRequest request) {
        return request.getParameter(this.captchaParameter);
    }

    public void setUsernameParameter(String usernameParameter) {
        Assert.hasText(usernameParameter, "usernameParameter parameter must not be empty or null");
        this.usernameParameter = usernameParameter;
    }

    public void setPasswordParameter(String passwordParameter) {
        Assert.hasText(passwordParameter, "passwordParameter parameter must not be empty or null");
        this.passwordParameter = passwordParameter;
    }

    public void setKeyParameter(String keyParameter) {
        Assert.hasText(keyParameter, "keyParameter parameter must not be empty or null");
        this.keyParameter = keyParameter;
    }

    public void setCaptchaParameter(String captchaParameter) {
        Assert.hasText(captchaParameter, "captchaParameter parameter must not be empty or null");
        this.captchaParameter = captchaParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getUsernameParameter() {
        return this.usernameParameter;
    }

    public final String getPasswordParameter() {
        return this.passwordParameter;
    }

    public final String getKeyParameter() {
        return this.keyParameter;
    }

    public final String getCaptchaParameter() {
        return this.captchaParameter;
    }
}
