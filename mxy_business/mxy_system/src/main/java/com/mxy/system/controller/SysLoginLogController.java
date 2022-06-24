package com.mxy.system.controller;

import com.mxy.common.log.annotation.SysLog;
import com.mxy.common.log.enums.OperType;
import com.mxy.system.entity.vo.SysLoginLogVO;
import com.mxy.system.service.SysLoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 登录日志 前端控制器
 * </p>
 *
 * @author 孟小耀
 * @since 2022-06-24
 */
@Api(value = "登录日志",tags = "登录日志")
@RestController
@RequestMapping("/api/sysLoginLog")
public class SysLoginLogController {

    @Autowired
    public SysLoginLogService sysLoginLogService;

    /**
     * @Description 获取登录日志列表
     * @author 孟小耀
     * @date 2022-06-24
     */
    @SysLog(module = "获取登录日志列表")
    @ApiOperation(value = "获取登录日志列表")
    @PostMapping("/getList")
    public String getList(@RequestBody SysLoginLogVO sysLoginLogVO) {
        return sysLoginLogService.getList(sysLoginLogVO);
    }

    /**
     * @Description 新增登录日志
     * @author 孟小耀
     * @date 2022-06-24
     */
    @SysLog(module = "新增登录日志", operType = OperType.ADD)
    @ApiOperation(value = "新增登录日志")
    @PostMapping("/add")
    public String add(@RequestBody SysLoginLogVO sysLoginLogVO) {
        return sysLoginLogService.add(sysLoginLogVO);
        }

    /**
     * @Description 编辑登录日志
     * @author 孟小耀
     * @date 2022-06-24
     */
    @SysLog(module = "编辑登录日志", operType = OperType.UPDATE)
    @ApiOperation(value = "编辑登录日志")
    @PostMapping("/edit")
    public String edit(@RequestBody SysLoginLogVO sysLoginLogVO) {
        return sysLoginLogService.edit(sysLoginLogVO);
        }

    /**
     * @Description 删除登录日志
     * @author 孟小耀
     * @date 2022-06-24
     */
    @SysLog(module = "删除登录日志", operType = OperType.DELETE)
    @ApiOperation(value = "删除登录日志")
    @PostMapping("/delete")
    public String delete(@RequestBody SysLoginLogVO sysLoginLogVO) {
        return sysLoginLogService.delete(sysLoginLogVO);
        }

}

