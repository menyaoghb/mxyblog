package com.mxy.common.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description 日期时间工具类
 * @Author 孟小耀
 * @Date 2021/8/4 8:33
 */
public class DateUtils {


    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";


    /**
     * 格式化日期时间，年-月-日 时:分:秒
     */
    public static final String formatDateTime(Date date) {

        return date == null ? "" : format(date, "yyyy-MM-dd HH:mm:ss");
    }


    /**
     * 按照指定类型格式化日期
     */
    public static final String format(Date date, String formatPattern) {
        return date == null ? "" : (new SimpleDateFormat(formatPattern)).format(date);
    }

}
