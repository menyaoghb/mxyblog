package com.mxy.system.controller;

import com.mxy.common.log.annotation.SysLog;
import com.mxy.common.log.enums.OperType;
import com.mxy.system.entity.vo.SysBookmarksVO;
import com.mxy.system.service.SysBookmarksService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 收藏书签 前端控制器
 * </p>
 *
 * @author 孟小耀
 * @since 2022-03-07
 */
@Api(value = "收藏书签",tags = "收藏书签")
@RestController
@RequestMapping("/system/sysBookmarks")
public class SysBookmarksController {

    @Autowired
    public SysBookmarksService sysBookmarksService;

    /**
     * @Description 获取收藏书签列表
     * @author 孟小耀
     * @date 2022-03-07
     */
    @SysLog(module = "获取收藏书签列表")
    @ApiOperation(value = "获取收藏书签列表")
    @PostMapping("/getList")
    public String getList(@RequestBody SysBookmarksVO sysBookmarksVO) {
        return sysBookmarksService.getList(sysBookmarksVO);
    }

    /**
     * @Description 新增收藏书签
     * @author 孟小耀
     * @date 2022-03-07
     */
    @SysLog(module = "新增收藏书签", operType = OperType.ADD)
    @ApiOperation(value = "新增收藏书签")
    @PostMapping("/add")
    public String add(@RequestBody SysBookmarksVO sysBookmarksVO) {
        return sysBookmarksService.add(sysBookmarksVO);
        }

    /**
     * @Description 编辑收藏书签
     * @author 孟小耀
     * @date 2022-03-07
     */
    @SysLog(module = "编辑收藏书签", operType = OperType.UPDATE)
    @ApiOperation(value = "编辑收藏书签")
    @PostMapping("/edit")
    public String edit(@RequestBody SysBookmarksVO sysBookmarksVO) {
        return sysBookmarksService.edit(sysBookmarksVO);
        }

    /**
     * @Description 删除收藏书签
     * @author 孟小耀
     * @date 2022-03-07
     */
    @SysLog(module = "删除收藏书签", operType = OperType.DELETE)
    @ApiOperation(value = "删除收藏书签")
    @PostMapping("/delete")
    public String delete(@RequestBody SysBookmarksVO sysBookmarksVO) {
        return sysBookmarksService.delete(sysBookmarksVO);
        }

}

