package com.mxy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mxy.common.core.constant.BaseMessage;
import com.mxy.common.core.entity.SelfUserEntity;
import com.mxy.common.core.entity.SysEsData;
import com.mxy.common.core.utils.ServiceResult;
import com.mxy.security.common.util.SecurityUtil;
import com.mxy.system.entity.vo.SysEsDataVO;
import com.mxy.system.mapper.SysEsDataMapper;
import com.mxy.system.service.SysEsDataService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * ES数据存储 服务实现类
 * </p>
 *
 * @author 孟小耀
 * @since 2022-05-09
 */
@Service
public class SysEsDataServiceImpl extends ServiceImpl<SysEsDataMapper, SysEsData> implements SysEsDataService {

    @Override
    public String getList(SysEsDataVO sysEsDataVO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        Page<SysEsData> page = new Page<>();
        page.setCurrent(sysEsDataVO.getCurrentPage());
        page.setSize(sysEsDataVO.getPageSize());
        IPage<SysEsData> pageList = this.page(page, queryWrapper);
        return ServiceResult.success(pageList);
    }

    @Override
    public String add(SysEsDataVO sysEsDataVO) {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        SysEsData sysEsData = new SysEsData();
        BeanUtils.copyProperties(sysEsDataVO, sysEsData);
        sysEsData.setCreateUser(userDetails.getUsername());
        Boolean result = sysEsData.insert();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.INSERT_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.INSERT_FAIL);
        }
    }

    @Override
    public String edit(SysEsDataVO sysEsDataVO) {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        SysEsData sysEsData = new SysEsData();
        BeanUtils.copyProperties(sysEsDataVO, sysEsData);
        sysEsData.setUpdateUser(userDetails.getUsername());
        Boolean result = sysEsData.updateById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.UPDATE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.UPDATE_FAIL);
        }
    }

    @Override
    public String delete(SysEsDataVO sysEsDataVO) {
        SysEsData sysEsData = new SysEsData();
        BeanUtils.copyProperties(sysEsDataVO, sysEsData);
        Boolean result = sysEsData.deleteById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.DELETE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.DELETE_FAIL);
        }
    }

}
