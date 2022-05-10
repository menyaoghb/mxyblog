package com.mxy.common.core.utils;

/**
 * 通用工具类
 * @author 孟耀
 */
public class CommonUtils {

    /**
     * @Description 在字符串后面追加0,示例：str为1 length为4 则为 ”1000“
     * @Author 孟耀
     * @Date 2022/5/10 20:53
     */
    public static String numberAfterFillZero(String str,int length){
        StringBuffer buffer =new StringBuffer(str);
        if(buffer.length()>= length){
            return buffer.toString();
        }else{
            while(buffer.length()< length){
                buffer.append("0");
            }
        }
        return buffer.toString();
    }
}
