package com.mxy.system.controller;

import com.mxy.common.log.annotation.SysLog;
import com.mxy.common.log.enums.OperType;
import com.mxy.system.entity.vo.SysPictureVO;
import com.mxy.system.service.SysPictureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 图片 前端控制器
 * </p>
 *
 * @author 孟小耀
 * @since 2021-08-18
 */
@Api(value = "图片",tags = "图片")
@RestController
@RequestMapping("/system/sysPicture")
public class SysPictureController {

    @Autowired
    public SysPictureService sysPictureService;

    /**
     * @Description 获取图片列表
     * @author 孟小耀
     * @date 2021-08-18
     */
    @SysLog(module = "获取图片列表")
    @ApiOperation(value = "获取图片列表")
    @PostMapping("/getList")
    public String getList(@RequestBody SysPictureVO sysPictureVO) {
        return sysPictureService.getList(sysPictureVO);
    }

    /**
     * @Description 新增图片
     * @author 孟小耀
     * @date 2021-08-18
     */
    @SysLog(module = "新增图片", operType = OperType.ADD)
    @ApiOperation(value = "新增图片")
    @PostMapping("/add")
    public String add(@RequestBody SysPictureVO sysPictureVO) {
        return sysPictureService.add(sysPictureVO);
        }

    /**
     * @Description 编辑图片
     * @author 孟小耀
     * @date 2021-08-18
     */
    @SysLog(module = "编辑图片", operType = OperType.UPDATE)
    @ApiOperation(value = "编辑图片")
    @PostMapping("/edit")
    public String edit(@RequestBody SysPictureVO sysPictureVO) {
        return sysPictureService.edit(sysPictureVO);
        }

    /**
     * @Description 删除图片
     * @author 孟小耀
     * @date 2021-08-18
     */
    @SysLog(module = "删除图片", operType = OperType.DELETE)
    @ApiOperation(value = "删除图片")
    @PostMapping("/delete")
    public String delete(@RequestBody SysPictureVO sysPictureVO) {
        return sysPictureService.delete(sysPictureVO);
        }

}

