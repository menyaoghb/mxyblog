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
 * 图片
 * </p>
 *
 * @author 孟小耀
 * @since 2021-08-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_picture")
@ApiModel(value="SysPicture对象", description="图片")
public class SysPicture extends BaseEntity<SysPicture> {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 图片Id
     */
    private String pictureId;

    /**
     * 图片名称
     */
    private String pictureName;

    /**
     * 图片类别
     */
    private String type;

    /**
     * 文件类型（后缀）
     */
    private String fileType;

    /**
     * 状态（0禁用 1启用）
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
