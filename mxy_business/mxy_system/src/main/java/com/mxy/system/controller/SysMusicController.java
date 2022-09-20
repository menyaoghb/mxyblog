package com.mxy.system.controller;

import com.mxy.common.log.annotation.SysLog;
import com.mxy.common.log.enums.OperType;
import com.mxy.system.entity.vo.SysMusicVO;
import com.mxy.system.service.SysMusicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 音乐 前端控制器
 * </p>
 *
 * @author 孟小耀
 * @since 2022-09-13
 */
@Api(value = "音乐",tags = "音乐")
@RestController
@RequestMapping("/api/sysMusic")
public class SysMusicController {

    @Autowired
    public SysMusicService sysMusicService;

    /**
     * @Description 查询音乐列表
     * @author 孟小耀
     * @date 2022-09-13
     */
    @SysLog(module = "查询音乐列表")
    @ApiOperation(value = "查询音乐列表")
    @PostMapping("/getList")
    public String getList(@RequestBody SysMusicVO sysMusicVO) {
        return sysMusicService.getList(sysMusicVO);
    }

    /**
     * @Description 新增音乐
     * @author 孟小耀
     * @date 2022-09-13
     */
    @SysLog(module = "新增音乐", operType = OperType.ADD)
    @ApiOperation(value = "新增音乐")
    @PostMapping("/add")
    public String add(@RequestBody SysMusicVO sysMusicVO) {
        return sysMusicService.add(sysMusicVO);
        }

    /**
     * @Description 编辑音乐
     * @author 孟小耀
     * @date 2022-09-13
     */
    @SysLog(module = "编辑音乐", operType = OperType.UPDATE)
    @ApiOperation(value = "编辑音乐")
    @PostMapping("/edit")
    public String edit(@RequestBody SysMusicVO sysMusicVO) {
        return sysMusicService.edit(sysMusicVO);
        }

    /**
     * @Description 删除音乐
     * @author 孟小耀
     * @date 2022-09-13
     */
    @SysLog(module = "删除音乐", operType = OperType.DELETE)
    @ApiOperation(value = "删除音乐")
    @PostMapping("/delete")
    public String delete(@RequestBody SysMusicVO sysMusicVO) {
        return sysMusicService.delete(sysMusicVO);
        }

}

