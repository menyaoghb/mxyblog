package com.mxy.system.controller;


import com.mxy.common.log.annotation.SysLog;
import com.mxy.system.entity.vo.SysRoleVO;
import com.mxy.system.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色信息 前端控制器
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
@Api(value = "角色信息",tags = "角色信息")
@RestController
@RequestMapping("/system/sysRole")
public class SysRoleController {

    @Autowired
    public SysRoleService sysRoleService;

    /**
     * @Description 获取角色信息列表
     * @author 孟小耀
     * @date 2021-07-25
     */
    @SysLog(module = "获取角色信息列表")
    @ApiOperation(value = "获取角色信息列表")
    @PostMapping("/getList")
    public String getList(@RequestBody SysRoleVO sysRoleVO) {
        return sysRoleService.getList(sysRoleVO);
    }

    /**
     * @Description 新增角色信息
     * @author 孟小耀
     * @date 2021-07-25
     */
    @SysLog(module = "新增角色信息")
    @ApiOperation(value = "新增角色信息")
    @PostMapping("/add")
    public String add(@RequestBody SysRoleVO sysRoleVO) {
        return sysRoleService.add(sysRoleVO);
        }

    /**
     * @Description 编辑角色信息
     * @author 孟小耀
     * @date 2021-07-25
     */
    @SysLog(module = "编辑角色信息")
    @ApiOperation(value = "编辑角色信息")
    @PostMapping("/edit")
    public String edit(@RequestBody SysRoleVO sysRoleVO) {
        return sysRoleService.edit(sysRoleVO);
        }

    /**
     * @Description 删除角色信息
     * @author 孟小耀
     * @date 2021-07-25
     */
    @SysLog(module = "删除角色信息")
    @ApiOperation(value = "删除角色信息")
    @PostMapping("/delete")
    public String delete(@RequestBody SysRoleVO sysRoleVO) {
        return sysRoleService.delete(sysRoleVO);
        }

}

