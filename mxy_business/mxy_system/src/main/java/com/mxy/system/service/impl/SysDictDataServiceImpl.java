package com.mxy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mxy.common.core.constant.BaseMessage;
import com.mxy.common.core.entity.SelfUserEntity;
import com.mxy.common.core.entity.SysDictData;
import com.mxy.common.core.utils.ServiceResult;
import com.mxy.system.entity.vo.SysDictDataVO;
import com.mxy.system.mapper.SysDictDataMapper;
import com.mxy.security.common.util.SecurityUtil;
import com.mxy.system.service.SysDictDataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典数据 服务实现类
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {

    @Override
    public String getList(SysDictDataVO sysDictDataVO) {
        QueryWrapper<SysDictData> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(sysDictDataVO.getDictName())) {
            queryWrapper.like("dict_name", sysDictDataVO.getDictName());
        }
        if (StringUtils.isNotEmpty(sysDictDataVO.getDictType())) {
            queryWrapper.eq("dict_type", sysDictDataVO.getDictType());
        }
        if (StringUtils.isNotEmpty(sysDictDataVO.getDictValue())) {
            queryWrapper.eq("dict_value", sysDictDataVO.getDictValue());
        }
        queryWrapper.orderByAsc("dict_Sort");
        Page<SysDictData> page = new Page<>();
        page.setCurrent(sysDictDataVO.getCurrentPage());
        page.setSize(sysDictDataVO.getPageSize());
        IPage<SysDictData> pageList = this.page(page, queryWrapper);
        return ServiceResult.success(pageList);
    }

    @Override
    public String add(SysDictDataVO sysDictDataVO) {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        SysDictData sysDictData = new SysDictData();
        BeanUtils.copyProperties(sysDictDataVO, sysDictData);
        sysDictData.setCreateUser(userDetails.getUsername());
        Boolean result = sysDictData.insert();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.INSERT_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.INSERT_FAIL);
        }
    }

    @Override
    public String edit(SysDictDataVO sysDictDataVO) {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        SysDictData sysDictData = new SysDictData();
        BeanUtils.copyProperties(sysDictDataVO, sysDictData);
        sysDictData.setUpdateUser(userDetails.getUsername());
        Boolean result = sysDictData.updateById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.UPDATE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.UPDATE_FAIL);
        }
    }

    @Override
    public String delete(SysDictDataVO sysDictDataVO) {
        SysDictData sysDictData = new SysDictData();
        BeanUtils.copyProperties(sysDictDataVO, sysDictData);
        Boolean result = sysDictData.deleteById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.DELETE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.DELETE_FAIL);
        }
    }

    @Override
    public String getDicData(SysDictDataVO sysDictDataVO) {
        List<Map<String, String>> maps = this.baseMapper.selectDicData(sysDictDataVO);
        return ServiceResult.success(maps);
    }


    /**
     * 字典集合(List)：字典类型
     *
     * @param dictType 字典类型
     * @return 字典集合
     */
    @Override
    public List<SysDictData> getDictListDataByType(String dictType) {
        return this.baseMapper.selectList(Wrappers.<SysDictData>lambdaQuery().eq(SysDictData::getDictType, dictType).eq(SysDictData::getIsDelete, "0").eq(SysDictData::getStatus, "0").orderByAsc(SysDictData::getDictSort));
    }


    /**
     * 字典实体：字典类型、字典名称
     *
     * @param dictType 字典类型
     * @param dictName 字典名称
     * @return 字典实体
     */
    @Override
    public SysDictData getDicDataByTypeAndName(String dictType, String dictName) {
        return this.baseMapper.selectOne(Wrappers.<SysDictData>lambdaQuery().eq(SysDictData::getDictType, dictType).eq(SysDictData::getDictName, dictName).eq(SysDictData::getIsDelete, "0").eq(SysDictData::getStatus, "0"));
    }


    /**
     * 字典值(value)：字典类型、字典名称
     *
     * @param dictType 字典类型
     * @param dictName 字典名称
     * @return 字典值
     */
    @Override
    public String getDictValueByTypeAndName(String dictType, String dictName) {
        SysDictData dictData = this.baseMapper.selectOne(Wrappers.<SysDictData>lambdaQuery().eq(SysDictData::getDictType, dictType).eq(SysDictData::getDictName, dictName).eq(SysDictData::getIsDelete, "0").eq(SysDictData::getStatus, "0"));
        return dictData != null ? dictData.getDictValue() : "";
    }


    /**
     * 字典实体：字典类型、字典值
     *
     * @param dictType  字典类型
     * @param dictValue 字典值
     * @return 字典实体
     */
    @Override
    public SysDictData getDicDataByTypeAndValue(String dictType, String dictValue) {
        return this.baseMapper.selectOne(Wrappers.<SysDictData>lambdaQuery().eq(SysDictData::getDictType, dictType).eq(SysDictData::getDictValue, dictValue).eq(SysDictData::getIsDelete, "0").eq(SysDictData::getStatus, "0"));
    }


    /**
     * 字典名称(name)：字典类型、字典值
     *
     * @param dictType  字典类型
     * @param dictValue 字典值
     * @return 字典名称
     */
    @Override
    public String getDictNameByTypeAndValue(String dictType, String dictValue) {
        SysDictData dictData = this.baseMapper.selectOne(Wrappers.<SysDictData>lambdaQuery().eq(SysDictData::getDictType, dictType).eq(SysDictData::getDictValue, dictValue).eq(SysDictData::getIsDelete, "0").eq(SysDictData::getStatus, "0"));
        return dictData != null ? dictData.getDictName() : "";
    }

}
