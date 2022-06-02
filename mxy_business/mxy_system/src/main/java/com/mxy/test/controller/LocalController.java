package com.mxy.test.controller;

import com.mxy.common.core.entity.SysCountry;
import com.mxy.common.core.utils.CommonUtils;
import com.mxy.common.core.utils.LocalUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 测试类
 */
@RestController
@RequestMapping("/api/test/local")
public class LocalController {

    @Autowired
    private ThreadPoolExecutor executor;

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


}
