package com.mxy.system.service;

import com.mxy.common.core.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mxy.system.entity.vo.SysUserVO;

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

}
