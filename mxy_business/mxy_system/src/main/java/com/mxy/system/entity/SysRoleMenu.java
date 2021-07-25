package com.mxy.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.mxy.common.core.entity.BaseEntity;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.mxy.common.core.entity.BaseEntity;

/**
 * <p>
 * 角色和菜单关联
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role_menu")
@ApiModel(value="SysRoleMenu对象", description="角色和菜单关联")
public class SysRoleMenu extends BaseEntity<SysRoleMenu> {

    private static final long serialVersionUID=1L;

    /**
     * 角色ID
     */
      @TableId(value = "role_id", type = IdType.ID_WORKER_STR)
    private Long roleId;

    /**
     * 菜单ID
     */
    private Long menuId;


    @Override
    protected Serializable pkVal() {
        return this.roleId;
    }

}
