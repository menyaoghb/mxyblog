package com.mxy.security.security.handler;

import com.mxy.common.core.entity.SelfUserEntity;
import com.mxy.common.core.entity.SysUser;
import com.mxy.common.core.utils.IPUtils;
import com.mxy.common.log.enums.OperType;
import com.mxy.security.common.config.JWTConfig;
import com.mxy.security.common.util.JWTTokenUtil;
import com.mxy.security.common.util.ResultUtil;
import com.mxy.security.common.util.SecurityUtil;
import com.mxy.system.utils.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 登录成功处理类
 * @Author Mxy
 * @CreateTime 2022/01/10 21:20
 */
@Slf4j
@Component
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {
    /**
     * 登录成功返回结果
     *
     * @Author Mxy
     * @CreateTime 2022/01/10 21:20
     */

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 组装JWT
        SelfUserEntity selfUserEntity = (SelfUserEntity) authentication.getPrincipal();
        String token = JWTTokenUtil.createAccessToken(selfUserEntity);
        token = JWTConfig.tokenPrefix + token;
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userDetails.getUserId());
        sysUser.setLoginDate(new Date());
        sysUser.setUpdateUser(userDetails.getUsername());
        sysUser.setValidCode(token);
        sysUser.setLoginCount(userDetails.getLoginCount() + 1);
        Map<String, String> map = IPUtils.getOsAndBrowserInfo(request);
        String os = map.get("OS");
        String browser = map.get("BROWSER");
        String ip = IPUtils.getClientIp(request);
        sysUser.setLoginIp(ip);
        sysUser.setOs(os);
        sysUser.setBrowser(browser);
        sysUser.updateById();
        // 封装返回参数
        Map<String, Object> resultData = new HashMap<>();
        resultData.put("code", "200");
        resultData.put("msg", "登录成功");
        resultData.put("data", userDetails);
        resultData.put("token", token);
        LogUtil.saveLog(userDetails.getRegistrationType() + "登录", OperType.LOGIN.ordinal());
        LogUtil.saveLoginLog(userDetails, userDetails.getRegistrationType(), "博客系统");
        ResultUtil.responseJson(response, resultData);
    }
}