package com.mxy.common.core.enums;

/**
 * @desc 响应状态码
 * @Author 孟小耀
 * @Date 2021/7/20 8:22
 */
public enum StatusCode {

    /**
     * 成功
     */
    SUCCESS("200", "成功"),

    /**
     * 失败
     */
    FAIL("500", "失败");

    private final String key;

    private final String desc;


    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    private StatusCode(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }
}
