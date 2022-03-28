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
 * 收藏书签
 * </p>
 *
 * @author 孟小耀
 * @since 2022-03-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_bookmarks")
@ApiModel(value="SysBookmarks对象", description="收藏书签")
public class SysBookmarks extends BaseEntity<SysBookmarks> {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 书签链接
     */
    private String url;

    /**
     * 书签名称
     */
    private String name;

    /**
     * 书签分类
     */
    private String type;

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
        return this.id;
    }

}
