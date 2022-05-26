package com.mxy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mxy.common.core.constant.BaseMessage;
import com.mxy.common.core.entity.SelfUserEntity;
import com.mxy.common.core.entity.SysCountry;
import com.mxy.common.core.utils.ServiceResult;
import com.mxy.security.common.util.SecurityUtil;
import com.mxy.system.entity.vo.SysCountryVO;
import com.mxy.system.mapper.SysCountryMapper;
import com.mxy.system.service.SysCountryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 国家省分地市区县 服务实现类
 * </p>
 *
 * @author 孟小耀
 * @since 2022-05-10
 */
@Service
public class SysCountryServiceImpl extends ServiceImpl<SysCountryMapper, SysCountry> implements SysCountryService {

    @Override
    public String getList(SysCountryVO sysCountryVO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByAsc("value");
        Page<SysCountry> page = new Page<>();
        page.setCurrent(sysCountryVO.getCurrentPage());
        page.setSize(sysCountryVO.getPageSize());
        IPage<SysCountry> pageList = this.page(page, queryWrapper);
        return ServiceResult.success(pageList);
    }

    @Override
    public String add(SysCountryVO sysCountryVO) {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        SysCountry sysCountry = new SysCountry();
        BeanUtils.copyProperties(sysCountryVO, sysCountry);
        sysCountry.setCreateUser(userDetails.getUsername());
        Boolean result = sysCountry.insert();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.INSERT_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.INSERT_FAIL);
        }
    }

    @Override
    public String edit(SysCountryVO sysCountryVO) {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        SysCountry sysCountry = new SysCountry();
        BeanUtils.copyProperties(sysCountryVO, sysCountry);
        sysCountry.setUpdateUser(userDetails.getUsername());
        Boolean result = sysCountry.updateById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.UPDATE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.UPDATE_FAIL);
        }
    }

    @Override
    public String delete(SysCountryVO sysCountryVO) {
        SysCountry sysCountry = new SysCountry();
        BeanUtils.copyProperties(sysCountryVO, sysCountry);
        Boolean result = sysCountry.deleteById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.DELETE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.DELETE_FAIL);
        }
    }

    @Override
    public List<SysCountry> selectList() {
        return this.baseMapper.selectList(new QueryWrapper<>());
    }

}
