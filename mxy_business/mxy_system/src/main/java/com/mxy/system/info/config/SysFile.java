package com.mxy.system.info.config;

import lombok.Data;

/**
 * 系统文件相关信息
 *
 * @author 孟耀
 */
@Data
public class SysFile {
    /**
     * 盘符路径
     */
    private String dirName;

    /**
     * 盘符类型
     */
    private String sysTypeName;

    /**
     * 盘符名称
     */
    private String typeName;

    /**
     * 总磁盘大小 单位字节
     */
    private Long total;

    /**
     * 剩余磁盘大小 单位字节
     */
    private Long free;

    /**
     * 已经使用磁盘量 单位字节
     */
    private Long used;

    /**
     * 磁盘使用率
     */
    private double usage;

}
