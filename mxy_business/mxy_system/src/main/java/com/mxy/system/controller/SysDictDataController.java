package com.mxy.system.controller;


import com.mxy.common.log.annotation.SysLog;
import com.mxy.common.log.enums.OperType;
import com.mxy.system.entity.vo.SysDictDataVO;
import com.mxy.system.service.SysDictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 字典数据 前端控制器
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
@Api(value = "字典数据",tags = "字典数据")
@RestController
@RequestMapping("/api/sysDictData")
public class SysDictDataController {

    @Autowired
    public SysDictDataService sysDictDataService;

    /**
     * @Description 获取字典数据列表
     * @author 孟小耀
     * @date 2021-07-25
     */
    @SysLog(module = "字典数据列表",operType = OperType.SELECT)
    @ApiOperation(value = "字典数据列表")
    @PostMapping("/getList")
    public String getList(@RequestBody SysDictDataVO sysDictDataVO) {
        return sysDictDataService.getList(sysDictDataVO);
    }

    /**
     * @Description 新增字典数据
     * @author 孟小耀
     * @date 2021-07-25
     */
    @SysLog(module = "新增字典数据",operType = OperType.ADD)
    @ApiOperation(value = "新增字典数据")
    @PostMapping("/add")
    public String add(@RequestBody SysDictDataVO sysDictDataVO) {
        return sysDictDataService.add(sysDictDataVO);
        }

    /**
     * @Description 编辑字典数据
     * @author 孟小耀
     * @date 2021-07-25
     */
    @SysLog(module = "编辑字典数据",operType = OperType.UPDATE)
    @ApiOperation(value = "编辑字典数据")
    @PostMapping("/edit")
    public String edit(@RequestBody SysDictDataVO sysDictDataVO) {
        return sysDictDataService.edit(sysDictDataVO);
        }

    /**
     * @Description 删除字典数据
     * @author 孟小耀
     * @date 2021-07-25
     */
    @SysLog(module = "删除字典数据",operType = OperType.DELETE)
    @ApiOperation(value = "删除字典数据")
    @PostMapping("/delete")
    public String delete(@RequestBody SysDictDataVO sysDictDataVO) {
        return sysDictDataService.delete(sysDictDataVO);
        }

}

