package com.mxy.system.service;

import com.mxy.system.entity.SysBlog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mxy.system.entity.vo.SysBlogVO;

/**
 * <p>
 * 博客 服务类
 * </p>
 *
 * @author 孟小耀
 * @since 2022-06-14
 */
public interface SysBlogService extends IService<SysBlog> {

    /**
     * 获取博客列表
     */
    public String getList(SysBlogVO sysBlogVO);

    /**
    * 新增博客
    */
    public String add(SysBlogVO sysBlogVO);

    /**
    * 编辑博客
    */
    public String edit(SysBlogVO sysBlogVO);

    /**
    * 删除博客
    */
    public String delete(SysBlogVO sysBlogVO);

    /**
     * 博客详情
     */
    public String getDataById(String id);

}
