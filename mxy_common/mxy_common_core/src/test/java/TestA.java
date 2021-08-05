import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Description
 * @Author 孟小耀
 * @Date 2021/8/5 0005 8:33
 */
public class TestA {


    public static void main(String[] args) {
        String ip = "220.248.243.122";
        String addr = recordIp(ip);
        System.out.println(addr);
    }

    public static String recordIp(String ip) {
        String url = "http://ip.taobao.com/outGetIpInfo?ip=" + ip + "&accessKey=alibaba-inc";
        String jsonStr = OkGetArt(url);
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        JSONObject result = (JSONObject) jsonObject.get("data");
        return result.get("country") + "-" + result.get("region") + "-" + result.get("city")+"-"+result.get("isp");

    }

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
