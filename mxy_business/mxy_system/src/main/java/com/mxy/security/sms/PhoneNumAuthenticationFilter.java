package com.mxy.security.sms;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 模仿 UsernamePasswordAuthenticationFilter 获取前端传递的 手机号、验证码
 * <p>
 * 之前的 UsernamePasswordAuthenticationFilter 拦截的是 /user/login 请求，从json中获取用户名、密码
 * 参考 UsernamePasswordAuthenticationFilter 写一个过滤器，拦截短信登录接口/phone/login
 */
public class PhoneNumAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private String phoneParameter = "phone";
    private String codeParameter = "code";
    private boolean postOnly = true;


    /**
     * 表示这个 Filter 拦截 /phone/login 接口
     */
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/api/phone/phoneLogin", "POST");

    public PhoneNumAuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !"POST".equals(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String phone = this.obtainPhone(request);
            String code = this.obtainCode(request);
            if (phone == null) {
                phone = "";
            }

            if (code == null) {
                code = "";
            }

            phone = phone.trim();
            PhoneNumAuthenticationToken authRequest = new PhoneNumAuthenticationToken(phone, code);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    protected void setDetails(HttpServletRequest request, PhoneNumAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }


    protected String obtainCode(HttpServletRequest request) {
        return request.getParameter(this.codeParameter);
    }

    protected String obtainPhone(HttpServletRequest request) {
        return request.getParameter(this.phoneParameter);
    }

    public void setPhoneParameter(String phoneParameter) {
        Assert.hasText(phoneParameter, "Username parameter must not be empty or null");
        this.phoneParameter = phoneParameter;
    }

    public void setCodeParameter(String codeParameter) {
        Assert.hasText(codeParameter, "Password parameter must not be empty or null");
        this.codeParameter = codeParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getPhoneParameter() {
        return this.phoneParameter;
    }

    public final String getCodeParameter() {
        return this.codeParameter;
    }
}
