package com.mxy.system.controller;

import com.mxy.common.core.utils.ServiceResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 模拟登录
 * @Author 孟小耀
 * @Date 2021/7/22 0022 19:35
 */
@Api(value = "模拟登录", tags = "模拟登录")
@RestController
@CrossOrigin
@RequestMapping("/system")
public class LoginController {


    @ApiOperation(value = "用户登录", notes = "用户登录")
    @PostMapping("/login")
    public String login() {
        Map map = new HashMap();
        map.put("token", "admin");
        return ServiceResult.success(map);
    }

    @ApiOperation(value = "用户信息", notes = "用户信息")
    @GetMapping("/info")
    public String info() {
        Map map = new HashMap();
        map.put("roles", "[admin]");
        map.put("name", "admin");
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return ServiceResult.success(map);
    }

    @ApiOperation(value = "用户登出", notes = "用户登出")
    @PostMapping("/logout")
    public String logout() {
        return ServiceResult.success();
    }
}
