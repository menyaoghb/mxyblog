package com.mxy.system.info.config;

import lombok.Data;

/**
 * CPU相关信息
 *
 * @author SoulBGM
 */
@Data
public class Cpu {
    /**
     * CPU核心数
     */
    private int cpuNum;

    /**
     * CPU总的使用率
     */
    private double totalUsed;

    /**
     * CPU系统使用率
     */
    private double sysUsed;

    /**
     * CPU用户使用率
     */
    private double userUsed;

    /**
     * CPU当前等待率
     */
    private double wait;

    /**
     * CPU当前空闲率
     */
    private double free;

}
