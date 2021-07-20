package com.mxy.common.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

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

}
