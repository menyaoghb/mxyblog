package com.mxy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxy.common.core.constant.BaseMessage;
import com.mxy.common.core.utils.ServiceResult;
import com.mxy.common.core.entity.SysRoleMenu;
import com.mxy.system.entity.vo.SysRoleMenuVO;
import com.mxy.system.mapper.SysRoleMenuMapper;
import com.mxy.system.service.SysRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

/**
 * <p>
 * 角色和菜单关联 服务实现类
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Override
    public String getList(SysRoleMenuVO sysRoleMenuVO) {
        QueryWrapper<SysRoleMenu> queryWrapper = new QueryWrapper<>();
        Page<SysRoleMenu> page = new Page<>();
        page.setCurrent(sysRoleMenuVO.getCurrentPage());
        page.setSize(sysRoleMenuVO.getPageSize());
        IPage<SysRoleMenu> pageList = this.page(page, queryWrapper);
        return ServiceResult.success(pageList);
    }

    @Override
    public String add(SysRoleMenuVO sysRoleMenuVO) {
        SysRoleMenu sysRoleMenu = new SysRoleMenu();
        BeanUtils.copyProperties(sysRoleMenuVO, sysRoleMenu);
        Boolean result = sysRoleMenu.insert();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.INSERT_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.INSERT_FAIL);
        }
    }

    @Override
    public String edit(SysRoleMenuVO sysRoleMenuVO) {
        SysRoleMenu sysRoleMenu = new SysRoleMenu();
        BeanUtils.copyProperties(sysRoleMenuVO, sysRoleMenu);
        Boolean result = sysRoleMenu.updateById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.UPDATE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.UPDATE_FAIL);
        }
    }

    @Override
    public String delete(SysRoleMenuVO sysRoleMenuVO) {
        SysRoleMenu sysRoleMenu = new SysRoleMenu();
        BeanUtils.copyProperties(sysRoleMenuVO, sysRoleMenu);
        Boolean result = sysRoleMenu.deleteById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.DELETE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.DELETE_FAIL);
        }
    }

}
