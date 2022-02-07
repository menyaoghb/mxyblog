package com.mxy.system.mapper;

import com.mxy.common.core.entity.BeautifulWords;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 优美的句子 Mapper 接口
 * </p>
 *
 * @author 孟小耀
 * @since 2021-08-06
 */
public interface BeautifulWordsMapper extends BaseMapper<BeautifulWords> {

    /**
     * 查询 标签 字段数据
     */
    public List<Map<String,String>> selectLabData();

    /**
     * 查询 灵感 字段数据
     */
    public List<Map<String,String>> selectInsData();

    /**
     * 查询 灵感 字段数据
     */
    public Map<String,String> selectWordRand();
}
