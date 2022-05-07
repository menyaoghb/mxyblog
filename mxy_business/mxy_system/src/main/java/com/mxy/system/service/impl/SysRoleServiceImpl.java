package com.mxy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mxy.common.core.constant.BaseMessage;
import com.mxy.common.core.entity.SelfUserEntity;
import com.mxy.common.core.entity.SysRole;
import com.mxy.common.core.entity.SysRoleMenu;
import com.mxy.common.core.utils.ServiceResult;
import com.mxy.system.entity.vo.SysRoleVO;
import com.mxy.system.mapper.SysRoleMapper;
import com.mxy.security.common.util.SecurityUtil;
import com.mxy.system.service.SysRoleService;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色信息 服务实现类
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public String getList(SysRoleVO sysRoleVO) {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(sysRoleVO.getRoleName())) {
            queryWrapper.like("role_name", sysRoleVO.getRoleName());
        }
        queryWrapper.orderByAsc("role_sort");
        Page<SysRole> page = new Page<>();
        page.setCurrent(sysRoleVO.getCurrentPage());
        page.setSize(sysRoleVO.getPageSize());
        IPage<SysRole> pageList = this.page(page, queryWrapper);
        return ServiceResult.success(pageList);
    }

    @Override
    public String getRoles() {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", "0");
        List<SysRole> sysRoles = this.baseMapper.selectList(queryWrapper);
        return ServiceResult.success(sysRoles);
    }

    @Override
    public String add(SysRoleVO sysRoleVO) {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        // 角色标识去重
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysRole::getRoleKey,sysRoleVO.getRoleKey());
        SysRole role =  this.baseMapper.selectOne(queryWrapper);
        if (role!=null){
            return ServiceResult.errorMsg(BaseMessage.INSERT_FAIL_ROLE_REPEAT);
        }
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(sysRoleVO, sysRole);
        sysRole.setCreateUser(userDetails.getUsername());
        Boolean result = sysRole.insert();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.INSERT_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.INSERT_FAIL);
        }
    }

    @Override
    public String edit(SysRoleVO sysRoleVO) {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(sysRoleVO, sysRole);
        sysRole.setUpdateUser(userDetails.getUsername());
        Boolean result = sysRole.updateById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.UPDATE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.UPDATE_FAIL);
        }
    }

    @Override
    public String editRoleStatus(SysRoleVO sysRoleVO) {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        SysRole sysRole = new SysRole();
        sysRole.setRoleId(sysRoleVO.getRoleId());
        sysRole.setStatus(sysRoleVO.getStatus());
        sysRole.setUpdateUser(userDetails.getUsername());
        Boolean result = sysRole.updateById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.UPDATE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.UPDATE_FAIL);
        }
    }

    @Override
    public String delete(SysRoleVO sysRoleVO) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(sysRoleVO, sysRole);
        Boolean result = sysRole.deleteById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.DELETE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.DELETE_FAIL);
        }
    }

    @Override
    public String saveRoleMenu(Map<String, Object> map) {
        String roleId = MapUtils.getString(map,"roleId");
        List<String> list = (List<String>) MapUtils.getObject(map,"menuIds");
        // 更新用户角色关系，先删再新增
        SysRoleMenu sysRoleMenu = new SysRoleMenu();
        QueryWrapper<SysRoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        sysRoleMenu.delete(queryWrapper);
        for (String s : list) {
            SysRoleMenu menu = new SysRoleMenu();
            menu.setRoleId(roleId);
            menu.setMenuId(s);
            menu.insert();
        }
        return ServiceResult.successMsg(BaseMessage.UPDATE_SUCCESS);
    }

}
