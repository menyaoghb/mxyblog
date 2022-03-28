package com.mxy.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mxy.common.core.entity.SysBookmarks;
import com.mxy.system.entity.vo.SysBookmarksVO;

/**
 * <p>
 * 收藏书签 服务类
 * </p>
 *
 * @author 孟小耀
 * @since 2022-03-07
 */
public interface SysBookmarksService extends IService<SysBookmarks> {

    /**
     * 获取收藏书签列表
     */
    public String getList(SysBookmarksVO sysBookmarksVO);

    /**
    * 新增收藏书签
    */
    public String add(SysBookmarksVO sysBookmarksVO);

    /**
    * 编辑收藏书签
    */
    public String edit(SysBookmarksVO sysBookmarksVO);

    /**
    * 删除收藏书签
    */
    public String delete(SysBookmarksVO sysBookmarksVO);

}
