package com.mxy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mxy.common.core.constant.BaseMessage;
import com.mxy.common.core.entity.SelfUserEntity;
import com.mxy.common.core.entity.SysMenu;
import com.mxy.common.core.utils.ServiceResult;
import com.mxy.system.entity.vo.SysMenuVO;
import com.mxy.system.mapper.SysMenuMapper;
import com.mxy.system.security.common.util.SecurityUtil;
import com.mxy.system.service.SysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
        SelfUserEntity userInfo = SecurityUtil.getUserInfo();
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(sysMenuVO, sysMenu);
        sysMenu.setCreateUser(userInfo.getUsername());
        Boolean result = sysMenu.insert();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.INSERT_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.INSERT_FAIL);
        }
    }

    @Override
    public String edit(SysMenuVO sysMenuVO) {
        SelfUserEntity userInfo = SecurityUtil.getUserInfo();
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(sysMenuVO, sysMenu);
        sysMenu.setUpdateUser(userInfo.getUsername());
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

    @Override
    public String treeData() {
        //获取父节点数据
        List<Map<String, Object>> mapList = this.baseMapper.getSysMenuTreeData("0");
        return ServiceResult.success(treeData(mapList));
    }

    public List<Map<String, Object>> treeData(List<Map<String, Object>> mapList) {
        mapList.forEach(map -> {
            List<Map<String, Object>> childList = this.baseMapper.getSysMenuTreeData(String.valueOf(map.get("id")));
            if (childList != null) {
                map.put("children", childList);
                childList = treeData(childList);
            }
        });
        return mapList;
    }

}
