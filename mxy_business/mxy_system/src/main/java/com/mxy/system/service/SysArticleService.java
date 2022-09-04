package com.mxy.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mxy.common.core.entity.SysArticle;
import com.mxy.system.entity.vo.SysArticleVO;

/**
 * <p>
 * 文章 服务类
 * </p>
 *
 * @author 孟小耀
 * @since 2021-08-17
 */
public interface SysArticleService extends IService<SysArticle> {

    /**
     * 查询文章列表
     */
    public String getList(SysArticleVO sysArticleVO);

    /**
     * 查询文章列表（网站）
     */
    public String getWebList(SysArticleVO sysArticleVO);

    /**
    * 新增文章
    */
    public String add(SysArticleVO sysArticleVO);

    /**
    * 编辑文章
    */
    public String edit(SysArticleVO sysArticleVO);

    /**
    * 删除文章
    */
    public String delete(SysArticleVO sysArticleVO);

    /**
     * 查询文章详情
     */
    public String getDataById(String id);

}
