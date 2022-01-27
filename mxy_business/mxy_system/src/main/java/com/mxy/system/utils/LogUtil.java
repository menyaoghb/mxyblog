package com.mxy.system.utils;


import com.mxy.common.core.entity.SysOperLog;
import com.mxy.common.core.utils.IpUtils;
import com.mxy.common.core.utils.ServletUtils;
import com.mxy.common.log.enums.Status;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Objects;

/**
 * 日志存储工具类
 *
 * @author 孟耀
 */
@Component
public class LogUtil {

    public static void saveLog(String title,Integer type) {
        SysOperLog sysOperLog = new SysOperLog();
        // 模块标题
        sysOperLog.setTitle(title);
        // 操作类型
        sysOperLog.setBusinessType(type);
        // 方法名称
        sysOperLog.setMethod("");
        // 请求参数
        sysOperLog.setOperParam("");
        // 请求方式
        sysOperLog.setRequestMethod(Objects.requireNonNull(ServletUtils.getRequest()).getMethod());
        String ip = IpUtils.getIpAddrs(Objects.requireNonNull(ServletUtils.getRequest()));
        // 主机地址
        sysOperLog.setOperIp(ip);
        // 操作地址
        String ipName = "";
        if (StringUtils.isEmpty(ip)) {
            ipName = IpUtils.recordIp(ip);
        }
        sysOperLog.setOperLocation(ipName);
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
        sysOperLog.setOperName("");
        sysOperLog.insert();
    }
}
