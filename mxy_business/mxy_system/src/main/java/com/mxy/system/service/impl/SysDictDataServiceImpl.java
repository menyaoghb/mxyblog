package com.mxy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mxy.common.core.constant.BaseMessage;
import com.mxy.common.core.entity.SelfUserEntity;
import com.mxy.common.core.entity.SysDictData;
import com.mxy.common.core.utils.ServiceResult;
import com.mxy.system.entity.vo.SysDictDataVO;
import com.mxy.system.mapper.SysDictDataMapper;
import com.mxy.system.security.common.util.SecurityUtil;
import com.mxy.system.service.SysDictDataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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

}
