package com.mxy.system.controller;


import com.mxy.system.entity.vo.SysOperLogVO;
import com.mxy.system.service.SysOperLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 操作日志记录 前端控制器
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
@Api(value = "操作日志记录", tags = "操作日志记录")
@RestController
@RequestMapping("/api/sysOperLog")
public class SysOperLogController {

    @Autowired
    public SysOperLogService sysOperLogService;

    /**
     * @Description 获取操作日志记录列表
     * @author 孟小耀
     * @date 2021-07-25
     */
    @ApiOperation(value = "获取操作日志记录列表")
    @PostMapping("/getList")
    public String getList(@RequestBody SysOperLogVO sysOperLogVO) {
        return sysOperLogService.getList(sysOperLogVO);
    }

    /**
     * @Description 新增操作日志记录
     * @author 孟小耀
     * @date 2021-07-25
     */
    @ApiOperation(value = "新增操作日志记录")
    @PostMapping("/add")
    public String add(@RequestBody SysOperLogVO sysOperLogVO) {
        return sysOperLogService.add(sysOperLogVO);
    }

    /**
     * @Description 编辑操作日志记录
     * @author 孟小耀
     * @date 2021-07-25
     */
    @ApiOperation(value = "编辑操作日志记录")
    @PostMapping("/edit")
    public String edit(@RequestBody SysOperLogVO sysOperLogVO) {
        return sysOperLogService.edit(sysOperLogVO);
    }

    /**
     * @Description 删除操作日志记录
     * @author 孟小耀
     * @date 2021-07-25
     */
    @ApiOperation(value = "删除操作日志记录")
    @PostMapping("/delete")
    public String delete(@RequestBody SysOperLogVO sysOperLogVO) {
        return sysOperLogService.delete(sysOperLogVO);
    }

    @ApiOperation(value = "导出操作日志记录")
    @GetMapping("/export")
    public void exportSysLog(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "startTime") String startTime, @RequestParam(value = "endTime") String endTime) {
        SysOperLogVO sysOperLogVO = new SysOperLogVO();
//        sysOperLogVO.setTitle(title);
//        sysOperLogVO.setBusinessType(Integer.parseInt(businessType));
        sysOperLogVO.setStartTime(startTime);
        sysOperLogVO.setEndTime(endTime);
        sysOperLogService.exportSysLog(request, response, sysOperLogVO);
    }
}

