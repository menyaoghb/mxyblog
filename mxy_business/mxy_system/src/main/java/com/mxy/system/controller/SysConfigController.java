package com.mxy.system.controller;

import com.mxy.common.log.annotation.SysLog;
import com.mxy.common.log.enums.OperType;
import com.mxy.system.entity.vo.SysConfigVO;
import com.mxy.system.service.SysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统参数配置 前端控制器
 * </p>
 *
 * @author 孟小耀
 * @since 2022-02-10
 */
@Api(value = "系统参数配置",tags = "系统参数配置")
@RestController
@RequestMapping("/api/sysConfig")
public class SysConfigController {

    @Autowired
    public SysConfigService sysConfigService;

    /**
     * @Description 获取系统参数配置列表
     * @author 孟小耀
     * @date 2022-02-10
     */
    @SysLog(module = "获取系统参数配置列表")
    @ApiOperation(value = "获取系统参数配置列表")
    @PostMapping("/getList")
    public String getList(@RequestBody SysConfigVO sysConfigVO) {
        return sysConfigService.getList(sysConfigVO);
    }

    /**
     * @Description 新增系统参数配置
     * @author 孟小耀
     * @date 2022-02-10
     */
    @SysLog(module = "新增系统参数配置", operType = OperType.ADD)
    @ApiOperation(value = "新增系统参数配置")
    @PostMapping("/add")
    public String add(@RequestBody SysConfigVO sysConfigVO) {
        return sysConfigService.add(sysConfigVO);
        }

    /**
     * @Description 编辑系统参数配置
     * @author 孟小耀
     * @date 2022-02-10
     */
    @SysLog(module = "编辑系统参数配置", operType = OperType.UPDATE)
    @ApiOperation(value = "编辑系统参数配置")
    @PostMapping("/edit")
    public String edit(@RequestBody SysConfigVO sysConfigVO) {
        return sysConfigService.edit(sysConfigVO);
        }

    /**
     * @Description 删除系统参数配置
     * @author 孟小耀
     * @date 2022-02-10
     */
    @SysLog(module = "删除系统参数配置", operType = OperType.DELETE)
    @ApiOperation(value = "删除系统参数配置")
    @PostMapping("/delete")
    public String delete(@RequestBody SysConfigVO sysConfigVO) {
        return sysConfigService.delete(sysConfigVO);
        }

}

