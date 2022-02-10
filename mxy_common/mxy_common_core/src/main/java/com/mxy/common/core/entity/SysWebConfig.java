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
 * 网站参数配置
 * </p>
 *
 * @author 孟小耀
 * @since 2021-08-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_web_config")
@ApiModel(value="SysWebConfig对象", description="网站参数配置")
public class SysWebConfig extends BaseEntity<SysWebConfig> {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 值
     */
    private String value;

    /**
     * 标识
     */
    private String sign;

    /**
     * 类型
     */
    private String type;

    /**
     * 状态（0已发布 1未发布）
     */
    private String status;

    /**
     * 是否删除（0未删除 1已删除）
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
        return this.id;
    }

}
