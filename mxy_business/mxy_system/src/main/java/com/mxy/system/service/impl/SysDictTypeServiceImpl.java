package com.mxy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mxy.common.core.constant.BaseMessage;
import com.mxy.common.core.entity.SelfUserEntity;
import com.mxy.common.core.entity.SysDictType;
import com.mxy.common.core.utils.ServiceResult;
import com.mxy.system.entity.vo.SysDictTypeVO;
import com.mxy.system.mapper.SysDictTypeMapper;
import com.mxy.system.security.common.util.SecurityUtil;
import com.mxy.system.service.SysDictTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 字典类型 服务实现类
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

    @Override
    public String getList(SysDictTypeVO sysDictTypeVO) {
        QueryWrapper<SysDictType> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(sysDictTypeVO.getDictName())) {
            queryWrapper.like("dict_name", sysDictTypeVO.getDictName());
        }
        if (StringUtils.isNotEmpty(sysDictTypeVO.getDictType())) {
            queryWrapper.eq("dict_type", sysDictTypeVO.getDictType());
        }
        Page<SysDictType> page = new Page<>();
        page.setCurrent(sysDictTypeVO.getCurrentPage());
        page.setSize(sysDictTypeVO.getPageSize());
        IPage<SysDictType> pageList = this.page(page, queryWrapper);
        return ServiceResult.success(pageList);
    }

    @Override
    public String add(SysDictTypeVO sysDictTypeVO) {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        SysDictType sysDictType = new SysDictType();
        BeanUtils.copyProperties(sysDictTypeVO, sysDictType);
        sysDictType.setCreateUser(userDetails.getUsername());
        Boolean result = sysDictType.insert();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.INSERT_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.INSERT_FAIL);
        }
    }

    @Override
    public String edit(SysDictTypeVO sysDictTypeVO) {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        SysDictType sysDictType = new SysDictType();
        BeanUtils.copyProperties(sysDictTypeVO, sysDictType);
        sysDictType.setUpdateUser(userDetails.getUsername());
        Boolean result = sysDictType.updateById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.UPDATE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.UPDATE_FAIL);
        }
    }

    @Override
    public String delete(SysDictTypeVO sysDictTypeVO) {
        SysDictType sysDictType = new SysDictType();
        BeanUtils.copyProperties(sysDictTypeVO, sysDictType);
        Boolean result = sysDictType.deleteById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.DELETE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.DELETE_FAIL);
        }
    }

}
