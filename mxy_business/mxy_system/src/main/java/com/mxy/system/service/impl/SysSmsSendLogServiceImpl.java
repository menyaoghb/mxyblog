package com.mxy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxy.common.core.constant.BaseMessage;
import com.mxy.common.core.entity.SelfUserEntity;
import com.mxy.common.core.utils.ServiceResult;
import com.mxy.security.common.util.SecurityUtil;
import com.mxy.common.core.entity.SysSmsSendLog;
import com.mxy.system.entity.vo.SysSmsSendLogVO;
import com.mxy.system.mapper.SysSmsSendLogMapper;
import com.mxy.system.service.SysSmsSendLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

/**
 * <p>
 * 短信发送日志 服务实现类
 * </p>
 *
 * @author 孟小耀
 * @since 2022-09-02
 */
@Service
public class SysSmsSendLogServiceImpl extends ServiceImpl<SysSmsSendLogMapper, SysSmsSendLog> implements SysSmsSendLogService {

    @Override
    public String getList(SysSmsSendLogVO sysSmsSendLogVO) {
        QueryWrapper<SysSmsSendLog> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(sysSmsSendLogVO.getPhone())) {
            queryWrapper.lambda().eq(SysSmsSendLog::getPhone, sysSmsSendLogVO.getPhone());
        }
        queryWrapper.orderByDesc("create_time");
        Page<SysSmsSendLog> page = new Page<>();
        page.setCurrent(sysSmsSendLogVO.getCurrentPage());
        page.setSize(sysSmsSendLogVO.getPageSize());
        IPage<SysSmsSendLog> pageList = this.page(page, queryWrapper);
        return ServiceResult.success(pageList);
    }

    @Override
    public String add(SysSmsSendLogVO sysSmsSendLogVO) {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        SysSmsSendLog sysSmsSendLog = new SysSmsSendLog();
        BeanUtils.copyProperties(sysSmsSendLogVO, sysSmsSendLog);
        Boolean result = sysSmsSendLog.insert();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.INSERT_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.INSERT_FAIL);
        }
    }

    @Override
    public String edit(SysSmsSendLogVO sysSmsSendLogVO) {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        SysSmsSendLog sysSmsSendLog = new SysSmsSendLog();
        BeanUtils.copyProperties(sysSmsSendLogVO, sysSmsSendLog);
        Boolean result = sysSmsSendLog.updateById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.UPDATE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.UPDATE_FAIL);
        }
    }

    @Override
    public String delete(SysSmsSendLogVO sysSmsSendLogVO) {
        SysSmsSendLog sysSmsSendLog = new SysSmsSendLog();
        BeanUtils.copyProperties(sysSmsSendLogVO, sysSmsSendLog);
        Boolean result = sysSmsSendLog.deleteById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.DELETE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.DELETE_FAIL);
        }
    }

}
