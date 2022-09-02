package com.mxy.system.service;

import com.mxy.common.core.entity.SysOperLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mxy.system.entity.vo.SysOperLogVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 操作日志记录 服务类
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
public interface SysOperLogService extends IService<SysOperLog> {

    /**
     * 查询操作日志记录列表
     */
    public String getList(SysOperLogVO sysOperLogVO);

    /**
    * 新增操作日志记录
    */
    public String add(SysOperLogVO sysOperLogVO);

    /**
    * 编辑操作日志记录
    */
    public String edit(SysOperLogVO sysOperLogVO);

    /**
    * 删除操作日志记录
    */
    public String delete(SysOperLogVO sysOperLogVO);


    /**
     * 导出操作日志记录
     */
    public void exportSysLog(HttpServletRequest request, HttpServletResponse response, SysOperLogVO sysOperLogVO);

    /**
     * 统计操作日志
     */
    public String statisticsLogInfo(SysOperLogVO sysOperLogVO);

}
