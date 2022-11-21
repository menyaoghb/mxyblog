package com.mxy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mxy.common.core.constant.BaseMessage;
import com.mxy.common.core.entity.SelfUserEntity;
import com.mxy.common.core.entity.SysConfig;
import com.mxy.common.core.entity.SysDictData;
import com.mxy.common.core.utils.ServiceResult;
import com.mxy.system.entity.vo.SysConfigVO;
import com.mxy.system.mapper.SysConfigMapper;
import com.mxy.security.common.util.SecurityUtil;
import com.mxy.system.service.SysConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统参数配置 服务实现类
 * </p>
 *
 * @author 孟小耀
 * @since 2022-02-10
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    @Override
    public String getList(SysConfigVO sysConfigVO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (StringUtils.isNotEmpty(sysConfigVO.getConfigName())) {
            queryWrapper.eq("config_name", sysConfigVO.getConfigName());
        }
        if (StringUtils.isNotEmpty(sysConfigVO.getConfigValue())) {
            queryWrapper.eq("config_value", sysConfigVO.getConfigValue());
        }
        queryWrapper.orderByDesc("create_time");
        Page<SysConfig> page = new Page<>();
        page.setCurrent(sysConfigVO.getCurrentPage());
        page.setSize(sysConfigVO.getPageSize());
        IPage<SysConfig> pageList = this.page(page, queryWrapper);
        return ServiceResult.success(pageList);
    }

    @Override
    public String add(SysConfigVO sysConfigVO) {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        SysConfig sysConfig = new SysConfig();
        BeanUtils.copyProperties(sysConfigVO, sysConfig);
        sysConfig.setCreateUser(userDetails.getUsername());
        Boolean result = sysConfig.insert();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.INSERT_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.INSERT_FAIL);
        }
    }

    @Override
    public String edit(SysConfigVO sysConfigVO) {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        SysConfig sysConfig = new SysConfig();
        BeanUtils.copyProperties(sysConfigVO, sysConfig);
        sysConfig.setUpdateUser(userDetails.getUsername());
        Boolean result = sysConfig.updateById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.UPDATE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.UPDATE_FAIL);
        }
    }

    @Override
    public String delete(SysConfigVO sysConfigVO) {
        SysConfig sysConfig = new SysConfig();
        BeanUtils.copyProperties(sysConfigVO, sysConfig);
        Boolean result = sysConfig.deleteById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.DELETE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.DELETE_FAIL);
        }
    }

    /**
     * 获取系统配置-参数值
     *
     * @param configType 参数类型
     * @return 参数值
     */
    @Override
    public String getConfigValueByType(String configType) {
        SysConfig data = this.baseMapper.selectOne(Wrappers.<SysConfig>lambdaQuery().eq(SysConfig::getConfigType, configType).eq(SysConfig::getIsDelete, "0").eq(SysConfig::getStatus, "0"));
        return data != null ? data.getConfigValue() : "";
    }

}
