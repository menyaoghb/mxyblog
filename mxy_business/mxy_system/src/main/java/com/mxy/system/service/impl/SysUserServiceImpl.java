package com.mxy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxy.common.core.constant.BaseMessage;
import com.mxy.common.core.entity.SysMenu;
import com.mxy.common.core.entity.SysRole;
import com.mxy.common.core.utils.ServiceResult;
import com.mxy.common.core.entity.SysUser;
import com.mxy.system.entity.vo.SysUserVO;
import com.mxy.system.mapper.SysUserMapper;
import com.mxy.system.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

import java.util.List;

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
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(sysUserVO.getNickName())) {
            queryWrapper.like("nick_name", sysUserVO.getNickName());
        }
        if (StringUtils.isNotEmpty(sysUserVO.getUsername())) {
            queryWrapper.eq("username", sysUserVO.getUsername());
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

    /**
     * 根据用户名查询实体
     * @Author Mxy
     * @CreateTime 2022/01/14 16:30
     * @Param  userName 用户名
     * @Return SysUserEntity 用户实体
     */
    @Override
    public SysUser selectUserByName(String userName) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUser::getUsername,userName);
        return this.baseMapper.selectOne(queryWrapper);
    }
    /**
     * 通过用户ID查询角色集合
     * @Author Mxy
     * @CreateTime 2022/01/18 18:01
     * @Param  userId 用户ID
     * @Return List<SysRoleEntity> 角色名集合
     */
    @Override
    public List<SysRole> selectSysRoleByUserId(String userId) {
        return this.baseMapper.selectSysRoleByUserId(userId);
    }

    /**
     * 根据用户ID查询权限集合
     * @Author Mxy
     * @CreateTime 2022/01/18 18:01
     * @Param userId 用户ID
     * @Return List<SysMenuEntity> 角色名集合
     */
    @Override
    public List<SysMenu> selectSysMenuByUserId(String userId) {
        return this.baseMapper.selectSysMenuByUserId(userId);
    }

}
