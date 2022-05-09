package com.mxy.core.elasticsearch;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Es连接客户端
 * @author 孟耀
 * @date 2022-05-07 20:57
 */
@Configuration
public class EsConfig {

    private Logger logger = LoggerFactory.getLogger(EsConfig.class);

    @Value("${elasticsearch.clusterNodes}")
    private String address;

    @Value("${elasticsearch.connectTimeoutMin}")
    private String connectTimeoutMin;

    @Value("${elasticsearch.connectTimeoutMax}")
    private String connectTimeoutMax;

    @Bean
    public RestHighLevelClient getHighClient() {
        return new RestHighLevelClient(getClientBuilder());
    }

    private RestClientBuilder getClientBuilder() {

        String[] addressArray = address.split(",");
        int len = addressArray.length;
        HttpHost[] httpHostsArray = new HttpHost[len];
        for (int i = 0; i < len; i++) {
            String[] iphost = addressArray[i].split(":");
            httpHostsArray[i] = new HttpHost(iphost[0], Integer.parseInt(iphost[1]), "http");
        }
        // 如果有多个从节点可以持续在内部new多个HttpHost，参数1是ip,参数2是HTTP端口，参数3是通信协议
        RestClientBuilder clientBuilder = RestClient.builder(httpHostsArray);

        // 添加其他配置，返回来的还是RestClientBuilder对象，这些配置都是可选的
        // clientBuilder.setXX()...

        // 设置请求头，每个请求都会带上这个请求头
        Header[] defaultHeaders = {new BasicHeader("header", "value")};
        clientBuilder.setDefaultHeaders(defaultHeaders);

        // 设置监听器，每次节点失败都可以监听到，可以作额外处理
        clientBuilder.setFailureListener(new RestClient.FailureListener() {
            @Override
            public void onFailure(Node node) {
                super.onFailure(node);
                logger.error(node.getName() + "==节点失败了");
            }
        });

        clientBuilder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
            @Override
            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                // 连接5秒超时，套接字连接60s超时
                return requestConfigBuilder.setConnectTimeout(Integer.parseInt(connectTimeoutMin)).setSocketTimeout(Integer.parseInt(connectTimeoutMax));
            }
        });

        // 最后配置好的clientBuilder再build一下即可得到真正的Client
        return clientBuilder;
    }
}
