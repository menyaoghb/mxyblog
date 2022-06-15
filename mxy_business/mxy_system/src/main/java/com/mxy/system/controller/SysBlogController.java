package com.mxy.system.controller;

import com.mxy.common.log.annotation.SysLog;
import com.mxy.common.log.enums.OperType;
import com.mxy.system.entity.vo.SysBlogVO;
import com.mxy.system.service.SysBlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 博客 前端控制器
 * </p>
 *
 * @author 孟小耀
 * @since 2022-06-14
 */
@Api(value = "博客",tags = "博客")
@RestController
@RequestMapping("/api/sysBlog")
@CrossOrigin
public class SysBlogController {

    @Autowired
    public SysBlogService sysBlogService;

    /**
     * @Description 获取博客列表
     * @author 孟小耀
     * @date 2022-06-14
     */
    @SysLog(module = "获取博客列表")
    @ApiOperation(value = "获取博客列表")
    @PostMapping("/getList")
    public String getList(@RequestParam(value = "type") String type) {
        SysBlogVO sysBlogVO = new SysBlogVO();
        sysBlogVO.setCurrentPage(1L);
        sysBlogVO.setPageSize(100L);
        sysBlogVO.setType(type);
        return sysBlogService.getList(sysBlogVO);
    }


    @ApiOperation(value = "博客详细")
    @GetMapping("/getDataById")
    public String getDataById(@RequestParam(value = "id") String id) {
        return sysBlogService.getDataById(id);
    }

    /**
     * @Description 新增博客
     * @author 孟小耀
     * @date 2022-06-14
     */
    @SysLog(module = "新增博客", operType = OperType.ADD)
    @ApiOperation(value = "新增博客")
    @PostMapping("/add")
    public String add(@RequestBody SysBlogVO sysBlogVO) {
        return sysBlogService.add(sysBlogVO);
        }

    /**
     * @Description 编辑博客
     * @author 孟小耀
     * @date 2022-06-14
     */
    @SysLog(module = "编辑博客", operType = OperType.UPDATE)
    @ApiOperation(value = "编辑博客")
    @PostMapping("/edit")
    public String edit(@RequestBody SysBlogVO sysBlogVO) {
        return sysBlogService.edit(sysBlogVO);
        }

    /**
     * @Description 删除博客
     * @author 孟小耀
     * @date 2022-06-14
     */
    @SysLog(module = "删除博客", operType = OperType.DELETE)
    @ApiOperation(value = "删除博客")
    @PostMapping("/delete")
    public String delete(@RequestBody SysBlogVO sysBlogVO) {
        return sysBlogService.delete(sysBlogVO);
        }

}

