package com.mxy.core.schedule.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mxy.common.core.utils.RedisUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

//@Component
//@Slf4j
public class TestHandler {


    @Resource
    private RedisUtil redisUtil;

    @XxlJob("setRedis")
    public void setRedis() throws Exception {
        String command = XxlJobHelper.getJobParam();
        redisUtil.hashSet("VIC_TRANSHISTORYNEWCB:" + command, "data", "{\"STATUS\":\"0000\",\"MSG\":\"SUCCESS\",\"TXID\":\"TXID: 0d720af7d968^1652843388835^339; APPTX: 20220518135844253280734; totalCost: 452; proxyCost: 447; thirdCost: 208; thirdUrl: http://10.244.18.105:8000/api/chinaUnicom/microservice/trades/qryTransactionHistory/v1; HOSTINFO=> 【LIBPROCESS_IP=10.172.33.84; HOST=10.172.33.84; PORTS=3810; MESOS_TASK_ID=cs_ppc-uniproxy-sim.ecffd2d8-d657-11ec-bd71-ca5f24af8ca9】\",\"RSP\":{\"RSP_CODE\":\"0000\",\"RSP_DESC\":\"SUCCESS\",\"DATA\":{\"UnicomTaskBack\":{\"Header\":{\"tokenCode\":\"\",\"PRO_ID\":\"S1\",\"CHANNEL_ID\":null,\"OP_CITY\":null,\"TaskNo\":\"123456789021\",\"OP_DISTRICT\":null,\"TestFlag\":null,\"BusinessType\":\"TRANSHISTORYNEWCB\",\"BUSI_PROVINCE_ID\":\"湖南\",\"USER_CODE\":\"\",\"OPERATOR_ID\":null,\"ErrorDescript\":\"Success\",\"Version\":\"1.0.0\",\"RetCode\":\"00000\",\"AreaCode\":\"\",\"BusNo\":\"18570592518\",\"TENANT_ID\":\"UNICOM\",\"KF_CHNL_TYPE\":\"V8\",\"CODE_THIRD_BUSINESS_CODE\":\"1\",\"CHANNEL_TYPE\":null,\"CBSS-MS-GRAY\":\"staffid=$departid=$bus_pro_id=S1$provincecode=S1$eparchycode=S1$citycode=$serialnumber=18570592518$userid=$custid=$acctid=$psptid=$ip=$origdomain=$osnduns=$routetype=$routevalue=$transido=$channelid=$channeltype=$accesstype=03$processtime=\",\"OP_PROVINCE\":null,\"TestFlagion\":\"1\"},\"ResponseBody\":[{\"TRANSACTION_DATE\":\"20220514104312\",\"SERVICE_CODE\":\"120\",\"TRADE_STAFF_ID\":\"vhnnan2-3265\",\"CHANGE_INFO\":\"[{\\\"CHANGE_NAME_DIY\\\":\\\"\\\",\\\"TRANSACT_TYPE\\\":\\\"\\\",\\\"TRANSACT_CLASS\\\":\\\"S\\\",\\\"OPERATE_TYPE\\\":\\\"0\\\",\\\"CHANGE_ID\\\":\\\"53546\\\",\\\"CHANGE_NAME\\\":\\\"5G上网服务(下行峰值500Mbps)\\\",\\\"CHANGE_DEACT_TIME\\\":\\\"20220531235959\\\",\\\"CHANGE_ACT_TIME\\\":\\\"20220512180859\\\"}]\",\"SERVICE_DESC\":\"移网产品/服务变更\",\"TRANSACTION_CHANNEL\":\"湖南省分公司客服呼叫中心运营部\"}]}},\"ATTACH\":null}}");
        String invokeResult = String.valueOf(redisUtil.hashGet("VIC_TRANSHISTORYNEWCB:" + "18815513148", "data"));

        JSONArray jsonArray = JSONObject.parseObject(invokeResult).getJSONObject("RSP").getJSONObject("DATA").getJSONObject("UnicomTaskBack").getJSONArray("ResponseBody");

        System.out.println(jsonArray);
    }
}
