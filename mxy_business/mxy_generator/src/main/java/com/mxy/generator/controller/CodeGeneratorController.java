package com.mxy.generator.controller;

import com.mxy.common.core.utils.ServiceResult;
import com.mxy.generator.mapper.CodeGeneratorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 孟耀
 */
@Api(value = "代码生成", tags = "代码生成")
@RestController
@RequestMapping("/api/codeGenerator")
public class CodeGeneratorController {

    @Resource
    private CodeGeneratorService codeGeneratorService;

    /**
     * @Description 获取系统数据库表名
     */
    @ApiOperation(value = "获取系统数据库表名")
    @PostMapping("/getTableName")
    public String getTableName() {
        List<Map<String,String>> list = codeGeneratorService.getTableName();
        return ServiceResult.success(list);
    }


    public void codeGenerator() {

    }
}
