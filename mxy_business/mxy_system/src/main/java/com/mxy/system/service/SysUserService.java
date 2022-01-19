package com.mxy.system.service;

import com.mxy.common.core.entity.SysMenu;
import com.mxy.common.core.entity.SysRole;
import com.mxy.common.core.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mxy.system.entity.vo.SysUserVO;

import java.util.List;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-21
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 获取用户信息列表
     */
    public String getList(SysUserVO sysUserVO);

    /**
    * 新增用户信息
    */
    public String add(SysUserVO sysUserVO);

    /**
    * 编辑用户信息
    */
    public String edit(SysUserVO sysUserVO);

    /**
    * 删除用户信息
    */
    public String delete(SysUserVO sysUserVO);

    /**
     * 重置密码
     */
    public String resetPassword(SysUserVO sysUserVO);

    /**
     * 用户状态改变
     */
    public String editUserStatus(SysUserVO sysUserVO);

    /**
     * 根据用户名查询实体
     * @Author Mxy
     * @CreateTime 2022/01/14 16:30
     * @Param  userName 用户名
     * @Return SysUserEntity 用户实体
     */
    SysUser selectUserByName(String userName);
    /**
     * 根据用户ID查询角色集合
     * @Author Mxy
     * @CreateTime 2022/01/18 18:01
     * @Param  userId 用户ID
     * @Return List<SysRoleEntity> 角色名集合
     */
    List<SysRole> selectSysRoleByUserId(String userId);
    /**
     * 根据用户ID查询权限集合
     * @Author Mxy
     * @CreateTime 2022/01/18 18:01
     * @Param  userId 用户ID
     * @Return List<SysMenuEntity> 角色名集合
     */
    List<SysMenu> selectSysMenuByUserId(String userId);

}
