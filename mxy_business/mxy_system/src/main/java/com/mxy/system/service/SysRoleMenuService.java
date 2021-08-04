package com.mxy.system.service;

import com.mxy.common.core.entity.SysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mxy.system.entity.vo.SysRoleMenuVO;

/**
 * <p>
 * 角色和菜单关联 服务类
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 获取角色和菜单关联列表
     */
    public String getList(SysRoleMenuVO sysRoleMenuVO);

    /**
    * 新增角色和菜单关联
    */
    public String add(SysRoleMenuVO sysRoleMenuVO);

    /**
    * 编辑角色和菜单关联
    */
    public String edit(SysRoleMenuVO sysRoleMenuVO);

    /**
    * 删除角色和菜单关联
    */
    public String delete(SysRoleMenuVO sysRoleMenuVO);

}
