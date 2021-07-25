package com.mxy.system.entity.vo;

import lombok.Data;
import com.mxy.common.core.entity.BaseVO;
import com.mxy.system.entity.SysRoleMenu;

/**
 * <p>
 * 角色和菜单关联-VO
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
@Data

public class SysRoleMenuVO extends BaseVO<SysRoleMenu> {

    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 菜单ID
     */
    private Long menuId;

}
