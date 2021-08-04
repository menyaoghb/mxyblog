package com.mxy.system.controller;


import com.mxy.common.log.annotation.SysLog;
import com.mxy.system.entity.vo.SysRoleMenuVO;
import com.mxy.system.service.SysRoleMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色和菜单关联 前端控制器
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
@Api(value = "角色和菜单关联",tags = "角色和菜单关联")
@RestController
@RequestMapping("/system/sysRoleMenu")
public class SysRoleMenuController {

    @Autowired
    public SysRoleMenuService sysRoleMenuService;

    /**
     * @Description 获取角色和菜单关联列表
     * @author 孟小耀
     * @date 2021-07-25
     */
    @SysLog(module = "获取角色和菜单关联列表")
    @ApiOperation(value = "获取角色和菜单关联列表")
    @PostMapping("/getList")
    public String getList(@RequestBody SysRoleMenuVO sysRoleMenuVO) {
        return sysRoleMenuService.getList(sysRoleMenuVO);
    }

    /**
     * @Description 新增角色和菜单关联
     * @author 孟小耀
     * @date 2021-07-25
     */
    @SysLog(module = "新增角色和菜单关联")
    @ApiOperation(value = "新增角色和菜单关联")
    @PostMapping("/add")
    public String add(@RequestBody SysRoleMenuVO sysRoleMenuVO) {
        return sysRoleMenuService.add(sysRoleMenuVO);
        }

    /**
     * @Description 编辑角色和菜单关联
     * @author 孟小耀
     * @date 2021-07-25
     */
    @SysLog(module = "编辑角色和菜单关联")
    @ApiOperation(value = "编辑角色和菜单关联")
    @PostMapping("/edit")
    public String edit(@RequestBody SysRoleMenuVO sysRoleMenuVO) {
        return sysRoleMenuService.edit(sysRoleMenuVO);
        }

    /**
     * @Description 删除角色和菜单关联
     * @author 孟小耀
     * @date 2021-07-25
     */
    @SysLog(module = "删除角色和菜单关联")
    @ApiOperation(value = "删除角色和菜单关联")
    @PostMapping("/delete")
    public String delete(@RequestBody SysRoleMenuVO sysRoleMenuVO) {
        return sysRoleMenuService.delete(sysRoleMenuVO);
        }

}

