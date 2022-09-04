package com.mxy.system.info.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * 获取主机工具类
 *
 * @author 孟耀
 */
public class HostUtil {

    public static String getNetworkName() {
        String networkName = null;
        Enumeration<NetworkInterface> netInterfaces;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                String displayName = ni.getDisplayName();
                String name = ni.getName();
                // 判断网络接口的名称不能为 lo获得docker 我们要找的应该是 eth0
                if (!name.contains("docker") && !name.contains("lo") && !displayName.contains("VMware")) {
                    Enumeration<InetAddress> ips = ni.getInetAddresses();
                    while (ips.hasMoreElements()) {
                        InetAddress inetAddress = ips.nextElement();
                        // 不是回环地址 127.0.0.1
                        if (!inetAddress.isLoopbackAddress()) {
                            String ip = inetAddress.getHostAddress();
                            if (isIp(ip)) {
                                networkName = name;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("get network interface name error." + e);
        }
        if (networkName == null) {
            throw new RuntimeException("get network interface name error. networkName = null.");
        }
        return networkName;
    }

    /**
     * 获得Linux主机IP
     *
     * @return IP地址
     */
    public static String getLinuxHostIp() throws Exception {
        String localHostIp = null;
        Enumeration<NetworkInterface> netInterfaces;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                String displayName = ni.getDisplayName();
                String name = ni.getName();
                // 判断网络接口的名称不能为 lo获得docker 我们要找的应该是 eth0
                if (!name.contains("docker") && !name.contains("lo") && !displayName.contains("VMware")) {
                    Enumeration<InetAddress> ips = ni.getInetAddresses();
                    while (ips.hasMoreElements()) {
                        InetAddress inetAddress = ips.nextElement();
                        // 不是回环地址 127.0.0.1
                        if (!inetAddress.isLoopbackAddress()) {
                            String ip = inetAddress.getHostAddress();
                            if (isIp(ip)) {
                                localHostIp = ip;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("get linux ip error." + e);
        }
        if (localHostIp == null) {
            throw new Exception("get linux ip error. local_ip = null.");
        }
        return localHostIp;
    }

    /**
     * 获得Window主机IP
     *
     * @return IP地址
     */
    public static String getWindowsHostIp() throws Exception {
        String localHostIp = null;
        try {
            localHostIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            System.out.println("get windows ip error." + e);
        }
        if (localHostIp == null) {
            throw new Exception("get windows ip error. local_ip = null.");
        }
        return localHostIp;
    }

    /**
     * 获得主机IP
     *
     * @return IP地址
     */
    public static String getHostIp() {
        try {
            String osName = System.getProperties().getProperty("os.name").toLowerCase();
            if (osName.contains("win")) {
                return getWindowsHostIp();
            } else {
                return getLinuxHostIp();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 判断是否是IP地址的字符串
     *
     * @param ipAddress IP地址
     * @return
     */
    private static boolean isIp(String ipAddress) {
        String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        return ipAddress.matches(regex);
    }

    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
        }
        return "地球";
    }

}
