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
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        } else if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        } else if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        } else if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        } else if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
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
        return "未知";
    }
}
