package com.mxy.generator.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 代码生成 服务类
 * </p>
 *
 * @author 孟小耀
 */
@Mapper
public interface CodeGeneratorService{

    @Select("select table_name as lable,table_name as value from information_schema.tables where table_schema='mxy_blog'")
    List<Map<String,String>> getTableName();

}
