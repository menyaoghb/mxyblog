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

    /**
     * @Description 文件大小转换
     * @Author 孟耀
     * @Date 2022/6/10 12:45
     */
    public static String getFileSize(String size) {
        double length = Double.parseDouble(size);
        //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (length < 1024) {
            return length + "B";
        } else {
            length = length / 1024.0;
        }
        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推
        if (length < 1024) {
            return Math.round(length * 100) / 100.0 + "KB";
        } else {
            length = length / 1024.0;
        }
        if (length < 1024) {
            //因为如果以MB为单位的话，要保留最后1位小数，
            //因此，把此数乘以100之后再取余
            return Math.round(length * 100) / 100.0 + "MB";
        } else {
            //否则如果要以GB为单位的，先除于1024再作同样的处理
            return Math.round(length / 1024 * 100) / 100.0 + "GB";
        }
    }
}
