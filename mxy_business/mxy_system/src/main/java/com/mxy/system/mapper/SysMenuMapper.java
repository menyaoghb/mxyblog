package com.mxy.system.mapper;

import com.mxy.common.core.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单权限 Mapper 接口
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<Map<String,Object>> getSysMenuTreeData(String parentId);
}
