package com.mxy.system.controller;

import com.mxy.common.log.annotation.SysLog;
import com.mxy.common.log.enums.OperType;
import com.mxy.system.entity.vo.SysUserVO;
import com.mxy.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/api/sysUser")
public class SysUserController {

    private static final Logger LOG = LogManager.getLogger(SysUserController.class);

    @Autowired
    public SysUserService sysUserService;

    /**
     * @Description 获取用户信息列表
     * @author 孟小耀
     * @date 2021-07-21
     */
    @ApiOperation(value = "用户信息列表")
    @PostMapping(value = "/getList")
    public String getList(@RequestBody(required = false) SysUserVO sysUserVO) {
        return sysUserService.getList(sysUserVO);
    }

    /**
     * @Description 新增用户信息
     * @author 孟小耀
     * @date 2021-07-21
     */
    @SysLog(module = "新增用户",operType = OperType.ADD)
    @ApiOperation(value = "新增用户")
    @PostMapping("/add")
    public String add(@RequestBody SysUserVO sysUserVO) {
        return sysUserService.add(sysUserVO);
        }

    /**
     * @Description 编辑用户信息
     * @author 孟小耀
     * @date 2021-07-21
     */
    @SysLog(module = "编辑用户", operType = OperType.UPDATE)
    @ApiOperation(value = "编辑用户")
    @PostMapping("/edit")
    public String edit(@RequestBody SysUserVO sysUserVO) {
        return sysUserService.edit(sysUserVO);
        }

    /**
     * @Description 删除用户信息
     * @author 孟小耀
     * @date 2021-07-21
     */
    @SysLog(module = "删除用户",operType = OperType.DELETE)
    @ApiOperation(value = "删除用户")
    @PostMapping("/delete")
    public String delete(@RequestBody SysUserVO sysUserVO) {
        return sysUserService.delete(sysUserVO);
        }

    /**
     * @Description 重置密码
     * @author 孟小耀
     * @date 2021-07-21
     */
    @SysLog(module = "重置密码", operType = OperType.UPDATE)
    @ApiOperation(value = "重置密码")
    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody SysUserVO sysUserVO) {
        return sysUserService.resetPassword(sysUserVO);
    }

    /**
     * @Description 用户状态改变
     * @author 孟小耀
     * @date 2021-07-21
     */
    @SysLog(module = "用户状态改变", operType = OperType.UPDATE)
    @ApiOperation(value = "用户状态改变")
    @PostMapping("/editUserStatus")
    public String editUserStatus(@RequestBody SysUserVO sysUserVO) {
        return sysUserService.editUserStatus(sysUserVO);
    }

}

