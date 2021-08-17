package com.mxy.system.controller;

import com.mxy.common.log.annotation.SysLog;
import com.mxy.common.log.enums.OperType;
import com.mxy.system.entity.vo.SysWebConfigVO;
import com.mxy.system.service.SysWebConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 网站参数配置 前端控制器
 * </p>
 *
 * @author 孟小耀
 * @since 2021-08-17
 */
@Api(value = "网站参数配置",tags = "网站参数配置")
@RestController
@RequestMapping("/system/sysWebConfig")
public class SysWebConfigController {

    @Autowired
    public SysWebConfigService sysWebConfigService;

    /**
     * @Description 获取网站参数配置列表
     * @author 孟小耀
     * @date 2021-08-17
     */
    @SysLog(module = "获取网站参数配置列表")
    @ApiOperation(value = "获取网站参数配置列表")
    @PostMapping("/getList")
    public String getList(@RequestBody SysWebConfigVO sysWebConfigVO) {
        return sysWebConfigService.getList(sysWebConfigVO);
    }

    /**
     * @Description 新增网站参数配置
     * @author 孟小耀
     * @date 2021-08-17
     */
    @SysLog(module = "新增网站参数配置", operType = OperType.ADD)
    @ApiOperation(value = "新增网站参数配置")
    @PostMapping("/add")
    public String add(@RequestBody SysWebConfigVO sysWebConfigVO) {
        return sysWebConfigService.add(sysWebConfigVO);
        }

    /**
     * @Description 编辑网站参数配置
     * @author 孟小耀
     * @date 2021-08-17
     */
    @SysLog(module = "编辑网站参数配置", operType = OperType.UPDATE)
    @ApiOperation(value = "编辑网站参数配置")
    @PostMapping("/edit")
    public String edit(@RequestBody SysWebConfigVO sysWebConfigVO) {
        return sysWebConfigService.edit(sysWebConfigVO);
        }

    /**
     * @Description 删除网站参数配置
     * @author 孟小耀
     * @date 2021-08-17
     */
    @SysLog(module = "删除网站参数配置", operType = OperType.DELETE)
    @ApiOperation(value = "删除网站参数配置")
    @PostMapping("/delete")
    public String delete(@RequestBody SysWebConfigVO sysWebConfigVO) {
        return sysWebConfigService.delete(sysWebConfigVO);
        }

}

