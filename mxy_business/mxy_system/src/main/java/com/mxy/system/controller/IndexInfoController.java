package com.mxy.system.controller;

import com.mxy.system.entity.vo.SysOperLogVO;
import com.mxy.system.service.SysOperLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mengyao
 */
@Api(value = "系统首页", tags = "系统首页")
@RestController
@RequestMapping("/api/indexInfo")
public class IndexInfoController {

    @Autowired
    public SysOperLogService sysOperLogService;

    /**
     * @Description 统计操作日志
     * @author 孟小耀
     * @date 2022-08-13
     */
    @ApiOperation(value = "统计操作日志")
    @PostMapping("/statisticsLogInfo")
    public String statisticsLogInfo(@RequestBody SysOperLogVO sysOperLogVO) {
        return sysOperLogService.statisticsLogInfo(sysOperLogVO);
    }

}
