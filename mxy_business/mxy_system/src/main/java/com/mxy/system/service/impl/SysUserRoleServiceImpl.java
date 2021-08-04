package com.mxy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxy.common.core.constant.BaseMessage;
import com.mxy.common.core.utils.ServiceResult;
import com.mxy.common.core.entity.SysUserRole;
import com.mxy.system.entity.vo.SysUserRoleVO;
import com.mxy.system.mapper.SysUserRoleMapper;
import com.mxy.system.service.SysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

/**
 * <p>
 * 用户和角色关联 服务实现类
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Override
    public String getList(SysUserRoleVO sysUserRoleVO) {
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        Page<SysUserRole> page = new Page<>();
        page.setCurrent(sysUserRoleVO.getCurrentPage());
        page.setSize(sysUserRoleVO.getPageSize());
        IPage<SysUserRole> pageList = this.page(page, queryWrapper);
        return ServiceResult.success(pageList);
    }

    @Override
    public String add(SysUserRoleVO sysUserRoleVO) {
        SysUserRole sysUserRole = new SysUserRole();
        BeanUtils.copyProperties(sysUserRoleVO, sysUserRole);
        Boolean result = sysUserRole.insert();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.INSERT_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.INSERT_FAIL);
        }
    }

    @Override
    public String edit(SysUserRoleVO sysUserRoleVO) {
        SysUserRole sysUserRole = new SysUserRole();
        BeanUtils.copyProperties(sysUserRoleVO, sysUserRole);
        Boolean result = sysUserRole.updateById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.UPDATE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.UPDATE_FAIL);
        }
    }

    @Override
    public String delete(SysUserRoleVO sysUserRoleVO) {
        SysUserRole sysUserRole = new SysUserRole();
        BeanUtils.copyProperties(sysUserRoleVO, sysUserRole);
        Boolean result = sysUserRole.deleteById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.DELETE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.DELETE_FAIL);
        }
    }

}
