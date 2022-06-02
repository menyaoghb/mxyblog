package com.mxy.system.controller;

import com.mxy.common.log.annotation.SysLog;
import com.mxy.common.log.enums.OperType;
import com.mxy.system.entity.vo.SysCountryVO;
import com.mxy.system.service.SysCountryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 国家省分地市区县 前端控制器
 * </p>
 *
 * @author 孟小耀
 * @since 2022-05-10
 */
@Api(value = "国家省分地市区县",tags = "国家省分地市区县")
@RestController
@RequestMapping("/api/sysCountry")
public class SysCountryController {

    @Autowired
    public SysCountryService sysCountryService;

    /**
     * @Description 获取国家省分地市区县列表
     * @author 孟小耀
     * @date 2022-05-10
     */
    @SysLog(module = "获取国家省分地市区县列表")
    @ApiOperation(value = "获取国家省分地市区县列表")
    @PostMapping("/getList")
    public String getList(@RequestBody SysCountryVO sysCountryVO) {
        return sysCountryService.getList(sysCountryVO);
    }

    /**
     * @Description 新增国家省分地市区县
     * @author 孟小耀
     * @date 2022-05-10
     */
    @SysLog(module = "新增国家省分地市区县", operType = OperType.ADD)
    @ApiOperation(value = "新增国家省分地市区县")
    @PostMapping("/add")
    public String add(@RequestBody SysCountryVO sysCountryVO) {
        return sysCountryService.add(sysCountryVO);
        }

    /**
     * @Description 编辑国家省分地市区县
     * @author 孟小耀
     * @date 2022-05-10
     */
    @SysLog(module = "编辑国家省分地市区县", operType = OperType.UPDATE)
    @ApiOperation(value = "编辑国家省分地市区县")
    @PostMapping("/edit")
    public String edit(@RequestBody SysCountryVO sysCountryVO) {
        return sysCountryService.edit(sysCountryVO);
        }

    /**
     * @Description 删除国家省分地市区县
     * @author 孟小耀
     * @date 2022-05-10
     */
    @SysLog(module = "删除国家省分地市区县", operType = OperType.DELETE)
    @ApiOperation(value = "删除国家省分地市区县")
    @PostMapping("/delete")
    public String delete(@RequestBody SysCountryVO sysCountryVO) {
        return sysCountryService.delete(sysCountryVO);
        }

    /**
     * @Description 加载世界树
     * @author 孟小耀
     * @date 2022-05-10
     */
    @SysLog(module = "加载世界树", operType = OperType.DELETE)
    @ApiOperation(value = "加载世界树")
    @PostMapping("/getWorldTree")
    public String worldTree() {
        return sysCountryService.worldTree();
    }

}

