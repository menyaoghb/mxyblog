package com.mxy.system.entity.vo;

import com.mxy.common.core.entity.SysArticle;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.mxy.common.core.entity.vo.BaseVO;

import java.util.Date;
/**
 * <p>
 * 文章-VO
 * </p>
 *
 * @author 孟小耀
 * @since 2021-08-17
 */
@Data
public class SysArticleVO extends BaseVO<SysArticle> {

    /**
     * 主键
     */
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
