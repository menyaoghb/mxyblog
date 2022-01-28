package com.mxy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxy.common.core.constant.BaseMessage;
import com.mxy.common.core.utils.ServiceResult;
import com.mxy.common.core.entity.SysOperLog;
import com.mxy.system.entity.vo.SysOperLogVO;
import com.mxy.system.mapper.SysOperLogMapper;
import com.mxy.system.service.SysOperLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

/**
 * <p>
 * 操作日志记录 服务实现类
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements SysOperLogService {

    @Override
    public String getList(SysOperLogVO sysOperLogVO) {
        QueryWrapper<SysOperLog> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(sysOperLogVO.getTitle())) {
            queryWrapper.like("title", sysOperLogVO.getTitle());
        }
        if (null != sysOperLogVO.getBusinessType()) {
            queryWrapper.eq("business_type", sysOperLogVO.getBusinessType());
        }
        if (StringUtils.isNotEmpty(sysOperLogVO.getStartTime())) {
            queryWrapper.ge("oper_time", sysOperLogVO.getStartTime());
            queryWrapper.le("oper_time", sysOperLogVO.getEndTime());
        }
        queryWrapper.orderByDesc("oper_time");
        Page<SysOperLog> page = new Page<>();
        page.setCurrent(sysOperLogVO.getCurrentPage());
        page.setSize(sysOperLogVO.getPageSize());
        IPage<SysOperLog> pageList = this.page(page, queryWrapper);
        return ServiceResult.success(pageList);
    }

    @Override
    public String add(SysOperLogVO sysOperLogVO) {
        SysOperLog sysOperLog = new SysOperLog();
        BeanUtils.copyProperties(sysOperLogVO, sysOperLog);
        Boolean result = sysOperLog.insert();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.INSERT_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.INSERT_FAIL);
        }
    }

    @Override
    public String edit(SysOperLogVO sysOperLogVO) {
        SysOperLog sysOperLog = new SysOperLog();
        BeanUtils.copyProperties(sysOperLogVO, sysOperLog);
        Boolean result = sysOperLog.updateById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.UPDATE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.UPDATE_FAIL);
        }
    }

    @Override
    public String delete(SysOperLogVO sysOperLogVO) {
        SysOperLog sysOperLog = new SysOperLog();
        BeanUtils.copyProperties(sysOperLogVO, sysOperLog);
        Boolean result = sysOperLog.deleteById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.DELETE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.DELETE_FAIL);
        }
    }

}
