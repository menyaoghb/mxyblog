package com.mxy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxy.common.core.constant.BaseMessage;
import com.mxy.common.core.utils.ServiceResult;
import com.mxy.system.entity.SysMenu;
import com.mxy.system.entity.vo.SysMenuVO;
import com.mxy.system.mapper.SysMenuMapper;
import com.mxy.system.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

/**
 * <p>
 * 菜单权限 服务实现类
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public String getList(SysMenuVO sysMenuVO) {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(sysMenuVO.getMenuName())) {
            queryWrapper.like("menu_name", sysMenuVO.getMenuName());
        }
        if (StringUtils.isNotEmpty(sysMenuVO.getMenuType())) {
            queryWrapper.eq("menu_type", sysMenuVO.getMenuType());
        }
        Page<SysMenu> page = new Page<>();
        page.setCurrent(sysMenuVO.getCurrentPage());
        page.setSize(sysMenuVO.getPageSize());
        IPage<SysMenu> pageList = this.page(page, queryWrapper);
        return ServiceResult.success(pageList);
    }

    @Override
    public String add(SysMenuVO sysMenuVO) {
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(sysMenuVO, sysMenu);
        Boolean result = sysMenu.insert();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.INSERT_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.INSERT_FAIL);
        }
    }

    @Override
    public String edit(SysMenuVO sysMenuVO) {
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(sysMenuVO, sysMenu);
        Boolean result = sysMenu.updateById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.UPDATE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.UPDATE_FAIL);
        }
    }

    @Override
    public String delete(SysMenuVO sysMenuVO) {
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(sysMenuVO, sysMenu);
        Boolean result = sysMenu.deleteById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.DELETE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.DELETE_FAIL);
        }
    }

}
