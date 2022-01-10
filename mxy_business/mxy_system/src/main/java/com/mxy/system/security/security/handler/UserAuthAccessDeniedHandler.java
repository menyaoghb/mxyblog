package com.mxy.system.security.security.handler;

import com.mxy.system.security.common.util.ResultUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description 暂无权限处理类
 * @Author Mxy
 * @CreateTime 2022/01/10 21:20
 */
@Component
public class UserAuthAccessDeniedHandler implements AccessDeniedHandler{
    /**
     * 暂无权限返回结果
     * @Author Mxy
     * @CreateTime 2022/01/10 21:20
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception){
        ResultUtil.responseJson(response,ResultUtil.resultCode(403,"未授权"));
    }
}