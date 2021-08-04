package com.mxy.system.service;

import com.mxy.common.core.entity.SysDictData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mxy.system.entity.vo.SysDictDataVO;

/**
 * <p>
 * 字典数据 服务类
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
public interface SysDictDataService extends IService<SysDictData> {

    /**
     * 获取字典数据列表
     */
    public String getList(SysDictDataVO sysDictDataVO);

    /**
    * 新增字典数据
    */
    public String add(SysDictDataVO sysDictDataVO);

    /**
    * 编辑字典数据
    */
    public String edit(SysDictDataVO sysDictDataVO);

    /**
    * 删除字典数据
    */
    public String delete(SysDictDataVO sysDictDataVO);

}
