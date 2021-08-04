package com.mxy.common.core.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author 孟小耀
 * @Date 2021/7/20 0020 16:11
 */
@Data
public class PageVo<T> {
    /**
     * 关键字
     */
    private String keyword;

    /**
     * 当前页
     */
    private Long currentPage;

    /**
     * 页大小
     */
    private Long pageSize;



}
