package com.mxy.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mxy.common.core.entity.SysPicture;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 图片 Mapper 接口
 * </p>
 *
 * @author 孟小耀
 * @since 2021-08-18
 */
public interface SysPictureMapper extends BaseMapper<SysPicture> {

    /**
     * 统计 图片标签 数据
     */
    public List<Map<String,String>> selectFileTypeData();
}
