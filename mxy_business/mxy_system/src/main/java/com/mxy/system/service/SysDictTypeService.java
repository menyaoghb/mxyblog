package com.mxy.system.service;

import com.mxy.common.core.entity.SysDictType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mxy.system.entity.vo.SysDictTypeVO;

/**
 * <p>
 * 字典类型 服务类
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
public interface SysDictTypeService extends IService<SysDictType> {

    /**
     * 获取字典类型列表
     */
    public String getList(SysDictTypeVO sysDictTypeVO);

    /**
    * 新增字典类型
    */
    public String add(SysDictTypeVO sysDictTypeVO);

    /**
    * 编辑字典类型
    */
    public String edit(SysDictTypeVO sysDictTypeVO);

    /**
    * 删除字典类型
    */
    public String delete(SysDictTypeVO sysDictTypeVO);

}
