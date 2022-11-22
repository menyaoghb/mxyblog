package com.mxy.test;

import com.alibaba.fastjson.JSON;
import com.google.common.net.MediaType;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
//    public static void main(String[] args) throws UnsupportedEncodingException {
//        String utf8Str= "NMC-SMS VER 1.0\r\nCommand: Send\r\nSrcNum: 91\r\nDestNum: " + "12312113123" + "\r\nContent-Length: " + 11 + "\r\n\r\n" + "测试测试测试测试" + "";
//        String oldUnicode = new String(utf8Str.getBytes( "UTF-8"), "UTF-8");
//        String gbkStr = new String(oldUnicode.getBytes("GBK"), "GBK");
//        System.out.println(gbkStr );
//    }
//    public static void main(String[] args) {
//        // /context/open/oauth/callback/gitee
//        String uri = "/api/login/callback/gitee";
//        String EXTEND_LOGIN_URL = "/api/login/callback/qq";
//        // "/open/oauth/callback/".length()
//        int common = EXTEND_LOGIN_URL.length() - 2;
//        int start = uri.indexOf(EXTEND_LOGIN_URL.substring(0, common));
//        System.out.println(uri.substring(start + common));
//    }

/*
    public static String postRequestNoSecurity(String url, Map<String, String> headers, Object data) throws Exception {
        String securityReq = JSON.toJSONString(data);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS).build();
        //当时这句话报错了 在create()这个方法上
        RequestBody body =RequestBody.create(MediaType.parse("application/json"), securityReq);

        Request.Builder builder = new Request.Builder();
        if (!BaseUtils.isEmpty(headers)) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        final Request req = builder.addHeader("Content-Length", String.valueOf(securityReq.length()))
                .url(url)
                .post(body)
                .build();
        Call call = okHttpClient.newCall(req);
        Response response = call.execute();

        String securityRes = response.body().string();
        return securityRes;
    }
*/


}
