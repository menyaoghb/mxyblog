package com.mxy.security.justauth;

import com.mxy.common.core.utils.ServiceResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * 第三方登录认证
 */
@RestController
@RequestMapping("/api/login")
@Api(value = "第三方登录相关接口", tags = {"第三方登录相关接口"})
@Slf4j
public class AuthRestApi {

    @Resource
    private AuthUtil authUtil;

    @ApiOperation(value = "系统认证", notes = "系统认证")
    @RequestMapping("/render")
    public String renderAuth(String source) {
        log.info("进入第三方认证:" + source);
        Map<String, String> map = new HashMap<>();
        AuthRequest authRequest = authUtil.getAuthRequest(source);
        if (authRequest == null) {
            map.put("code", "201");
            map.put("msg", "系统未开启该登录方式");
            return ServiceResult.success(map);
        }
        String token = AuthStateUtils.createState();
        String authorizeUrl = authRequest.authorize(token);
        log.info("获取返回url:" + authorizeUrl);
        map.put("code", "200");
        map.put("url", authorizeUrl);
        return ServiceResult.success(map);
    }

}
