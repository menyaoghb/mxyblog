package com.mxy.system.controller;

import com.mxy.common.log.annotation.SysLog;
import com.mxy.common.log.enums.OperType;
import com.mxy.system.entity.vo.SysArticleVO;
import com.mxy.system.service.SysArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 文章 前端控制器
 * </p>
 *
 * @author 孟小耀
 * @since 2021-08-17
 */
@Api(value = "文章",tags = "文章")
@RestController
@RequestMapping("/api/sysArticle")
public class SysArticleController {

    @Autowired
    public SysArticleService sysArticleService;

    /**
     * @Description 获取文章列表
     * @author 孟小耀
     * @date 2021-08-17
     */
    @SysLog(module = "获取文章列表")
    @ApiOperation(value = "文章列表")
    @PostMapping("/getList")
    public String getList(@RequestBody SysArticleVO sysArticleVO) {
        return sysArticleService.getList(sysArticleVO);
    }

    /**
     * @Description 新增文章
     * @author 孟小耀
     * @date 2021-08-17
     */
    @SysLog(module = "新增文章", operType = OperType.ADD)
    @ApiOperation(value = "新增文章")
    @PostMapping("/add")
    public String add(@RequestBody SysArticleVO sysArticleVO) {
        return sysArticleService.add(sysArticleVO);
        }

    /**
     * @Description 编辑文章
     * @author 孟小耀
     * @date 2021-08-17
     */
    @SysLog(module = "编辑文章", operType = OperType.UPDATE)
    @ApiOperation(value = "编辑文章")
    @PostMapping("/edit")
    public String edit(@RequestBody SysArticleVO sysArticleVO) {
        return sysArticleService.edit(sysArticleVO);
        }

    /**
     * @Description 删除文章
     * @author 孟小耀
     * @date 2021-08-17
     */
    @SysLog(module = "删除文章", operType = OperType.DELETE)
    @ApiOperation(value = "删除文章")
    @PostMapping("/delete")
    public String delete(@RequestBody SysArticleVO sysArticleVO) {
        return sysArticleService.delete(sysArticleVO);
        }


}

