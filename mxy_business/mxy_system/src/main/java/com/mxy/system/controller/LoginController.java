package com.mxy.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mxy.common.core.utils.ServiceResult;
import com.mxy.common.log.annotation.SysLog;
import com.mxy.common.core.entity.SysUser;
import com.mxy.common.log.enums.OperType;
import com.mxy.system.entity.vo.SysUserVO;
import com.mxy.system.service.impl.SysUserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 模拟登录
 * @Author 孟小耀
 * @Date 2021/7/22  19:35
 */
@Api(value = "模拟登录", tags = "模拟登录")
@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/system")
public class LoginController {

    @Autowired
    SysUserServiceImpl sysUserService;

    /**
     * @Description: 登录
     * @Param: [sysUserVO]
     * @return: java.lang.String
     * @Author: 孟耀
     * @Date: 2021/8/5
     */
    @SysLog(module = "用户登录",operType = OperType.LOGIN)
    @ApiOperation(value = "用户登录", notes = "用户登录")
    @PostMapping("/login")
    public String login(@RequestBody SysUserVO sysUserVO) {
        String userName = sysUserVO.getUserName();
        String password = sysUserVO.getPassword();
        // 查询用户是否存在
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);
        queryWrapper.eq("password", password);
        SysUser sysUser = sysUserService.getOne(queryWrapper);
        if (sysUser == null) {
            return ServiceResult.error();
        }
        Map map = new HashMap();
        map.put("token", "admin");
        map.put("name", userName);
        log.info("登录成功");
        return ServiceResult.success(map);
    }

    /**
     * @Description: 获取用户信息
     * @Param: [token, name]
     * @return: java.lang.String
     * @Author: 孟耀
     * @Date: 2021/8/5
     */
    @SysLog(module = "用户信息",operType = OperType.SELECT)
    @ApiOperation(value = "用户信息", notes = "用户信息")
    @GetMapping("/info")
    public String info(@RequestParam(value = "token") String token,@RequestParam(value = "name") String name) {
        // 查询用户个人信息
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", name);
        SysUser sysUser = sysUserService.getOne(queryWrapper);
        Map map = new HashMap();
        map.put("roles", "[admin]");
        map.put("name", name);
        map.put("userId", sysUser.getUserId());
        map.put("avatar", sysUser.getAvatar());
        return ServiceResult.success(map);
    }

    /**
     * @Description: 登出
     * @Param: []
     * @return: java.lang.String
     * @Author: 孟耀
     * @Date: 2021/8/5
     */
    @SysLog(module = "登出",operType = OperType.LOGOUT)
    @ApiOperation(value = "用户登出", notes = "用户登出")
    @PostMapping("/logout")
    public String logout() {
        return ServiceResult.success();
    }
}
