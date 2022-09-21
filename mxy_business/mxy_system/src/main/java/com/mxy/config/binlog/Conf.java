package com.mxy.config.binlog;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 数据库配置
 *
 * @author 百度
 * @since 2022/09/21
 **/
@Data
@AllArgsConstructor
public class Conf {
    private String host;
    private int port;
    private String username;
    private String passwd;
}

