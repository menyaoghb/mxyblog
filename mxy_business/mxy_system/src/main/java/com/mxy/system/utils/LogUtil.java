package com.mxy.system.utils;


import com.mxy.common.core.entity.SelfUserEntity;
import com.mxy.common.core.entity.SysLoginLog;
import com.mxy.common.core.entity.SysOperLog;
import com.mxy.common.core.utils.IPUtils;
import com.mxy.common.core.utils.ServletUtils;
import com.mxy.common.log.enums.Status;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * 日志存储工具类
 *
 * @author mengyao
 */
@Component
public class LogUtil {

    /**
     * @description: 记录登录相关日志
     * @author: meng.yao
     * @date: 2022/8/13 18:39
     **/
    public static void saveLog(String title, Integer type) {
        try {
                SelfUserEntity userDetails = new SelfUserEntity();
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                userDetails = (SelfUserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            }
            SysOperLog sysOperLog = new SysOperLog();
            // 模块标题
            sysOperLog.setTitle(title);
            // 操作类型
            sysOperLog.setBusinessType(type);
            // 方法名称
            sysOperLog.setMethod("com.mxy");
            // 请求参数
            sysOperLog.setOperParam(String.valueOf(ServletUtils.getRequest()));
            // 请求方式
            sysOperLog.setRequestMethod(Objects.requireNonNull(ServletUtils.getRequest()).getMethod());
            String ip = IPUtils.getClientIp(Objects.requireNonNull(ServletUtils.getRequest()));
            // 主机地址
            sysOperLog.setOperIp(ip);
            // 操作地址
            String ipName = "";
            if (!StringUtils.isEmpty(ip)) {
                ipName = IPUtils.getIpAddress(ip);
            } else {
                sysOperLog.setOperIp("0.0.0.0");
                ipName = "X-X-X-X";
            }
            sysOperLog.setOperLocation(StringUtils.isNotBlank(ipName) ? ipName : "X-X-X-X");
            // 请求URL
            sysOperLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
            // 请求时长
            sysOperLog.setResTime(100);
            // 操作状态
            sysOperLog.setStatus(Status.SUCCESS.ordinal());
            // 错误消息
            sysOperLog.setErrorMsg("");
            // 操作时间
            sysOperLog.setOperTime(new Date());
            sysOperLog.setOperName(StringUtils.isNotBlank(userDetails.getUsername()) ? userDetails.getUsername() : "unknown");
            sysOperLog.insert();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description: 记录博客日志
     * @author: meng.yao
     * @date: 2022/8/13 18:39
     **/
    public static void saveBlogLog(String title,String param, Integer type) {
        try {
            SysOperLog sysOperLog = new SysOperLog();
            // 模块标题
            sysOperLog.setTitle(title);
            // 操作类型
            sysOperLog.setBusinessType(type);
            // 方法名称
            sysOperLog.setMethod("com.mxy");
            // 请求参数
            sysOperLog.setOperParam(param);
            // 请求方式
            sysOperLog.setRequestMethod(Objects.requireNonNull(ServletUtils.getRequest()).getMethod());
            String ip = IPUtils.getClientIp(Objects.requireNonNull(ServletUtils.getRequest()));
            // 主机地址
            sysOperLog.setOperIp(ip);
            // 操作地址
            String ipName = "";
            if (!StringUtils.isEmpty(ip)) {
                ipName = IPUtils.getIpAddress(ip);
            } else {
                sysOperLog.setOperIp("0.0.0.0");
                ipName = "X-X-X-X";
            }
            sysOperLog.setOperLocation(StringUtils.isNotBlank(ipName) ? ipName : "X-X-X-X");
            // 请求URL
            sysOperLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
            // 请求时长
            sysOperLog.setResTime(100);
            // 操作状态
            sysOperLog.setStatus(Status.SUCCESS.ordinal());
            // 错误消息
            sysOperLog.setErrorMsg("");
            // 操作时间
            sysOperLog.setOperTime(new Date());
            sysOperLog.setOperName("unknown");
            sysOperLog.insert();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description 记录登录日志
     * @Author mengyao
     * @Date 2022/6/24 15:28
     */
    public static void saveLoginLog(SelfUserEntity userDetails, String type, String description) {
        try {
            SysLoginLog login = new SysLoginLog();
            login.setUserId(userDetails.getUsername());
            login.setUserName(userDetails.getRelName());
            login.setLoginTime(new Date());
            login.setLoginType(type);
            String ip = IPUtils.getClientIp(Objects.requireNonNull(ServletUtils.getRequest()));
            // 主机地址
            login.setIp(ip);
            // 操作地址
            String ipName = "";
            if (!StringUtils.isEmpty(ip)) {
                ipName = IPUtils.getIpAddress(ip);
            } else {
                login.setIp("0.0.0.0");
                ipName = "X-X-X-X";
            }
            login.setAddress(StringUtils.isNotBlank(ipName) ? ipName : "X-X-X-X");
            login.setDescription(description);
            login.insert();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description: 记录免登录日志
     * @author: meng.yao
     * @date: 2022/8/13 18:39
     **/
    public static void saveNoLoginLog(String title,String param,Integer type) {
        try {
            SysOperLog sysOperLog = new SysOperLog();
            // 模块标题
            sysOperLog.setTitle(title);
            // 操作类型
            sysOperLog.setBusinessType(type);
            // 方法名称
            sysOperLog.setMethod("com.mxy");
            // 请求参数
            sysOperLog.setOperParam(param);
            // 请求方式
            sysOperLog.setRequestMethod(Objects.requireNonNull(ServletUtils.getRequest()).getMethod());
            String ip = IPUtils.getClientIp(Objects.requireNonNull(ServletUtils.getRequest()));
            // 主机地址
            sysOperLog.setOperIp(ip);
            // 操作地址
            String ipName = "";
            if (!StringUtils.isEmpty(ip)) {
                ipName = IPUtils.getIpAddress(ip);
            } else {
                sysOperLog.setOperIp("0.0.0.0");
                ipName = "X-X-X-X";
            }
            sysOperLog.setOperLocation(StringUtils.isNotBlank(ipName) ? ipName : "X-X-X-X");
            // 请求URL
            sysOperLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
            // 请求时长
            sysOperLog.setResTime(100);
            // 操作状态
            sysOperLog.setStatus(Status.SUCCESS.ordinal());
            // 错误消息
            sysOperLog.setErrorMsg("");
            // 操作时间
            sysOperLog.setOperTime(new Date());
            sysOperLog.setOperName("SYSTEM");
            sysOperLog.insert();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
