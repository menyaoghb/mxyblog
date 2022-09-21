package com.mxy.config.binlog;

import lombok.Data;

/**
 * 字段属性对象
 *
 * @author 百度
 * @since 2022/09/21
 **/
@Data
public class Colum {
    public int inx;
    /**
     * 列名
     */
    public String colName;
    /**
     * 类型
     */
    public String dataType;
    /**
     * 数据库
     */
    public String schema;
    /**
     * 表
     */
    public String table;

    public Colum(String schema, String table, int idx, String colName, String dataType) {
        this.schema = schema;
        this.table = table;
        this.colName = colName;
        this.dataType = dataType;
        this.inx = idx;
    }
}

