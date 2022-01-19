package com.mxy.system.entity.vo;

import com.mxy.common.core.entity.SysRoleMenu;
import com.mxy.common.core.entity.vo.BaseVO;
import lombok.Data;

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
     * ID
     */
    private String id;

    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 菜单ID
     */
    private Long menuId;

}
