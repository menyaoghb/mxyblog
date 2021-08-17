package com.mxy.system.entity.vo;

import java.util.Date;
import lombok.Data;
import com.mxy.common.core.entity.vo.BaseVO;
import com.mxy.common.core.entity.SysRole;

/**
 * <p>
 * 角色信息-VO
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
@Data
public class SysRoleVO extends BaseVO<SysRole> {

    /**
     * 角色ID
     */
    private String roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色权限字符串
     */
    private String roleKey;
    /**
     * 显示顺序
     */
    private Integer roleSort;
    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
    private String dataScope;
    /**
     * 菜单树选择项是否关联显示
     */
    private Boolean menuCheckStrictly;
    /**
     * 角色状态（0正常 1停用）
     */
    private String status;
    /**
     * 是否删除（0未删除1已删除）
     */
    private String isDelete;
    /**
     * 创建者
     */
    private String createUser;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新者
     */
    private String updateUser;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 备注
     */
    private String remark;

}
