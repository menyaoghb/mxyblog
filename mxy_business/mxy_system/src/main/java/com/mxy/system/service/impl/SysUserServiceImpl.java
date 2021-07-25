package com.mxy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxy.common.core.constant.BaseMessage;
import com.mxy.common.core.utils.ServiceResult;
import com.mxy.system.entity.SysUser;
import com.mxy.system.entity.vo.SysUserVO;
import com.mxy.system.mapper.SysUserMapper;
import com.mxy.system.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-21
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public String getList(SysUserVO sysUserVO) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
        if (StringUtils.isNotEmpty(sysUserVO.getNickName())) {
            queryWrapper.like("nick_name", sysUserVO.getNickName());
        }
        if (StringUtils.isNotEmpty(sysUserVO.getUserName())) {
            queryWrapper.eq("user_name", sysUserVO.getUserName());
        }
        Page<SysUser> page = new Page<>();
        page.setCurrent(sysUserVO.getCurrentPage());
        page.setSize(sysUserVO.getPageSize());
        IPage<SysUser> pageList = this.page(page, queryWrapper);
        return ServiceResult.success(pageList);
    }

    @Override
    public String add(SysUserVO sysUserVO) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserVO, sysUser);
        Boolean result = sysUser.insert();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.INSERT_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.INSERT_FAIL);
        }
    }

    @Override
    public String edit(SysUserVO sysUserVO) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserVO, sysUser);
        Boolean result = sysUser.updateById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.UPDATE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.UPDATE_FAIL);
        }
    }

    @Override
    public String delete(SysUserVO sysUserVO) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserVO, sysUser);
        Boolean result = sysUser.deleteById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.DELETE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.DELETE_FAIL);
        }
    }

}
