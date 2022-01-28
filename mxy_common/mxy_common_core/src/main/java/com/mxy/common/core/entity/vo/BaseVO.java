package com.mxy.common.core.entity.vo;

import lombok.Data;

/**
 * @Description
 * @Author 孟小耀
 * @Date 2021/7/20 0020 16:07
 */
@Data
public class BaseVO<T> extends PageVo<T>  {

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;
}
