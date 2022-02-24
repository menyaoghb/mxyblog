package com.mxy.system.info.config;

/**
 * 网络相关信息
 *
 * @author 孟耀
 */
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
    private Long packetsReceiveAmount;

    /**
     * 字节接收量
     */
    private Long bytesReceiveAmount;

    /**
     * 错误数据包接收量
     */
    private Long errorPacketsReceiveAmount;

    /**
     * 数据包发送量
     */
    private Long packetsSendAmount;

    /**
     * 字节发送量
     */
    private Long bytesSendAmount;

    /**
     * 错误数据包发送量
     */
    private Long errorPacketsSendAmount;

    /**
     * 上行bps 单位字节
     */
    private Long uploadBps;

    /**
     * 下行bps 单位字节
     */
    private Long downloadBps;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getPacketsReceiveAmount() {
        return packetsReceiveAmount;
    }

    public void setPacketsReceiveAmount(Long packetsReceiveAmount) {
        this.packetsReceiveAmount = packetsReceiveAmount;
    }

    public Long getBytesReceiveAmount() {
        return bytesReceiveAmount;
    }

    public void setBytesReceiveAmount(Long bytesReceiveAmount) {
        this.bytesReceiveAmount = bytesReceiveAmount;
    }

    public Long getErrorPacketsReceiveAmount() {
        return errorPacketsReceiveAmount;
    }

    public void setErrorPacketsReceiveAmount(Long errorPacketsReceiveAmount) {
        this.errorPacketsReceiveAmount = errorPacketsReceiveAmount;
    }

    public Long getPacketsSendAmount() {
        return packetsSendAmount;
    }

    public void setPacketsSendAmount(Long packetsSendAmount) {
        this.packetsSendAmount = packetsSendAmount;
    }

    public Long getBytesSendAmount() {
        return bytesSendAmount;
    }

    public void setBytesSendAmount(Long bytesSendAmount) {
        this.bytesSendAmount = bytesSendAmount;
    }

    public Long getErrorPacketsSendAmount() {
        return errorPacketsSendAmount;
    }

    public void setErrorPacketsSendAmount(Long errorPacketsSendAmount) {
        this.errorPacketsSendAmount = errorPacketsSendAmount;
    }

    public Long getUploadBps() {
        return uploadBps;
    }

    public void setUploadBps(Long uploadBps) {
        this.uploadBps = uploadBps;
    }

    public Long getDownloadBps() {
        return downloadBps;
    }

    public void setDownloadBps(Long downloadBps) {
        this.downloadBps = downloadBps;
    }
}
