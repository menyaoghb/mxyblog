package com.mxy.system.entity.vo;

import com.mxy.common.core.entity.SysUserRole;
import com.mxy.common.core.entity.vo.BaseVO;
import lombok.Data;

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
     * 主键
     */
    private String id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;

}
