package com.mxy.system.service;

import com.mxy.common.core.entity.SysMusic;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mxy.system.entity.vo.SysMusicVO;

/**
 * <p>
 * 音乐 服务类
 * </p>
 *
 * @author 孟小耀
 * @since 2022-09-13
 */
public interface SysMusicService extends IService<SysMusic> {

    /**
     * 查询音乐列表
     */
    public String getList(SysMusicVO sysMusicVO);

    /**
    * 新增音乐
    */
    public String add(SysMusicVO sysMusicVO);

    /**
    * 编辑音乐
    */
    public String edit(SysMusicVO sysMusicVO);

    /**
    * 删除音乐
    */
    public String delete(SysMusicVO sysMusicVO);

}
