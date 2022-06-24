package com.mxy.system.service;

import com.mxy.common.core.entity.SysLoginLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mxy.system.entity.vo.SysLoginLogVO;

/**
 * <p>
 * 登录日志 服务类
 * </p>
 *
 * @author 孟小耀
 * @since 2022-06-24
 */
public interface SysLoginLogService extends IService<SysLoginLog> {

    /**
     * 获取登录日志列表
     */
    public String getList(SysLoginLogVO sysLoginLogVO);

    /**
    * 新增登录日志
    */
    public String add(SysLoginLogVO sysLoginLogVO);

    /**
    * 编辑登录日志
    */
    public String edit(SysLoginLogVO sysLoginLogVO);

    /**
    * 删除登录日志
    */
    public String delete(SysLoginLogVO sysLoginLogVO);

}
