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
 * 文章
 * </p>
 *
 * @author 孟小耀
 * @since 2021-08-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_article")
@ApiModel(value="SysArticle对象", description="文章")
public class SysArticle extends BaseEntity<SysArticle> {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章简介
     */
    private String summary;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章分类
     */
    private String classify;

    /**
     * 浏览量
     */
    private Integer pageViews;

    /**
     * 标题图片路径
     */
    private String filePath;

    /**
     * 作者
     */
    private String author;

    /**
     * 文章来源
     */
    private String source;

    /**
     * 排序
     */
    private Integer sort;

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
