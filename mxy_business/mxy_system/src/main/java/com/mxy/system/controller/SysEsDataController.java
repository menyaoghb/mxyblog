package com.mxy.system.controller;

import com.mxy.common.log.annotation.SysLog;
import com.mxy.common.log.enums.OperType;
import com.mxy.system.entity.vo.SysEsDataVO;
import com.mxy.system.service.SysEsDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * ES数据存储 前端控制器
 * </p>
 *
 * @author 孟小耀
 * @since 2022-05-09
 */
@Api(value = "ES数据存储",tags = "ES数据存储")
@RestController
@RequestMapping("/api/sysEsData")
public class SysEsDataController {

    @Autowired
    public SysEsDataService sysEsDataService;

    /**
     * @Description 获取ES数据存储列表
     * @author 孟小耀
     * @date 2022-05-09
     */
    @SysLog(module = "获取ES数据存储列表")
    @ApiOperation(value = "获取ES数据存储列表")
    @PostMapping("/getList")
    public String getList(@RequestBody SysEsDataVO sysEsDataVO) {
        return sysEsDataService.getList(sysEsDataVO);
    }

    /**
     * @Description 新增ES数据存储
     * @author 孟小耀
     * @date 2022-05-09
     */
    @SysLog(module = "新增ES数据存储", operType = OperType.ADD)
    @ApiOperation(value = "新增ES数据存储")
    @PostMapping("/add")
    public String add(@RequestBody SysEsDataVO sysEsDataVO) {
        return sysEsDataService.add(sysEsDataVO);
        }

    /**
     * @Description 编辑ES数据存储
     * @author 孟小耀
     * @date 2022-05-09
     */
    @SysLog(module = "编辑ES数据存储", operType = OperType.UPDATE)
    @ApiOperation(value = "编辑ES数据存储")
    @PostMapping("/edit")
    public String edit(@RequestBody SysEsDataVO sysEsDataVO) {
        return sysEsDataService.edit(sysEsDataVO);
        }

    /**
     * @Description 删除ES数据存储
     * @author 孟小耀
     * @date 2022-05-09
     */
    @SysLog(module = "删除ES数据存储", operType = OperType.DELETE)
    @ApiOperation(value = "删除ES数据存储")
    @PostMapping("/delete")
    public String delete(@RequestBody SysEsDataVO sysEsDataVO) {
        return sysEsDataService.delete(sysEsDataVO);
        }

}

