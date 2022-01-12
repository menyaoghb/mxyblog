package com.mxy.system.mapper;

import com.mxy.common.core.entity.SysMenu;
import com.mxy.common.core.entity.SysRole;
import com.mxy.common.core.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户信息 Mapper 接口
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-21
 */
public interface SysUserMapper extends BaseMapper<SysUser> {


    /**
     * 通过用户ID查询角色集合
     * @Author Mxy
     * @CreateTime 2022/01/18 18:01
     * @Param  userId 用户ID
     * @Return List<SysRoleEntity> 角色名集合
     */
    List<SysRole> selectSysRoleByUserId(String userId);
    /**
     * 通过用户ID查询权限集合
     * @Author Mxy
     * @CreateTime 2022/01/18 18:01
     * @Param  userId 用户ID
     * @Return List<SysMenuEntity> 角色名集合
     */
    List<SysMenu> selectSysMenuByUserId(String userId);

}
