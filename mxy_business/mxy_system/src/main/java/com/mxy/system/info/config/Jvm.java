package com.mxy.system.info.config;

import lombok.Data;

/**
 * JVM相关信息
 *
 * @author 孟耀
 */
@Data
public class Jvm {
    /**
     * 当前JVM占用的内存总数 单位字节
     */
    private double memoryTotal;

    /**
     * JVM最大可用内存总数 单位字节
     */
    private double maxAvailableMemory;

    /**
     * JVM空闲内存 单位字节
     */
    private double freeMemory;

    /**
     * JVM已用内存 单位字节
     */
    private double usageMemory;

    /**
     * JDK版本
     */
    private String jdkVersion;

    /**
     * JRE路径
     */
    private String jreHome;

    /**
     * 启动时间 单位毫秒
     */
    private long startTime;

    /**
     * 运行时长 单位毫秒
     */
    private long runTime;

}
