package com.mxy.common.log.aspect;

import com.google.gson.Gson;
import com.mxy.common.core.utils.IpUtils;
import com.mxy.common.core.utils.ServletUtils;
import com.mxy.common.log.annotation.SysLog;
import com.mxy.common.core.entity.SysOperLog;
import com.mxy.common.log.enums.Status;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description 系统日志切面
 * @Author 孟小耀
 * @Date 2021/8/2 14:31
 */
@Slf4j
@Aspect
@Component
public class LogAspect {


    /**
     * 配置织入点
     * 配置切点，切点是在空方法上去配置的，当然这是简便写法，因为这样的话，后面方法上面的切面就直接可以用这个空的方法名去代替这样就避免了重复去写契入点
     */
    @Pointcut("@annotation(com.mxy.common.log.annotation.SysLog)")
    public void logPointCut() {
    }


    /**
     * @Description: 环绕通知
     * @Author: 孟耀
     * @Date: 2021/8/4 8:21
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result = null;
        try {
            // invocation.proceed() 之前的代码：前置通知
            result = point.proceed();
            //result 就是目标方法addStudent()方法的返回值
            //invocation.proceed() 之后的代码：后置通知
            long time = System.currentTimeMillis() - beginTime;
            saveLog(point, null, time);
        } catch (Exception e) {
            saveLog(point, e, System.currentTimeMillis() - beginTime);
            log.info("操作日志异常通知" + e.getMessage());
        }
        return result;
    }


    /**
     * @Description: 保存操作日志
     * @Author: 孟耀
     * @Date: 2021/8/4
     */
    private void saveLog(ProceedingJoinPoint joinPoint, Exception e, long time) {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            SysOperLog sysOperLog = new SysOperLog();
            SysLog sysLog = method.getAnnotation(SysLog.class);
            if (sysLog != null) {
                // 模块标题
                sysOperLog.setTitle(sysLog.module());
                // 操作类型
                sysOperLog.setBusinessType(sysLog.operType().ordinal());
            }
            // 方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = signature.getName();
            sysOperLog.setMethod(className + "." + methodName + "()");
            // 请求参数
            Object[] args = joinPoint.getArgs();
            List<String> list = new ArrayList<String>();
            for (Object o : args) {
                list.add(new Gson().toJson(o));
            }
            sysOperLog.setOperParam(list.toString());
            // 请求方式
            sysOperLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
            // 主机地址
            sysOperLog.setOperIp(ip);
            // 操作地址
            String ipName = IpUtils.recordIp("220.248.243.122");
            sysOperLog.setOperLocation(ipName);
            // 请求URL
            sysOperLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
            // 请求时长
            sysOperLog.setResTime((int) time);
            // 操作状态
            sysOperLog.setStatus(Status.SUCCESS.ordinal());
            // 错误消息
            if (e != null) {
                sysOperLog.setStatus(Status.FAIL.ordinal());
                if (!StringUtils.isEmpty(e.getMessage())) {
                    sysOperLog.setErrorMsg(e.getMessage().substring(0, 2000));
                } else {
                    sysOperLog.setErrorMsg("无错误消息");
                }
            }
            // 操作时间
            sysOperLog.setOperTime(new Date());
            sysOperLog.insert();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


}
