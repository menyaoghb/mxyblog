package com.mxy.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mxy.common.core.entity.SysDictData;
import com.mxy.system.entity.vo.SysDictDataVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典数据 Mapper 接口
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
public interface SysDictDataMapper extends BaseMapper<SysDictData> {

    /**
     * 查询 字典 数据
     */
    public List<Map<String,String>> selectDicData(SysDictDataVO sysDictDataVO);

}
