package com.mxy.system.controller;

import com.mxy.common.log.annotation.SysLog;
import com.mxy.common.log.enums.OperType;
import com.mxy.system.entity.vo.SysUserVO;
import com.mxy.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 用户信息 前端控制器
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-21
 */
@Api(value = "用户信息",tags = "用户信息")
@RestController
@CrossOrigin
@RequestMapping("/system/sysUser")
public class SysUserController {

    @Autowired
    public SysUserService sysUserService;

    /**
     * @Description 获取用户信息列表
     * @author 孟小耀
     * @date 2021-07-21
     */
    @SysLog(module = "获取用户信息列表",operType = OperType.SELECT)
    @ApiOperation(value = "获取用户信息列表")
    @PostMapping(value = "/getList")
    public String getList(@RequestBody(required = false) SysUserVO sysUserVO) {
        return sysUserService.getList(sysUserVO);
    }

    /**
     * @Description 新增用户信息
     * @author 孟小耀
     * @date 2021-07-21
     */
    @SysLog(module = "新增用户信息",operType = OperType.ADD)
    @ApiOperation(value = "新增用户信息")
    @PostMapping("/add")
    public String add(@RequestBody SysUserVO sysUserVO) {
        return sysUserService.add(sysUserVO);
        }

    /**
     * @Description 编辑用户信息
     * @author 孟小耀
     * @date 2021-07-21
     */
    @SysLog(module = "编辑用户信息", operType = OperType.UPDATE)
    @ApiOperation(value = "编辑用户信息")
    @PostMapping("/edit")
    public String edit(@RequestBody SysUserVO sysUserVO) {
        return sysUserService.edit(sysUserVO);
        }

    /**
     * @Description 删除用户信息
     * @author 孟小耀
     * @date 2021-07-21
     */
    @SysLog(module = "删除用户信息",operType = OperType.DELETE)
    @ApiOperation(value = "删除用户信息")
    @PostMapping("/delete")
    public String delete(@RequestBody SysUserVO sysUserVO) {
        return sysUserService.delete(sysUserVO);
        }

}

