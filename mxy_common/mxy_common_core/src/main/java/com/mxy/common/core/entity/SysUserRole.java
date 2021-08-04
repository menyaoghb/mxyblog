package com.mxy.common.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.mxy.common.core.entity.vo.BaseEntity;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户和角色关联
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_role")
@ApiModel(value="SysUserRole对象", description="用户和角色关联")
public class SysUserRole extends BaseEntity<SysUserRole> {

    private static final long serialVersionUID=1L;

    /**
     * 用户ID
     */
      @TableId(value = "user_id", type = IdType.ID_WORKER_STR)
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;


    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

}
