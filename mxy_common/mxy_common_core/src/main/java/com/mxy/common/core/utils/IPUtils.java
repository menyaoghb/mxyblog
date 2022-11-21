package com.mxy.common.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description IP工具类
 * @Author 孟小耀
 * @Date 2021/8/4 23:27
 */
@Slf4j
public class IPUtils {

    /**
     * 获取IP地址
     * 例：127.0.0.1
     */
    public static String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  //"***.***.***.***".length() = 15
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    /**
     * 获取操作系统,浏览器及浏览器版本信息
     *
     * @param request
     * @return
     */
    public static Map<String, String> getOsAndBrowserInfo(HttpServletRequest request) {
        String browserDetails = request.getHeader("User-Agent");
        String userAgent = browserDetails;
        String user = userAgent.toLowerCase();

        String os = "";
        String browser = "";

        //=================OS Info=======================
        if (userAgent.toLowerCase().indexOf("windows") >= 0) {
            os = "Windows";
        } else if (userAgent.toLowerCase().indexOf("mac") >= 0) {
            os = "Mac";
        } else if (userAgent.toLowerCase().indexOf("x11") >= 0) {
            os = "Unix";
        } else if (userAgent.toLowerCase().indexOf("android") >= 0) {
            os = "Android";
        } else if (userAgent.toLowerCase().indexOf("iphone") >= 0) {
            os = "IPhone";
        } else {
            os = "UnKnown, More-Info: " + userAgent;
        }

        //===============Browser===========================
        try {
            if (user.contains("edge")) {
                browser = (userAgent.substring(userAgent.indexOf("Edge")).split(" ")[0]).replace("/", "-");
            } else if (user.contains("msie")) {
                String substring = userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
                browser = substring.split(" ")[0].replace("MSIE", "IE") + "-" + substring.split(" ")[1];
            } else if (user.contains("safari") && user.contains("version")) {
                browser = (userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0]
                        + "-" + (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
            } else if (user.contains("opr") || user.contains("opera")) {
                if (user.contains("opera")) {
                    browser = (userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0]
                            + "-" + (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
                } else if (user.contains("opr")) {
                    browser = ((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-"))
                            .replace("OPR", "Opera");
                }
            } else if (user.contains("chrome")) {
                browser = (userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
            } else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1) ||
                    (user.indexOf("mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1) ||
                    (user.indexOf("mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1)) {
                browser = "Netscape-?";

            } else if (user.contains("firefox")) {
                browser = (userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
            } else if (user.contains("rv")) {
                String IEVersion = (userAgent.substring(userAgent.indexOf("rv")).split(" ")[0]).replace("rv:", "-");
                browser = "IE" + IEVersion.substring(0, IEVersion.length() - 1);
            } else {
                browser = "UnKnown";
            }
        } catch (Exception e) {
            log.error("获取浏览器版本失败");
            log.error(e.getMessage());
            browser = "UnKnown";
        }

        Map<String, String> result = new HashMap<>(2);
        result.put("OS", os);
        result.put("BROWSER", browser);
        return result;
    }


    /**
     * 解析IP地址
     * 例：中国-安徽-合肥-联通
     */
    public static String getIpAddress(String ip) {
        // 读流
        ClassPathResource classPathResource = new ClassPathResource("ip2region.db");
        // 新建文件，把流存放到这个文件，再从这个文件里面读取数据
        File file = new File("ip2region.db");
        // 获取输入流
        InputStream inputStream = null;
        try {
            FileUtils.inputStreamToFile(classPathResource.getInputStream(), file);
            inputStream = new FileInputStream(file);
            int len;
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer)) != -1) {
            }
        } catch (IOException e) {
            log.error("初始化文件 ip2region.db 失败");
            e.printStackTrace();
        } finally {
            // 关闭流操作
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 查询算法B-tree, Binary, Memory
        int algorithm = DbSearcher.MEMORY_ALGORITYM;
        DbSearcher searcher = null;
        try {
            DbConfig config = new DbConfig();
            searcher = new DbSearcher(config, file.getPath());
            Method method;
            switch (algorithm) {
                case DbSearcher.BTREE_ALGORITHM:
                    method = searcher.getClass().getMethod("btreeSearch", String.class);
                    break;
                case DbSearcher.BINARY_ALGORITHM:
                    method = searcher.getClass().getMethod("binarySearch", String.class);
                    break;
                case DbSearcher.MEMORY_ALGORITYM:
                default:
                    method = searcher.getClass().getMethod("memorySearch", String.class);
                    break;
            }
            DataBlock dataBlock = null;
            if (!Util.isIpAddress(ip)) {
                log.error("无效的IP：{}", ip);
            }
            dataBlock = (DataBlock) method.invoke(searcher, ip);
            String ipInfo = dataBlock.getRegion();
            if (StringUtils.isNotBlank(ipInfo)) {
                ipInfo = ipInfo.replace("|0", "");
                ipInfo = ipInfo.replace("0|", "");
            }
            return ipInfo;
        } catch (Exception e) {
            log.error("解析IP信息失败：", e);
        } finally {
            try {
                assert searcher != null;
                searcher.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 解析IP属地
     */
    public static String getIpPossession(String ip) {
        String cityInfo = getIpAddress(ip);
        if (!StringUtils.isEmpty(cityInfo)) {
            cityInfo = cityInfo.replace("|", " ");
            String[] cityList = cityInfo.split(" ");
            if (cityList.length > 0) {
                // 国内的显示到具体的省
                if ("中国".equals(cityList[0])) {
                    if (cityList.length > 1) {
                        return cityList[1];
                    }
                }
                // 国外显示到国家
                return cityList[0];
            }
        }
        return "X-X-X-X";
    }
}
