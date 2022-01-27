package com.mxy.system.security.security.handler;

import com.mxy.common.log.enums.OperType;
import com.mxy.system.security.common.util.ResultUtil;
import com.mxy.system.utils.LogUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户未登录处理类
 * @Author Mxy
 * @CreateTime 2022/01/10 21:20
 */
@Component
public class UserAuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    /**
     * 用户未登录返回结果
     * @Author Mxy
     * @CreateTime 2022/01/10 21:20
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception){
        LogUtil.saveLog("未登录", OperType.ERROR.ordinal());
        ResultUtil.responseJson(response,ResultUtil.resultCode(401,"未登录"));
    }
}