package com.mxy.system.service;

import com.mxy.common.core.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mxy.system.entity.vo.SysUserRoleVO;

/**
 * <p>
 * 用户和角色关联 服务类
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 获取用户和角色关联列表
     */
    public String getList(SysUserRoleVO sysUserRoleVO);

    /**
    * 新增用户和角色关联
    */
    public String add(SysUserRoleVO sysUserRoleVO);

    /**
    * 编辑用户和角色关联
    */
    public String edit(SysUserRoleVO sysUserRoleVO);

    /**
    * 删除用户和角色关联
    */
    public String delete(SysUserRoleVO sysUserRoleVO);

}
