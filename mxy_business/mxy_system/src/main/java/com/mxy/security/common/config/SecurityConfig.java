package com.mxy.security.common.config;

import com.mxy.common.log.enums.OperType;
import com.mxy.security.account.AccountAuthenticationFilter;
import com.mxy.security.common.util.ResultUtil;
import com.mxy.security.email.EmailAuthenticationProvider;
import com.mxy.security.email.EmailNumAuthenticationFilter;
import com.mxy.security.account.AccountAuthenticationProvider;
import com.mxy.security.security.UserPermissionEvaluator;
import com.mxy.security.security.handler.*;
import com.mxy.security.security.jwt.JWTAuthenticationTokenFilter;
import com.mxy.security.sms.PhoneAuthenticationProvider;
import com.mxy.security.sms.PhoneNumAuthenticationFilter;
import com.mxy.system.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * SpringSecurity配置类
 *
 * @Author Mxy
 * @CreateTime 2022/01/1 9:40
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启权限注解,默认是关闭的
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 自定义登录成功处理器
     */
    @Autowired
    private UserLoginSuccessHandler userLoginSuccessHandler;
    /**
     * 自定义登录失败处理器
     */
    @Autowired
    private UserLoginFailureHandler userLoginFailureHandler;
    /**
     * 自定义注销成功处理器
     */
    @Autowired
    private UserLogoutSuccessHandler userLogoutSuccessHandler;
    /**
     * 自定义暂无权限处理器
     */
    @Autowired
    private UserAuthAccessDeniedHandler userAuthAccessDeniedHandler;
    /**
     * 自定义未登录的处理器
     */
    @Autowired
    private UserAuthenticationEntryPointHandler userAuthenticationEntryPointHandler;
    /**
     * 自定义登录逻辑验证器
     */
    @Autowired
    private AccountAuthenticationProvider accountAuthenticationProvider;
    /**
     * 短信验证登录逻辑验证器
     */
    @Autowired
    private PhoneAuthenticationProvider phoneAuthenticationProvider;
    /**
     * 邮箱验证登录逻辑验证器
     */
    @Autowired
    private EmailAuthenticationProvider emailAuthenticationProvider;

    /**
     * 加密方式
     *
     * @Author Mxy
     * @CreateTime 2022/01/1 14:00
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 注入自定义PermissionEvaluator
     */
    @Bean
    public DefaultWebSecurityExpressionHandler userSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(new UserPermissionEvaluator());
        return handler;
    }

    /**
     * 配置登录验证逻辑
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        //这里可启用我们自己的登陆验证逻辑
        // 系统账号登录逻辑
        auth.authenticationProvider(accountAuthenticationProvider);
        // 手机号登录逻辑
        auth.authenticationProvider(phoneAuthenticationProvider);
        // 邮箱登录逻辑
        auth.authenticationProvider(emailAuthenticationProvider);
    }

    /**
     * 配置security的控制逻辑
     *
     * @Author Mxy
     * @CreateTime 2022/01/10 21:56
     * @Param http 请求
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 允许加载iframe
        http.headers().frameOptions().disable();
        http.authorizeRequests()
                // 不进行权限验证的请求或资源(从配置文件中读取)
                .antMatchers(JWTConfig.antMatchers.split(",")).permitAll()
                // 其他的需要登陆后才能访问
                .anyRequest().authenticated()
                .and()
                // 配置未登录自定义处理类
                .httpBasic().authenticationEntryPoint(userAuthenticationEntryPointHandler)
                .and()
                // 配置登录地址
                .formLogin()
                .loginProcessingUrl("/api/as/login")
                // 配置登录成功自定义处理类
                .successHandler(userLoginSuccessHandler)
                // 配置登录失败自定义处理类
                .failureHandler(userLoginFailureHandler)
                .and()
                // 配置登出地址
                .logout()
                .logoutUrl("/api/as/logout")
                // 配置用户登出自定义处理类
                .logoutSuccessHandler(userLogoutSuccessHandler)
                .and()
                // 配置没有权限自定义处理类
                .exceptionHandling().accessDeniedHandler(userAuthAccessDeniedHandler)
                .and()
                // 开启跨域
                .cors()
                .and()
                // 取消跨站请求伪造防护
                .csrf().disable();

        // 基于Token不需要session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 禁用缓存
        http.headers().cacheControl();
        // 添加JWT过滤器
        http.addFilter(new JWTAuthenticationTokenFilter(authenticationManager()));
        //把 账号认证过滤器 加到拦截器链中
        http.addFilterAfter(accountAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        //把 手机号认证过滤器 加到拦截器链中
        http.addFilterAfter(phoneNumAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        //把 邮箱认证过滤器 加到拦截器链中
        http.addFilterAfter(emailNumAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 账号登录认证过滤器
     */
    @Bean
    public AccountAuthenticationFilter accountAuthenticationFilter() throws Exception {
        AccountAuthenticationFilter filter = new AccountAuthenticationFilter();
        //认证使用
        filter.setAuthenticationManager(authenticationManagerBean());
        //设置登陆成功返回值是json
        filter.setAuthenticationSuccessHandler(userLoginSuccessHandler);
        //设置登陆失败返回值是json
        filter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                LogUtil.saveLog("账号登陆失败：" + exception.getMessage(), OperType.ERROR.ordinal());
                ResultUtil.responseJson(response, ResultUtil.resultCode(500, exception.getMessage()));
            }
        });
        return filter;
    }

    /**
     * 手机号认证过滤器
     */
    @Bean
    public PhoneNumAuthenticationFilter phoneNumAuthenticationFilter() throws Exception {
        PhoneNumAuthenticationFilter filter = new PhoneNumAuthenticationFilter();
        //认证使用
        filter.setAuthenticationManager(authenticationManagerBean());
        //设置登陆成功返回值是json
        filter.setAuthenticationSuccessHandler(userLoginSuccessHandler);
        //设置登陆失败返回值是json
        filter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                LogUtil.saveLog("手机登陆失败：" + exception.getMessage(), OperType.ERROR.ordinal());
                ResultUtil.responseJson(response, ResultUtil.resultCode(500, exception.getMessage()));
            }
        });
        return filter;
    }

    /**
     * 邮箱认证过滤器
     */
    @Bean
    public EmailNumAuthenticationFilter emailNumAuthenticationFilter() throws Exception {
        EmailNumAuthenticationFilter filter = new EmailNumAuthenticationFilter();
        //认证使用
        filter.setAuthenticationManager(authenticationManagerBean());
        //设置登陆成功返回值是json
        filter.setAuthenticationSuccessHandler(userLoginSuccessHandler);
        //设置登陆失败返回值是json
        filter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                LogUtil.saveLog("邮箱登陆失败：" + exception.getMessage(), OperType.ERROR.ordinal());
                ResultUtil.responseJson(response, ResultUtil.resultCode(500, exception.getMessage()));
            }
        });
        return filter;
    }

}