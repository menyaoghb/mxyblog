package com.mxy.system.controller;

import com.mxy.common.log.annotation.SysLog;
import com.mxy.common.log.enums.OperType;
import com.mxy.system.entity.vo.SysCodeGenerateVO;
import com.mxy.system.service.SysCodeGenerateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 代码生成 前端控制器
 * </p>
 *
 * @author 孟小耀
 * @since 2022-04-18
 */
@Api(value = "代码生成",tags = "代码生成")
@RestController
@RequestMapping("/api/sysCodeGenerate")
public class SysCodeGenerateController {

    @Autowired
    public SysCodeGenerateService sysCodeGenerateService;

    /**
     * @Description 获取系统数据库表名
     * @author 孟小耀
     * @date 2022-04-18
     */
    @ApiOperation(value = "获取系统数据库表名")
    @PostMapping("/getTableName")
    public String getTableName() {
        return sysCodeGenerateService.getTableName();
    }

    /**
     * @Description 代码生成
     * @author 孟小耀
     * @date 2022-04-18
     */
    @SysLog(module = "操作代码生成", operType = OperType.GENERATE)
    @ApiOperation(value = "代码生成")
    @PostMapping("/codeGenerate")
    public String codeGenerate(@RequestBody SysCodeGenerateVO sysCodeGenerateVO) {
        return sysCodeGenerateService.codeGenerate(sysCodeGenerateVO);
    }

}

