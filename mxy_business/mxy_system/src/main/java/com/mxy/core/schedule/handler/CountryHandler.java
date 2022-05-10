package com.mxy.core.schedule.handler;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.mxy.common.core.entity.SysCountry;
import com.mxy.common.core.utils.RedisUtil;
import com.mxy.system.service.SysCountryService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * 定时每天刷新 省分索引 中的数据
 *
 * @author 孟耀
 */
@Component
@Slf4j
public class CountryHandler {

    private static String INDEX_NAME = "country";

    @Autowired
    public RestHighLevelClient client;

    @Resource
    private RedisUtil redisUtil;

    @Autowired
    public SysCountryService sysEsDataService;

    @XxlJob("refreshCountry")
    public void refreshCountry() throws Exception {
        // 获取所有省分数据
        List<SysCountry> sysCountryList = sysEsDataService.selectList();
        // 1、更新es中数据
        for (SysCountry sysCountry : sysCountryList) {
            UpdateRequest updateRequest = new UpdateRequest(INDEX_NAME, sysCountry.getId());
            updateRequest.doc(BeanUtil.beanToMap(sysCountry), XContentType.JSON);
            try {
                UpdateResponse update = client.update(updateRequest, RequestOptions.DEFAULT);
                XxlJobHelper.log("更新索引文档 {" + INDEX_NAME + "} 数据结果：{" + JSONObject.toJSONString(update) + "}");
            } catch (IOException e) {
                XxlJobHelper.log("更新索引文档 {" + INDEX_NAME + "} 数据 {" + JSONObject.toJSONString(sysCountry) + "} 失败", e);
            }
        }
        // 2、刷新redis中数据
        redisUtil.listSet("COUNTRY_DATA", sysCountryList);
    }
}
