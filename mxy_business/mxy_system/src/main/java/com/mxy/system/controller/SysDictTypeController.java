package com.mxy.system.controller;


import com.mxy.common.log.annotation.SysLog;
import com.mxy.system.entity.vo.SysDictTypeVO;
import com.mxy.system.service.SysDictTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 字典类型 前端控制器
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
@Api(value = "字典类型", tags = "字典类型")
@RestController
@RequestMapping("/system/sysDictType")
public class SysDictTypeController {

    @Autowired
    public SysDictTypeService sysDictTypeService;

    /**
     * @Description 获取字典类型列表
     * @author 孟小耀
     * @date 2021-07-25
     */
    @SysLog(module = "获取字典类型列表")
    @ApiOperation(value = "获取字典类型列表")
    @PostMapping("/getList")
    public String getList(@RequestBody SysDictTypeVO sysDictTypeVO) {
        return sysDictTypeService.getList(sysDictTypeVO);
    }

    /**
     * @Description 新增字典类型
     * @author 孟小耀
     * @date 2021-07-25
     */
    @SysLog(module = "新增字典类型")
    @ApiOperation(value = "新增字典类型")
    @PostMapping("/add")
    public String add(@RequestBody SysDictTypeVO sysDictTypeVO) {
        return sysDictTypeService.add(sysDictTypeVO);
    }

    /**
     * @Description 编辑字典类型
     * @author 孟小耀
     * @date 2021-07-25
     */
    @SysLog(module = "编辑字典类型")
    @ApiOperation(value = "编辑字典类型")
    @PostMapping("/edit")
    public String edit(@RequestBody SysDictTypeVO sysDictTypeVO) {
        return sysDictTypeService.edit(sysDictTypeVO);
    }

    /**
     * @Description 删除字典类型
     * @author 孟小耀
     * @date 2021-07-25
     */
    @SysLog(module = "删除字典类型")
    @ApiOperation(value = "删除字典类型")
    @PostMapping("/delete")
    public String delete(@RequestBody SysDictTypeVO sysDictTypeVO) {
        return sysDictTypeService.delete(sysDictTypeVO);
    }

}

