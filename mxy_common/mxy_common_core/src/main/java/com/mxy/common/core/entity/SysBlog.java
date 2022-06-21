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
 * 博客
 * </p>
 *
 * @author 孟小耀
 * @since 2022-06-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_blog")
@ApiModel(value="SysBlog对象", description="博客")
public class SysBlog extends BaseEntity<SysBlog> {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 内容
     */
    private String content;

    /**
     * 图片url
     */
    private String url;

    /**
     * 图片url（多图片JSON）
     */
    private String urlList;

    /**
     * 发布时间
     */
    private String time;

    /**
     * 来源（0原创 1转载）
     */
    private String source;

    /**
     * 博客展示类型（1 2 3）
     */
    private String showType;

    /**
     * 博客类型（1技术博客 2日常博客）
     */
    private String type;

    /**
     * 状态（0上线 1下线）
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
