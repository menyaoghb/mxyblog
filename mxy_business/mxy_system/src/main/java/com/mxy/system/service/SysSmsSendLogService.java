package com.mxy.system.service;

import com.mxy.common.core.entity.SysSmsSendLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mxy.system.entity.vo.SysSmsSendLogVO;

/**
 * <p>
 * 短信发送日志 服务类
 * </p>
 *
 * @author 孟小耀
 * @since 2022-09-02
 */
public interface SysSmsSendLogService extends IService<SysSmsSendLog> {

    /**
     * 查询短信发送日志列表
     */
    public String getList(SysSmsSendLogVO sysSmsSendLogVO);

    /**
    * 新增短信发送日志
    */
    public String add(SysSmsSendLogVO sysSmsSendLogVO);

    /**
    * 编辑短信发送日志
    */
    public String edit(SysSmsSendLogVO sysSmsSendLogVO);

    /**
    * 删除短信发送日志
    */
    public String delete(SysSmsSendLogVO sysSmsSendLogVO);

}
