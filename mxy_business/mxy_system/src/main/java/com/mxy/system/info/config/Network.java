package com.mxy.system.info.config;

import lombok.Data;

/**
 * 网络相关信息
 *
 * @author mengyao
 */
@Data
public class Network {

    /**
     * 网络接口名称
     */
    private String name;

    /**
     * MAC地址
     */
    private String mac;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 数据包接收量
     */
    private String packetsReceiveAmount;

    /**
     * 字节接收量
     */
    private String bytesReceiveAmount;

    /**
     * 错误数据包接收量
     */
    private String errorPacketsReceiveAmount;

    /**
     * 数据包发送量
     */
    private String packetsSendAmount;

    /**
     * 字节发送量
     */
    private String bytesSendAmount;

    /**
     * 错误数据包发送量
     */
    private String errorPacketsSendAmount;

    /**
     * 上行bps 单位字节
     */
    private String uploadBps;

    /**
     * 下行bps 单位字节
     */
    private String downloadBps;
}
