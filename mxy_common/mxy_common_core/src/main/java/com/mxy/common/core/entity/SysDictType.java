package com.mxy.common.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mxy.common.core.entity.vo.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 字典类型
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict_type")
@ApiModel(value="SysDictType对象", description="字典类型")
public class SysDictType extends BaseEntity<SysDictType> {

    private static final long serialVersionUID=1L;

    /**
     * 字典主键
     */
      @TableId(value = "dict_id", type = IdType.UUID)
    private String dictId;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 状态（0正常 1停用）
     */
    private String status;

    /**
     * 是否删除（0未删除1已删除）
     */
    @TableLogic
    private String isDelete;

    /**
     * 创建者
     */
    private String createUser;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新者
     */
    private String updateUser;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;


    @Override
    protected Serializable pkVal() {
        return this.dictId;
    }

}
