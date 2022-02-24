package com.mxy.system.info.utils;

import com.mxy.system.info.config.*;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 监控服务器工具类
 *
 * @author 孟耀
 */
public class MonitorServerUtil {

    /**
     * 第二次等待获取cpu的时间
     */
    private static final int OSHI_CPU_WAIT_SECOND = 50;

    /**
     * 第二次等待获取网络状态的时间
     */
    private static final int OSHI_NETWORK_WAIT_SECOND = 1000;

    /**
     * 获取CPU次数
     */
    private static final int GET_CPU_COUNT = 3;

    /**
     * 获取服务器运行信息
     *
     * @return 服务器运行信息
     */
    public static Server getServerRunInfo() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        Server server = new Server();
        server.setCpu(getCpu(hal.getProcessor()));
        server.setMem(getMemoryInfo(hal.getMemory()));
        server.setSysFiles(getDiskInfo(si.getOperatingSystem()));
        server.setJvm(getJvmInfo());
        server.setSys(getSystemInfo());
        server.setNetwork(getNetwork(hal));
        return server;
    }

    /**
     * 多次获取CPU求平均值
     * 以防止某个时段负载过高
     *
     * @param processor CentralProcessor对象
     * @return CPU信息
     */
    private static Cpu getCpu(CentralProcessor processor) {
        List<Cpu> cpuList = new ArrayList<>();
        for (int i = 0; i < GET_CPU_COUNT; i++) {
            cpuList.add(getCpuInfo(processor));
        }
        Cpu cpu = new Cpu();
        for (Cpu c : cpuList) {
            cpu.setCpuNum(c.getCpuNum());
            cpu.setTotalUsed(cpu.getTotalUsed() + c.getTotalUsed());
            cpu.setSysUsed(cpu.getSysUsed() + c.getSysUsed());
            cpu.setUserUsed(cpu.getUserUsed() + c.getUserUsed());
            cpu.setWait(cpu.getWait() + c.getWait());
            cpu.setFree(cpu.getFree() + c.getFree());
        }
        cpu.setTotalUsed(cpu.getTotalUsed() / GET_CPU_COUNT);
        cpu.setSysUsed(cpu.getSysUsed() / GET_CPU_COUNT);
        cpu.setUserUsed(cpu.getUserUsed() / GET_CPU_COUNT);
        cpu.setWait(cpu.getWait() / GET_CPU_COUNT);
        cpu.setFree(cpu.getFree() / GET_CPU_COUNT);
        return cpu;
    }

    /**
     * 获取CPU信息
     *
     * @param processor CentralProcessor对象
     * @return CPU信息
     */
    public static Cpu getCpuInfo(CentralProcessor processor) {
        // CPU信息
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(OSHI_CPU_WAIT_SECOND);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long totalUsed = user + nice + cSys + iowait + irq + softirq + steal;
        long totalCpu = totalUsed + idle;
        Cpu cpu = new Cpu();
        cpu.setCpuNum(processor.getLogicalProcessorCount());
        cpu.setTotalUsed(Arith.div((double) totalUsed, (double) totalCpu));
        cpu.setSysUsed(Arith.div((double) cSys, (double) totalCpu));
        cpu.setUserUsed(Arith.div((double) user, (double) totalCpu));
        cpu.setWait(Arith.div((double) iowait, (double) totalCpu));
        cpu.setFree(Arith.div((double) idle, (double) totalCpu));
        return cpu;
    }

    /**
     * 获取内存信息
     *
     * @param memory GlobalMemory对象
     * @return 内存信息
     */
    public static Mem getMemoryInfo(GlobalMemory memory) {
        Mem mem = new Mem();
        mem.setTotal(memory.getTotal());
        mem.setUsed(memory.getTotal() - memory.getAvailable());
        mem.setFree(memory.getAvailable());
        return mem;
    }

    /**
     * 获取磁盘信息
     *
     * @param os OperatingSystem对象
     * @return 磁盘信息
     */
    public static List<SysFile> getDiskInfo(OperatingSystem os) {
        List<SysFile> sysFileList = new ArrayList<>();
        FileSystem fileSystem = os.getFileSystem();
        OSFileStore[] fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray) {
            long free = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            long used = total - free;
            SysFile sysFile = new SysFile();
            sysFile.setDirName(fs.getMount());
            sysFile.setSysTypeName(fs.getType());
            sysFile.setTypeName(fs.getName());
            sysFile.setTotal(total);
            sysFile.setFree(free);
            sysFile.setUsed(used);
            sysFile.setUsage(Arith.div(used, total));
            sysFileList.add(sysFile);
        }
        return sysFileList;
    }

    /**
     * 获取系统信息
     *
     * @return 系统信息
     */
    public static Sys getSystemInfo() {
        Properties props = System.getProperties();
        Sys sys = new Sys();
        sys.setComputerName(HostUtil.getHostName());
        sys.setComputerIp(HostUtil.getHostIp());
        sys.setOsName(props.getProperty("os.name"));
        sys.setOsArch(props.getProperty("os.arch"));
        sys.setUserDir(props.getProperty("user.dir"));
        return sys;
    }

    /**
     * 获取JVM信息
     *
     * @return JVM信息
     */
    public static Jvm getJvmInfo() {
        Properties props = System.getProperties();
        Jvm jvm = new Jvm();
        jvm.setMemoryTotal(Runtime.getRuntime().totalMemory());
        jvm.setMaxAvailableMemory(Runtime.getRuntime().maxMemory());
        jvm.setFreeMemory(Runtime.getRuntime().freeMemory());
        jvm.setUsageMemory(jvm.getMemoryTotal() - jvm.getFreeMemory());
        jvm.setJdkVersion(props.getProperty("java.version"));
        jvm.setJreHome(props.getProperty("java.home"));
        jvm.setStartTime(ManagementFactory.getRuntimeMXBean().getStartTime());
        jvm.setRunTime(System.currentTimeMillis() - jvm.getStartTime());
        return jvm;
    }

    /**
     * 获取网络状态流量信息
     *
     * @param hal HardwareAbstractionLayer对象
     * @return 网络状态流量信息
     */
    public static Network getNetwork(HardwareAbstractionLayer hal) {
        NetworkIF[] beforeNetworkIFs = hal.getNetworkIFs();
        Util.sleep(OSHI_NETWORK_WAIT_SECOND);
        NetworkIF[] nowNetworkIFs = hal.getNetworkIFs();
        return getNetwork(beforeNetworkIFs, nowNetworkIFs);
    }

    /**
     * 获取网络状态流量信息
     *
     * @param beforeNetworkIFs 一秒之前的网络状态流量信息
     * @param nowNetworkIFs    现在的网络状态流量信息
     * @return 网络状态流量信息
     */
    public static Network getNetwork(NetworkIF[] beforeNetworkIFs, NetworkIF[] nowNetworkIFs) {
        String name = HostUtil.getNetworkName();
        NetworkIF beforeNetworkIF = null;
        for (NetworkIF n : beforeNetworkIFs) {
            if (name.equalsIgnoreCase(n.getName())) {
                beforeNetworkIF = n;
            }
        }
        NetworkIF nowNetworkIF = null;
        for (NetworkIF n : nowNetworkIFs) {
            if (name.equalsIgnoreCase(n.getName())) {
                nowNetworkIF = n;
            }
        }
        if (nowNetworkIF == null || beforeNetworkIF == null) {
            return new Network();
        }
        Network network = new Network();
        network.setName(nowNetworkIF.getName());
        network.setMac(nowNetworkIF.getMacaddr());
        network.setIp(nowNetworkIF.getIPv4addr()[0]);
        network.setPacketsReceiveAmount(nowNetworkIF.getPacketsRecv());
        network.setBytesReceiveAmount(nowNetworkIF.getBytesRecv());
        network.setErrorPacketsReceiveAmount(nowNetworkIF.getInErrors());
        network.setPacketsSendAmount(nowNetworkIF.getPacketsSent());
        network.setBytesSendAmount(nowNetworkIF.getBytesSent());
        network.setErrorPacketsSendAmount(nowNetworkIF.getOutErrors());
        network.setUploadBps(nowNetworkIF.getBytesRecv() - beforeNetworkIF.getBytesRecv());
        network.setDownloadBps(nowNetworkIF.getBytesSent() - beforeNetworkIF.getBytesSent());
        return network;
    }

    /**
     * 字节转换
     *
     * @param size 字节大小
     * @return 转换后值
     */
    public String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else {
            return String.format("%d B", size);
        }
    }

}
