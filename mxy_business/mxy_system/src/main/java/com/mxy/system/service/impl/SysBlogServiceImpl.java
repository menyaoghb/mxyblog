package com.mxy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mxy.common.core.constant.BaseMessage;
import com.mxy.common.core.entity.SelfUserEntity;
import com.mxy.common.core.entity.SysBlog;
import com.mxy.common.core.utils.ServiceResult;
import com.mxy.security.common.util.SecurityUtil;
import com.mxy.system.entity.vo.SysBlogVO;
import com.mxy.system.mapper.SysBlogMapper;
import com.mxy.system.service.SysBlogService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 博客 服务实现类
 * </p>
 *
 * @author 孟小耀
 * @since 2022-06-14
 */
@Service
public class SysBlogServiceImpl extends ServiceImpl<SysBlogMapper, SysBlog> implements SysBlogService {

    @Override
    public String getList(SysBlogVO sysBlogVO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("type", sysBlogVO.getType());
        Page<SysBlog> page = new Page<>();
        page.setCurrent(sysBlogVO.getCurrentPage());
        page.setSize(sysBlogVO.getPageSize());
        IPage<SysBlog> pageList = this.page(page, queryWrapper);
        return ServiceResult.success(pageList);
    }

    @Override
    public String add(SysBlogVO sysBlogVO) {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        SysBlog sysBlog = new SysBlog();
        BeanUtils.copyProperties(sysBlogVO, sysBlog);
        sysBlog.setCreateUser(userDetails.getUsername());
        Boolean result = sysBlog.insert();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.INSERT_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.INSERT_FAIL);
        }
    }

    @Override
    public String edit(SysBlogVO sysBlogVO) {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        SysBlog sysBlog = new SysBlog();
        BeanUtils.copyProperties(sysBlogVO, sysBlog);
        sysBlog.setUpdateUser(userDetails.getUsername());
        Boolean result = sysBlog.updateById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.UPDATE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.UPDATE_FAIL);
        }
    }

    @Override
    public String delete(SysBlogVO sysBlogVO) {
        SysBlog sysBlog = new SysBlog();
        BeanUtils.copyProperties(sysBlogVO, sysBlog);
        Boolean result = sysBlog.deleteById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.DELETE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.DELETE_FAIL);
        }
    }

    @Override
    public String getDataById(String id) {
        SysBlog sysBlog = this.baseMapper.selectById(id);
        return ServiceResult.success(sysBlog);
    }

}
