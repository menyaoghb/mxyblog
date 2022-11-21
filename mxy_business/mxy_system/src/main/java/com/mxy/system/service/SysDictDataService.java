package com.mxy.system.service;

import com.mxy.common.core.entity.SysDictData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mxy.system.entity.vo.SysDictDataVO;

import java.util.List;

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
     * 查询字典数据列表
     */
    public String getList(SysDictDataVO sysDictDataVO);

    /**
     * 字典数据
     */
    public String getDicData(SysDictDataVO sysDictDataVO);

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

    /**
     * 字典集合(List)：字典类型
     *
     * @param dictType 字典类型
     * @return 字典集合
     */
    public List<SysDictData> getDictListDataByType(String dictType);

    /**
     * 字典实体：字典类型、字典名称
     *
     * @param dictType 字典类型
     * @param dictName 字典名称
     * @return 字典实体
     */
    public SysDictData getDicDataByTypeAndName(String dictType, String dictName);

    /**
     * 字典值(value)：字典类型、字典名称
     *
     * @param dictType 字典类型
     * @param dictName 字典名称
     * @return 字典值
     */
    public String getDictValueByTypeAndName(String dictType, String dictName);

    /**
     * 字典实体：字典类型、字典值
     *
     * @param dictType  字典类型
     * @param dictValue 字典值
     * @return 字典实体
     */
    public SysDictData getDicDataByTypeAndValue(String dictType, String dictValue);

    /**
     * 字典名称(name)：字典类型、字典值
     *
     * @param dictType  字典类型
     * @param dictValue 字典值
     * @return 字典名称
     */
    public String getDictNameByTypeAndValue(String dictType, String dictValue);


}
