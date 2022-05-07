package com.mxy.info.config;

import cn.hutool.core.util.NumberUtil;
import lombok.Data;

/**
 * 內存相关信息
 *
 * @author 孟耀
 */
@Data
public class Mem {
    /**
     * 内存总量 单位字节
     */
    private double total;

    /**
     * 已用内存 单位字节
     */
    private double used;

    /**
     * 剩余内存 单位字节
     */
    private double free;

    public double getTotal() {
        return NumberUtil.div(total, (1024 * 1024 * 1024), 2);
    }

    public double getUsed() {
        return NumberUtil.div(used, (1024 * 1024 * 1024), 2);
    }


    public double getFree() {
        return NumberUtil.div(free, (1024 * 1024 * 1024), 2);
    }

    public double getUsage() {
        return NumberUtil.mul(NumberUtil.div(used, total, 4), 100);
    }

}
