package com.mxy.system.info.controller;

import com.mxy.common.core.utils.ServiceResult;
import com.mxy.system.info.config.Server;
import com.mxy.system.info.utils.MonitorServerUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mengyao
 */
@Api(value = "系统监控", tags = "系统监控")
@RestController
@CrossOrigin
@RequestMapping("/api/sysInfo")
public class SysInfoController {

    /**
     * @Description 系统监控
     * @author 孟小耀
     * @date 2022-02-07
     */
    @ApiOperation(value = "系统监控")
    @PostMapping("/detail")
    public String sysInfoDetail() {
        Server server = MonitorServerUtil.getServerRunInfo();
        return ServiceResult.success(server);
    }

}
