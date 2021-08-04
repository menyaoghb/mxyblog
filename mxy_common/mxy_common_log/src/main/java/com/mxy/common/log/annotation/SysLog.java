package com.mxy.common.log.annotation;

import com.mxy.common.log.enums.OperType;

import java.lang.annotation.*;

/**
 * @Description 系统日志注解
 * @Author 孟小耀
 * @Date 2021/8/2 14:26
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD }) // 作用在参数和方法上
@Retention(RetentionPolicy.RUNTIME) // 运行时注解
@Documented //表明这个注解应该被 javadoc工具记录
public @interface SysLog {

    /**
     * 描述
     */
    String module() default "";

    /**
     * 操作类型
     */
    OperType operType() default OperType.OTHER;

}
