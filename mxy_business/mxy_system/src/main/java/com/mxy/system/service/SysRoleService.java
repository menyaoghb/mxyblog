package com.mxy.system.service;

import com.mxy.common.core.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mxy.system.entity.vo.SysRoleVO;

/**
 * <p>
 * 角色信息 服务类
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 获取角色信息列表
     */
    public String getList(SysRoleVO sysRoleVO);

    /**
    * 新增角色信息
    */
    public String add(SysRoleVO sysRoleVO);

    /**
    * 编辑角色信息
    */
    public String edit(SysRoleVO sysRoleVO);

    /**
    * 删除角色信息
    */
    public String delete(SysRoleVO sysRoleVO);

}
