package com.mxy.common.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @Description json工具类
 * @Author 孟小耀
 * @Date 2021/7/20 13:56
 */
public class JsonUtils {


    /**
     * java对象转json字符串
     */
    public static String objectToJsonString(Object obj) {
        try {
            String json = JSONObject.toJSONString(obj);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把json转换为map类型的数据
     *
     * @param json
     * @return
     */
    public static Map<String, Object> jsonToMap(String json) {

        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .serializeNulls()
                .create();
        Map<String, Object> map = null;
        try {
            Type type = new TypeToken<Map<String, Object>>() {
            }.getType();

            map = gson.fromJson(json, type);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 把对象转换为json数据
     *
     * @param obj
     * @return 2018年5月7日  下午5:27:16
     */
    public static String objectToJson(Object obj) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        try {
            String json = gson.toJson(obj);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
