package com.mxy.system.info.config;

import lombok.Data;

import java.util.List;

/**
 * 服务器各项信息
 *
 * @author mengyao
 */
@Data
public class Server {

    /**
     * CPU相关信息
     */
    private Cpu cpu;

    /**
     * 內存相关信息
     */
    private Mem mem;

    /**
     * JVM相关信息
     */
    private Jvm jvm;

    /**
     * 服务器相关信息
     */
    private Sys sys;

    /**
     * 磁盘相关信息
     */
    private List<SysFile> sysFiles;

    /**
     * 网络相关信息
     */
    private Network network;

}
