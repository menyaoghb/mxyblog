package com.mxy.system.controller;


import com.mxy.common.log.annotation.SysLog;
import com.mxy.common.log.enums.OperType;
import com.mxy.system.entity.vo.SysRoleVO;
import com.mxy.system.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
@RequestMapping("/sysRole")
public class SysRoleController {

    @Autowired
    public SysRoleService sysRoleService;

    /**
     * @Description 获取角色信息列表
     * @author 孟小耀
     * @date 2021-07-25
     */
    @SysLog(module = "获取角色信息列表",operType = OperType.SELECT)
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
    @SysLog(module = "新增角色信息",operType = OperType.ADD)
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
    @SysLog(module = "编辑角色信息",operType = OperType.UPDATE)
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
    @SysLog(module = "删除角色信息",operType = OperType.DELETE)
    @ApiOperation(value = "删除角色信息")
    @PostMapping("/delete")
    public String delete(@RequestBody SysRoleVO sysRoleVO) {
        return sysRoleService.delete(sysRoleVO);
        }

    /**
     * @Description 获取角色信息列表
     * @author 孟小耀
     * @date 2021-07-25
     */
    @SysLog(module = "角色信息",operType = OperType.SELECT)
    @ApiOperation(value = "角色信息")
    @PostMapping("/getRoles")
    public String getRoles() {
        return sysRoleService.getRoles();
    }



    /**
     * @Description 角色状态改变
     * @author 孟小耀
     * @date 2021-07-21
     */
    @SysLog(module = "角色状态改变", operType = OperType.UPDATE)
    @ApiOperation(value = "角色状态改变")
    @PostMapping("/editRoleStatus")
    public String editRoleStatus(@RequestBody SysRoleVO sysRoleVO) {
        return sysRoleService.editRoleStatus(sysRoleVO);
    }


    /**
     * @Description: 角色菜单关联保存
     * @Author: 孟耀
     * @Date: 2021/7/29 0029
     */
    @SysLog(module = "角色菜单关联保存", operType = OperType.UPDATE)
    @ApiOperation(value = "角色菜单关联保存")
    @PostMapping("/saveRoleMenu")
    public String saveRoleMenu(@RequestBody Map<String, Object> map) {
        return sysRoleService.saveRoleMenu(map);
    }

}

