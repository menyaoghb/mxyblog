package com.mxy.config.mysql;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

//@Component
public class MysqlBinLogClient implements ApplicationRunner {


    @Value("${binlog.datasource.host}")
    private String host;
    @Value("${binlog.datasource.port}")
    private int port;
    @Value("${binlog.datasource.username}")
    private String userName;
    @Value("${binlog.datasource.passwd}")
    private String password;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //项目启动完成连接bin-log
        new Thread(() -> {
            connectMysqlBinLog();
        }).start();

    }

    /**
     * 连接mysqlBinLog
     */
    public void connectMysqlBinLog() {
        BinaryLogClient client = new BinaryLogClient(host, port, userName, password);
        client.setServerId(2);

        client.registerEventListener(event -> {
            EventData data = event.getData();
            if (data instanceof TableMapEventData) {
                System.out.println("Table:");
                TableMapEventData tableMapEventData = (TableMapEventData) data;
                System.out.println(tableMapEventData.getTableId());
            }
            if (data instanceof QueryEventData) {
                System.out.println("Query:");
                QueryEventData queryEventData = (QueryEventData) data;
                System.out.println(queryEventData);
            }
            if (data instanceof UpdateRowsEventData) {
                System.out.println("Update:");
                UpdateRowsEventData updateRowsEventData = (UpdateRowsEventData) data;
                for (Map.Entry<Serializable[], Serializable[]> row : updateRowsEventData.getRows()) {
                    List<Serializable> entries = Arrays.asList(row.getValue());
                    System.out.println(entries);
                }
            }
            else if (data instanceof WriteRowsEventData) {
                System.out.println("Insert:");
                System.out.println(data.toString());
            } else if (data instanceof DeleteRowsEventData) {
                System.out.println("Delete:");
                System.out.println(data.toString());
            }
        });

        try {
            client.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
