package com.mxy.config.binlog;


/**
 * BinLogListener监听器
 *
 * @author 百度
 * @since 2022/09/21
 **/
@FunctionalInterface
public interface BinLogListener {

    /**
     * 监听逻辑处理
     */
    void onEvent(BinLogItem item);
}


