package com.mxy.system.entity.vo;

import lombok.Data;
import com.mxy.common.core.entity.vo.BaseVO;
import com.mxy.common.core.entity.SysUserRole;

/**
 * <p>
 * 用户和角色关联-VO
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
@Data

public class SysUserRoleVO extends BaseVO<SysUserRole> {

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 角色ID
     */
    private Long roleId;

}
