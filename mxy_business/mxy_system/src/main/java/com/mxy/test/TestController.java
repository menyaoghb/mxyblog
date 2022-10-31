package com.mxy.test;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static cn.hutool.core.util.CharsetUtil.GBK;

public class TestController {
//    public static void main(String[] args) {
//        // String数组 转 int数组
//        String s = "1,2,3";
//        String[] split = s.split(",");
//        int[] ints = Arrays.stream(split).mapToInt(Integer::parseInt).toArray();
//        List<Integer> collect = Arrays.stream(ints).boxed().collect(Collectors.toList());
//        System.out.println(collect);
//    }

    /**
     * 跳出多重循环，可以在外面的循环语句前定义一个标号，然后在里层循环体的代码中使用带有标号的break 语句，即可跳出外层循环。
     * @param args
     */
//    public static void main(String[] args) {
//        ok:
//        for (int i = 0; i < 10; i++) {
//            for (int j = 0; j < 10; j++) {
//                System.out.println("i=" + i + ",j=" + j);
//                if (j == 5) {
//                    break ok;
//                }
//
//            }
//        }
//    }
    public static void main(String[] args) throws UnsupportedEncodingException {
        String utf8Str= "NMC-SMS VER 1.0\r\nCommand: Send\r\nSrcNum: 91\r\nDestNum: " + "12312113123" + "\r\nContent-Length: " + 11 + "\r\n\r\n" + "测试测试测试测试" + "";
        String oldUnicode = new String(utf8Str.getBytes( "UTF-8"), "UTF-8");
        String gbkStr = new String(oldUnicode.getBytes("GBK"), "GBK");
        System.out.println(gbkStr );
    }
}
