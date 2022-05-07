package com.mxy.info.config;

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
    private String total;

    /**
     * 剩余磁盘大小 单位字节
     */
    private String free;

    /**
     * 已经使用磁盘量 单位字节
     */
    private String used;

    /**
     * 磁盘使用率
     */
    private String usage;

}
