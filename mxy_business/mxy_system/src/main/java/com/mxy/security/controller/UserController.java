package com.mxy.security.controller;

import com.mxy.common.core.entity.SelfUserEntity;
import com.mxy.common.core.entity.SysMenu;
import com.mxy.common.core.entity.SysUser;
import com.mxy.security.common.util.ResultUtil;
import com.mxy.system.service.SysMenuService;
import com.mxy.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 普通用户
 *
 * @Author Mxy
 * @CreateTime 2022/01/2 14:43
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 用户端信息
     *
     * @Author Mxy
     * @CreateTime 2022/01/2 14:52
     * @Return Map<String, Object> 返回数据MAP
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Map<String, Object> userLogin() {
        Map<String, Object> result = new HashMap<>();
        SelfUserEntity userDetails = (SelfUserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser sysUser = sysUserService.getById(userDetails.getUserId());
        result.put("title", "用户信息");
        result.put("data", userDetails);
        result.put("user", sysUser);
        return ResultUtil.resultSuccess(result);

    }

    /**
     * 拥有USER角色和sys:user:info权限可以访问
     *
     * @Author Mxy
     * @CreateTime 2022/01/2 14:22
     * @Return Map<String, Object> 返回数据MAP
     */
    @PreAuthorize("hasRole('USER') and hasPermission('/user/menuList','sys:user:info')")
    @RequestMapping(value = "/menuList", method = RequestMethod.GET)
    public Map<String, Object> sysMenuEntity() {
        Map<String, Object> result = new HashMap<>();
        List<SysMenu> sysMenuEntityList = sysMenuService.list();
        result.put("title", "拥有USER角色和sys:user:info权限可以访问");
        result.put("data", sysMenuEntityList);
        return ResultUtil.resultSuccess(result);
    }

}
