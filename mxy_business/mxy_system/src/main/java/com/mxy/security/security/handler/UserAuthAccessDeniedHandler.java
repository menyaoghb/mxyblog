package com.mxy.security.security.handler;

import com.mxy.common.log.enums.OperType;
import com.mxy.security.common.util.ResultUtil;
import com.mxy.system.utils.LogUtil;
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
        LogUtil.saveLog("未授权", OperType.ERROR.ordinal());
        ResultUtil.responseJson(response,ResultUtil.resultCode(403,"未授权"));
    }
}