package com.mxy.info.config;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import lombok.Data;

import java.lang.management.ManagementFactory;
import java.util.Date;

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
    private String startTime;

    /**
     * 运行时长 单位毫秒
     */
    private String runTime;

    public double getMemoryTotal() {
        return NumberUtil.div(memoryTotal, (1024 * 1024), 2);
    }

    public double getMaxAvailableMemory() {
        return NumberUtil.div(maxAvailableMemory, (1024 * 1024), 2);
    }

    public double getFreeMemory() {
        return NumberUtil.div(freeMemory, (1024 * 1024), 2);
    }

    public double getUsageMemory() {
        return NumberUtil.div(memoryTotal - usageMemory, (1024 * 1024), 2);
    }

    public String getStartTime() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        Date date = new Date(time);
        return DateUtil.formatDateTime(date);
    }

    public String getRunTime() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        Date date = new Date(time);
        //运行多少分钟
        long runMS = DateUtil.between(date, new Date(), DateUnit.MS);
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;

        long day = runMS / nd;
        long hour = runMS % nd / nh;
        long min = runMS % nd % nh / nm;
        return day + "天" + hour + "小时" + min + "分钟";
    }
}
