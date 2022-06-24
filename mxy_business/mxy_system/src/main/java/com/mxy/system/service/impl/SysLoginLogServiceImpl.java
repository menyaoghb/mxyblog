package com.mxy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxy.common.core.constant.BaseMessage;
import com.mxy.common.core.entity.SelfUserEntity;
import com.mxy.common.core.utils.ServiceResult;
import com.mxy.security.common.util.SecurityUtil;
import com.mxy.common.core.entity.SysLoginLog;
import com.mxy.system.entity.vo.SysLoginLogVO;
import com.mxy.system.mapper.SysLoginLogMapper;
import com.mxy.system.service.SysLoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

/**
 * <p>
 * 登录日志 服务实现类
 * </p>
 *
 * @author 孟小耀
 * @since 2022-06-24
 */
@Service
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements SysLoginLogService {

    @Override
    public String getList(SysLoginLogVO sysLoginLogVO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        Page<SysLoginLog> page = new Page<>();
        page.setCurrent(sysLoginLogVO.getCurrentPage());
        page.setSize(sysLoginLogVO.getPageSize());
        IPage<SysLoginLog> pageList = this.page(page, queryWrapper);
        return ServiceResult.success(pageList);
    }

    @Override
    public String add(SysLoginLogVO sysLoginLogVO) {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        SysLoginLog sysLoginLog = new SysLoginLog();
        BeanUtils.copyProperties(sysLoginLogVO, sysLoginLog);
        Boolean result = sysLoginLog.insert();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.INSERT_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.INSERT_FAIL);
        }
    }

    @Override
    public String edit(SysLoginLogVO sysLoginLogVO) {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        SysLoginLog sysLoginLog = new SysLoginLog();
        BeanUtils.copyProperties(sysLoginLogVO, sysLoginLog);
        Boolean result = sysLoginLog.updateById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.UPDATE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.UPDATE_FAIL);
        }
    }

    @Override
    public String delete(SysLoginLogVO sysLoginLogVO) {
        SysLoginLog sysLoginLog = new SysLoginLog();
        BeanUtils.copyProperties(sysLoginLogVO, sysLoginLog);
        Boolean result = sysLoginLog.deleteById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.DELETE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.DELETE_FAIL);
        }
    }

}
