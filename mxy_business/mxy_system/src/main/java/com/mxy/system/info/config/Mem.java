package com.mxy.system.info.config;

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

}
