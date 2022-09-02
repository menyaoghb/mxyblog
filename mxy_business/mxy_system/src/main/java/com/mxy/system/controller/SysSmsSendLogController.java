package com.mxy.system.controller;

import com.mxy.common.log.annotation.SysLog;
import com.mxy.common.log.enums.OperType;
import com.mxy.system.entity.vo.SysSmsSendLogVO;
import com.mxy.system.service.SysSmsSendLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 短信发送日志 前端控制器
 * </p>
 *
 * @author 孟小耀
 * @since 2022-09-02
 */
@Api(value = "短信发送日志",tags = "短信发送日志")
@RestController
@RequestMapping("/api/sysSmsSendLog")
public class SysSmsSendLogController {

    @Autowired
    public SysSmsSendLogService sysSmsSendLogService;

    /**
     * @Description 获取短信发送日志列表
     * @author 孟小耀
     * @date 2022-09-02
     */
    @SysLog(module = "查询短信发送日志列表",operType = OperType.SELECT)
    @ApiOperation(value = "查询短信发送日志列表")
    @PostMapping("/getList")
    public String getList(@RequestBody SysSmsSendLogVO sysSmsSendLogVO) {
        return sysSmsSendLogService.getList(sysSmsSendLogVO);
    }

    /**
     * @Description 新增短信发送日志
     * @author 孟小耀
     * @date 2022-09-02
     */
    @SysLog(module = "新增短信发送日志", operType = OperType.ADD)
    @ApiOperation(value = "新增短信发送日志")
    @PostMapping("/add")
    public String add(@RequestBody SysSmsSendLogVO sysSmsSendLogVO) {
        return sysSmsSendLogService.add(sysSmsSendLogVO);
        }

    /**
     * @Description 编辑短信发送日志
     * @author 孟小耀
     * @date 2022-09-02
     */
    @SysLog(module = "编辑短信发送日志", operType = OperType.UPDATE)
    @ApiOperation(value = "编辑短信发送日志")
    @PostMapping("/edit")
    public String edit(@RequestBody SysSmsSendLogVO sysSmsSendLogVO) {
        return sysSmsSendLogService.edit(sysSmsSendLogVO);
        }

    /**
     * @Description 删除短信发送日志
     * @author 孟小耀
     * @date 2022-09-02
     */
    @SysLog(module = "删除短信发送日志", operType = OperType.DELETE)
    @ApiOperation(value = "删除短信发送日志")
    @PostMapping("/delete")
    public String delete(@RequestBody SysSmsSendLogVO sysSmsSendLogVO) {
        return sysSmsSendLogService.delete(sysSmsSendLogVO);
        }

}

