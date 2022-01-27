package com.mxy.system.security.security.handler;

import com.mxy.common.log.enums.OperType;
import com.mxy.system.security.common.util.ResultUtil;
import com.mxy.system.utils.LogUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 登出成功处理类
 * @Author Mxy
 * @CreateTime 2022/01/10 21:20
 */
@Component
public class UserLogoutSuccessHandler implements LogoutSuccessHandler {
    /**
     * 用户登出返回结果
     * 这里应该让前端清除掉Token
     * @Author Mxy
     * @CreateTime 2022/01/10 21:20
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        Map<String,Object> resultData = new HashMap<>();
        resultData.put("code","200");
        resultData.put("msg", "注销成功");
        SecurityContextHolder.clearContext();
        LogUtil.saveLog("注销", OperType.LOGOUT.ordinal());
        ResultUtil.responseJson(response,ResultUtil.resultSuccess(resultData));
    }
}