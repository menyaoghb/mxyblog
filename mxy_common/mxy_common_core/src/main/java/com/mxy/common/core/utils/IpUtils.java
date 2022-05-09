package com.mxy.common.core.utils;

import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Description IP工具类
 * @Author 孟小耀
 * @Date 2021/8/4 23:27
 */
public class IpUtils {


    /**
     * 解析IP地址
     * 例：127.0.0.1
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String ip = null;
        // X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            // Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            // WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            // HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            // X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }

        // 有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }

        // 还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }


    public static String getIpAddrs(HttpServletRequest request) {
        //负载均衡下为小写
        String ip = request.getHeader("x-forward-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    /**
     * 解析IP地址
     * 例：中国-安徽-合肥-联通
     */
    public static String recordIp(String ip) {
        String url = "http://ip.taobao.com/outGetIpInfo?ip=" + ip + "&accessKey=alibaba-inc";
        String jsonStr = OkGetArt(url);
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            JSONObject result = (JSONObject) jsonObject.get("data");
            if (result != null) {
                return result.get("country") + "-" + result.get("region") + "-" + result.get("city") + "-" + result.get("isp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "IP解析异常";

    }

    /**
     * 解析IP地址
     */
    public static String OkGetArt(String url) {
        String html = null;
        OkHttpClient client = new OkHttpClient();
        Request requestObj = new Request.Builder().url(url).addHeader("Content-type", "charset=utf-8").build();
        try (Response responseObj = client.newCall(requestObj).execute()) {
            html = responseObj.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return html;
    }
}
