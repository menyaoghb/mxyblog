package com.mxy.common.core.utils;

import com.mxy.common.core.constant.Constants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 统一结果返回
 * @Author 孟小耀
 * @Date 2021/7/20 8:52
 */
@Data
public class ServiceResult {

    final static String STATUS = "status";

    final static String CODE = "code";

    final static String SUCCESS = "success";

    final static String ERROR = "error";

    final static String DATA = "data";

    final static String MESSAGE = "message";

    final static int NUM_TWO = 5;

    /**
     * @param code success error
     * @param data 返回的数据
     * @return String
     */
    public static String result(Object status, Object data, Integer code) {
        return resultData(status, data, code);
    }

    /**
     * 返回结果【只携带数据】
     *
     * @param status success error
     * @param data   返回的数据
     * @param code   响应码
     * @return String
     */
    public static String resultData(Object status, Object data, Integer code) {
        Map<Object, Object> map = new HashMap<>(NUM_TWO);
        map.put(STATUS, status);
        map.put(DATA, data);
        map.put(CODE, code);
        return JsonUtils.objectToJsonString(map);
    }

    /**
     * 返回状态码
     */
    public static String resultCode(Object status, Integer code) {
        Map<Object, Object> map = new HashMap<>(NUM_TWO);
        map.put(STATUS, status);
        map.put(CODE, code);
        return JsonUtils.objectToJsonString(map);
    }

    /**
     * 返回结果【只携带消息】
     *
     * @param status success error
     * @return String
     */
    public static String resultMessage(Object status, String message, Integer code) {
        Map<Object, Object> map = new HashMap<>(NUM_TWO);
        map.put(STATUS, status);
        map.put(MESSAGE, message);
        map.put(CODE, code);
        return JsonUtils.objectToJsonString(map);
    }

    /**
     * 返回结果【携带数据和消息】
     *
     * @param status
     * @param data
     * @param message
     * @return
     */
    public static String resultDataAndMessage(Object status, Object data, String message, Integer code) {
        Map<Object, Object> map = new HashMap<>(NUM_TWO);
        map.put(STATUS, status);
        map.put(DATA, data);
        map.put(MESSAGE, message);
        map.put(CODE, code);
        return JsonUtils.objectToJsonString(map);
    }

    /**
     * 返回成功结果【只携带状态码】
     */
    public static String success() {
        return resultCode(SUCCESS, Constants.SUCCESS);
    }

    /**
     * 返回成功的结果【只携带数据】
     *
     * @param data
     * @return
     */
    public static String success(Object data) {
        return resultData(SUCCESS, data, Constants.SUCCESS);
    }

    /**
     * 返回成功的结果【只携带消息】
     *
     * @param message
     * @return
     */
    public static String successMsg(String message) {
        return resultMessage(SUCCESS, message, Constants.SUCCESS);
    }

    /**
     * 返回成功的结果【携带数据和消息】
     *
     * @param message
     * @return
     */
    public static String success(Object data, String message) {
        return resultDataAndMessage(SUCCESS, data, message, Constants.SUCCESS);
    }

    /**
     * 返回失败的结果【只携带消息】
     *
     * @param message
     * @return
     */
    public static String errorMsg(String message) {
        return resultMessage(ERROR, message, Constants.FAIL);
    }

    /**
     * 返回失败的结果【只携带数据】
     *
     * @param data
     * @return
     */
    public static String error(Object data) {
        return resultData(ERROR, data, Constants.FAIL);
    }

    /**
     * 返回失败的结果【只携带状态码】
     */
    public static String error() {
        return resultCode(ERROR, Constants.FAIL);
    }
}
