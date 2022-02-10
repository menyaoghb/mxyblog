package com.mxy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mxy.common.core.constant.BaseMessage;
import com.mxy.common.core.entity.SelfUserEntity;
import com.mxy.common.core.entity.SysWebConfig;
import com.mxy.common.core.utils.ServiceResult;
import com.mxy.system.entity.vo.SysWebConfigVO;
import com.mxy.system.mapper.SysWebConfigMapper;
import com.mxy.system.security.common.util.SecurityUtil;
import com.mxy.system.service.SysWebConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 网站参数配置 服务实现类
 * </p>
 *
 * @author 孟小耀
 * @since 2021-08-17
 */
@Service
public class SysWebConfigServiceImpl extends ServiceImpl<SysWebConfigMapper, SysWebConfig> implements SysWebConfigService {

    @Override
    public String getList(SysWebConfigVO sysWebConfigVO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        Page<SysWebConfig> page = new Page<>();
        page.setCurrent(sysWebConfigVO.getCurrentPage());
        page.setSize(sysWebConfigVO.getPageSize());
        IPage<SysWebConfig> pageList = this.page(page, queryWrapper);
        return ServiceResult.success(pageList);
    }

    @Override
    public String add(SysWebConfigVO sysWebConfigVO) {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        SysWebConfig sysWebConfig = new SysWebConfig();
        BeanUtils.copyProperties(sysWebConfigVO, sysWebConfig);
        sysWebConfig.setCreateUser(userDetails.getUsername());
        Boolean result = sysWebConfig.insert();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.INSERT_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.INSERT_FAIL);
        }
    }

    @Override
    public String edit(SysWebConfigVO sysWebConfigVO) {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        SysWebConfig sysWebConfig = new SysWebConfig();
        BeanUtils.copyProperties(sysWebConfigVO, sysWebConfig);
        Boolean result = sysWebConfig.updateById();
        sysWebConfig.setUpdateUser(userDetails.getUsername());
        if (result) {
            return ServiceResult.successMsg(BaseMessage.UPDATE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.UPDATE_FAIL);
        }
    }

    @Override
    public String delete(SysWebConfigVO sysWebConfigVO) {
        SysWebConfig sysWebConfig = new SysWebConfig();
        BeanUtils.copyProperties(sysWebConfigVO, sysWebConfig);
        Boolean result = sysWebConfig.deleteById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.DELETE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.DELETE_FAIL);
        }
    }

}
