package com.mxy.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mxy.common.core.entity.SysCodeGenerate;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 代码生成 Mapper 接口
 * </p>
 *
 * @author 孟小耀
 * @since 2022-04-18
 */
public interface SysCodeGenerateMapper extends BaseMapper<SysCodeGenerate> {

    List<Map<String,String>> getTableName();
}
