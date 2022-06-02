package com.mxy.test.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.mxy.common.core.entity.SysCountry;
import com.mxy.common.core.entity.SysEsData;
import com.mxy.common.core.utils.AutoNameUtils;
import com.mxy.common.core.utils.CommonUtils;
import com.mxy.common.core.utils.DateUtils;
import com.mxy.common.core.utils.LocalUtil;
import com.mxy.core.elasticsearch.EsServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试类
 */
@RestController
@RequestMapping("/api/test/local")
public class LocalController {

    @Autowired
    private ThreadPoolExecutor executor;

    private static String INDEX_NAME = "person";

    @Resource
    private EsServiceImpl esService;

    @Autowired
    public RestHighLevelClient client;

    private AtomicInteger integer = new AtomicInteger();

    private String[] names = {"紫南", "安蕾", "春雁", "凌香", "凡梦", "雅琴", "寻文", "青筠", "小萱", "访烟", "妙竹", "千青", "幻灵", "天荷", "春竹", "之瑶", "代云", "芷蕾"};
    private String[] addrs = {"冷泉亭", "涵秋馆", "凌虚阁", "倒影楼", "起云台", "凝辉殿", "沉心堂", "春泽斋", "听雨轩", "寒碧山庄", "玉兰山房", "松陵酒家", "九孔桥", "银锄湖", "洞天深处"};
    private String[] companys = {"缕月云开", "山高水长", "上下天光", "菊院荷风", "坐石临流", "水木明瑟", "雷峰夕照", "一碧万顷", "梧竹幽居", "夹镜鸣琴"};

    /**
     * @Description 新增世界地区数据
     * @Author 孟耀
     * @Date 2022/6/2 17:56
     */
    @PostMapping("/insertCountryData")
    public void insertCountryData() {
        System.out.println("开始");
        LocalUtil lu = LocalUtil.getInstance();
        List<Map<String, String>> list = lu.getCountry();
        for (int i = 0; i < list.size(); i++) {
            int finalI = i;
            Thread thread = new Thread(() -> {
                SysCountry sysCountry = new SysCountry();
                String countryId = UUID.randomUUID().toString().replace("-", "");
                sysCountry.setId(countryId);
                sysCountry.setName(MapUtils.getString(list.get(finalI), "name"));
                sysCountry.setValue(CommonUtils.numberAfterFillZero(MapUtils.getString(list.get(finalI), "value"), 8));
                sysCountry.setParentId("0");
                sysCountry.setPath(MapUtils.getString(list.get(finalI), "name"));
                sysCountry.insert();
                List<Map<String, String>> provincesList = lu.getProvinces(MapUtils.getString(list.get(finalI), "name"));
                if (CollectionUtils.isNotEmpty(provincesList)) {
                    for (int j = 0; j < provincesList.size(); j++) {
                        String provincesId = UUID.randomUUID().toString().replace("-", "");
                        sysCountry.setId(provincesId);
                        sysCountry.setName(MapUtils.getString(provincesList.get(j), "name"));
                        sysCountry.setValue(CommonUtils.numberAfterFillZero(MapUtils.getString(list.get(finalI), "value") + MapUtils.getString(provincesList.get(j), "value"), 8));
                        sysCountry.setParentId(countryId);
                        sysCountry.setPath(MapUtils.getString(list.get(finalI), "name") + "-" + MapUtils.getString(provincesList.get(j), "name"));
                        sysCountry.insert();
                        List<Map<String, String>> cityList = lu.getCities(MapUtils.getString(list.get(finalI), "name"), MapUtils.getString(provincesList.get(j), "name"));
                        for (int k = 0; k < cityList.size(); k++) {
                            String cityId = UUID.randomUUID().toString().replace("-", "");
                            sysCountry.setId(cityId);
                            sysCountry.setName(MapUtils.getString(cityList.get(k), "name"));
                            sysCountry.setValue(CommonUtils.numberAfterFillZero(MapUtils.getString(list.get(finalI), "value") + MapUtils.getString(provincesList.get(j), "value") + MapUtils.getString(cityList.get(k), "value"), 8));
                            sysCountry.setParentId(provincesId);
                            sysCountry.setPath(MapUtils.getString(list.get(finalI), "name") + "-" + MapUtils.getString(provincesList.get(j), "name") + "-" + MapUtils.getString(cityList.get(k), "name"));
                            sysCountry.insert();
                            List<Map<String, String>> countyList = lu.getcounty(MapUtils.getString(list.get(finalI), "name"), MapUtils.getString(provincesList.get(j), "name"), MapUtils.getString(cityList.get(k), "name"));
                            for (int t = 0; t < countyList.size(); t++) {
                                String countyId = UUID.randomUUID().toString().replace("-", "");
                                sysCountry.setId(countyId);
                                sysCountry.setName(MapUtils.getString(countyList.get(t), "name"));
                                sysCountry.setValue(CommonUtils.numberAfterFillZero(MapUtils.getString(list.get(finalI), "value") + MapUtils.getString(provincesList.get(j), "value") + MapUtils.getString(cityList.get(k), "value") + MapUtils.getString(countyList.get(t), "value"), 8));
                                sysCountry.setParentId(cityId);
                                sysCountry.setPath(MapUtils.getString(list.get(finalI), "name") + "-" + MapUtils.getString(provincesList.get(j), "name") + "-" + MapUtils.getString(cityList.get(k), "name") + "-" + MapUtils.getString(countyList.get(t), "name"));
                                sysCountry.insert();
                            }
                        }
                    }
                }
            });
            executor.execute(thread);
        }
        System.out.println("结束");
    }

    @PostMapping("/insertEsData")
    public String insertEsData() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for (int i = 0; i < 2000; i++) {
            Random random = new Random();
            IndexRequest indexRequest = new IndexRequest(INDEX_NAME).source(BeanUtil.beanToMap(SysEsData.builder()
                    .dataId(UUID.randomUUID().toString().replace("-", ""))
                    .name(AutoNameUtils.autoSurAndName())
                    .phone(18700000000L + random.nextInt(88888888))
                    .salary(new BigDecimal(random.nextInt(99999)))
                    .company(companys[random.nextInt(companys.length)])
                    .sex(random.nextInt(2))
                    .address(addrs[random.nextInt(addrs.length)] + companys[random.nextInt(companys.length)])
                    .createUser(names[random.nextInt(names.length)])
                    .createTime(new Date())
                    .status("0")
                    .isDelete("0")
                    .fieldOne(DateUtils.getNo(6))
                    .fieldTwo(DateUtils.getWeekStr(DateUtils.getStringDateShort()))
                    .fieldThree(DateUtils.getSeqWeek())
                    .fieldFour(DateUtils.getFromNow(Integer.parseInt(DateUtils.getRandom(1))))
                    .fieldFive(DateUtils.getTodayShort())
                    .fieldSix(DateUtils.getHour())
                    .fieldSeven(DateUtils.getTime())
                    .fieldEight(DateUtils.getTimeShort())
                    .fieldNine(Integer.parseInt(DateUtils.getRandom(9)))
                    .fieldTen(DateUtils.strToDate(DateUtils.dateToStr(new Date())))
                    .build()), XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        return JSONObject.toJSONString(bulk);
    }


}
