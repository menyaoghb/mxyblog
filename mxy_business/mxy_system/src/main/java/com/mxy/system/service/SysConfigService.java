package com.mxy.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mxy.common.core.entity.SysConfig;
import com.mxy.system.entity.vo.SysConfigVO;

/**
 * <p>
 * 系统参数配置 服务类
 * </p>
 *
 * @author 孟小耀
 * @since 2022-02-10
 */
public interface SysConfigService extends IService<SysConfig> {

    /**
     * 获取系统参数配置列表
     */
    public String getList(SysConfigVO sysConfigVO);

    /**
    * 新增系统参数配置
    */
    public String add(SysConfigVO sysConfigVO);

    /**
    * 编辑系统参数配置
    */
    public String edit(SysConfigVO sysConfigVO);

    /**
    * 删除系统参数配置
    */
    public String delete(SysConfigVO sysConfigVO);

}
