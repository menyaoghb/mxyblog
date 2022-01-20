package com.mxy.system.controller;


import com.mxy.system.entity.vo.SysUserRoleVO;
import com.mxy.system.service.SysUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户和角色关联 前端控制器
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
@Api(value = "用户和角色关联",tags = "用户和角色关联")
@RestController
@RequestMapping("/api/sysUserRole")
public class SysUserRoleController {

    @Autowired
    public SysUserRoleService sysUserRoleService;

    /**
     * @Description 获取用户和角色关联列表
     * @author 孟小耀
     * @date 2021-07-25
     */
    @ApiOperation(value = "获取用户和角色关联列表")
    @PostMapping("/getList")
    public String getList(@RequestBody SysUserRoleVO sysUserRoleVO) {
        return sysUserRoleService.getList(sysUserRoleVO);
    }

    /**
     * @Description 新增用户和角色关联
     * @author 孟小耀
     * @date 2021-07-25
     */
    @ApiOperation(value = "新增用户和角色关联")
    @PostMapping("/add")
    public String add(@RequestBody SysUserRoleVO sysUserRoleVO) {
        return sysUserRoleService.add(sysUserRoleVO);
        }

    /**
     * @Description 编辑用户和角色关联
     * @author 孟小耀
     * @date 2021-07-25
     */
    @ApiOperation(value = "编辑用户和角色关联")
    @PostMapping("/edit")
    public String edit(@RequestBody SysUserRoleVO sysUserRoleVO) {
        return sysUserRoleService.edit(sysUserRoleVO);
        }

    /**
     * @Description 删除用户和角色关联
     * @author 孟小耀
     * @date 2021-07-25
     */
    @ApiOperation(value = "删除用户和角色关联")
    @PostMapping("/delete")
    public String delete(@RequestBody SysUserRoleVO sysUserRoleVO) {
        return sysUserRoleService.delete(sysUserRoleVO);
        }

}

