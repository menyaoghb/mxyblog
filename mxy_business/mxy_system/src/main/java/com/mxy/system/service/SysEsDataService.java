package com.mxy.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mxy.common.core.entity.SysEsData;
import com.mxy.system.entity.vo.SysEsDataVO;

/**
 * <p>
 * ES数据存储 服务类
 * </p>
 *
 * @author 孟小耀
 * @since 2022-05-09
 */
public interface SysEsDataService extends IService<SysEsData> {

    /**
     * 获取ES数据存储列表
     */
    public String getList(SysEsDataVO sysEsDataVO);

    /**
    * 新增ES数据存储
    */
    public String add(SysEsDataVO sysEsDataVO);

    /**
    * 编辑ES数据存储
    */
    public String edit(SysEsDataVO sysEsDataVO);

    /**
    * 删除ES数据存储
    */
    public String delete(SysEsDataVO sysEsDataVO);

}
