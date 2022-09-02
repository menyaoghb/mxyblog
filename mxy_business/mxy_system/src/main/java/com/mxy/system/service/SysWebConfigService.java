package com.mxy.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mxy.common.core.entity.SysWebConfig;
import com.mxy.system.entity.vo.SysWebConfigVO;

/**
 * <p>
 * 网站参数配置 服务类
 * </p>
 *
 * @author 孟小耀
 * @since 2021-08-17
 */
public interface SysWebConfigService extends IService<SysWebConfig> {

    /**
     * 查询网站参数配置列表
     */
    public String getList(SysWebConfigVO sysWebConfigVO);

    /**
    * 新增网站参数配置
    */
    public String add(SysWebConfigVO sysWebConfigVO);

    /**
    * 编辑网站参数配置
    */
    public String edit(SysWebConfigVO sysWebConfigVO);

    /**
    * 删除网站参数配置
    */
    public String delete(SysWebConfigVO sysWebConfigVO);

}
