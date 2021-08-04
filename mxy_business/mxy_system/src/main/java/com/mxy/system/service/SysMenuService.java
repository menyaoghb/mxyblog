package com.mxy.system.service;

import com.mxy.common.core.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mxy.system.entity.vo.SysMenuVO;

/**
 * <p>
 * 菜单权限 服务类
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 获取菜单权限列表
     */
    public String getList(SysMenuVO sysMenuVO);

    /**
    * 新增菜单权限
    */
    public String add(SysMenuVO sysMenuVO);

    /**
    * 编辑菜单权限
    */
    public String edit(SysMenuVO sysMenuVO);

    /**
    * 删除菜单权限
    */
    public String delete(SysMenuVO sysMenuVO);

    /**
     * 获取菜单树数据
     */
    public String treeData();

}
