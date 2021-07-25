package com.mxy.system.service;

import com.mxy.system.entity.SysOperLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mxy.system.entity.vo.SysOperLogVO;

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
     * 获取操作日志记录列表
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

}
