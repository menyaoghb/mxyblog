package com.mxy.test.insert;

import com.mxy.common.core.entity.SysCountry;
import com.mxy.common.core.utils.CommonUtils;
import com.mxy.common.core.utils.LocalUtil;
import org.apache.commons.collections4.MapUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Description 地市测试类
 * @Date 2022/5/10 18:09
 */
public class LocalTest {

    @Test
    public void test1() {
        LocalUtil lu = LocalUtil.getInstance();
        List<Map<String, String>> list = lu.getCountry();
        for (int i = 0; i < 1; i++) {
            SysCountry sysCountry = new SysCountry();
            String countryId = UUID.randomUUID().toString().replace("-", "");
            sysCountry.setId(countryId);
            sysCountry.setName(MapUtils.getString(list.get(i), "name"));
            sysCountry.setValue(CommonUtils.numberAfterFillZero(MapUtils.getString(list.get(i), "value"), 8));
            sysCountry.setParentId("0");
            sysCountry.setPath(MapUtils.getString(list.get(i), "name"));
            sysCountry.insert();
            List<Map<String, String>> provincesList = lu.getProvinces(MapUtils.getString(list.get(i), "name"));
            for (int j = 0; j < provincesList.size(); j++) {
                String provincesId = UUID.randomUUID().toString().replace("-", "");
                sysCountry.setId(provincesId);
                sysCountry.setName(MapUtils.getString(provincesList.get(j), "name"));
                sysCountry.setValue(CommonUtils.numberAfterFillZero(MapUtils.getString(list.get(i), "value") + MapUtils.getString(provincesList.get(j), "value"), 8));
                sysCountry.setParentId(countryId);
                sysCountry.setPath(MapUtils.getString(list.get(i), "name") + "-" + MapUtils.getString(provincesList.get(j), "name"));
                sysCountry.insert();
                List<Map<String, String>> cityList = lu.getCities(MapUtils.getString(list.get(i), "name"), MapUtils.getString(provincesList.get(j), "name"));
                for (int k = 0; k < cityList.size(); k++) {
                    String cityId = UUID.randomUUID().toString().replace("-", "");
                    sysCountry.setId(cityId);
                    sysCountry.setName(MapUtils.getString(cityList.get(k), "name"));
                    sysCountry.setValue(CommonUtils.numberAfterFillZero(MapUtils.getString(list.get(i), "value") + MapUtils.getString(provincesList.get(j), "value") + MapUtils.getString(cityList.get(k), "value"), 8));
                    sysCountry.setParentId(provincesId);
                    sysCountry.setPath(MapUtils.getString(list.get(i), "name") + "-" + MapUtils.getString(provincesList.get(j), "name") + "-" + MapUtils.getString(cityList.get(k), "name"));
                    sysCountry.insert();
                    List<Map<String, String>> countyList = lu.getcounty(MapUtils.getString(list.get(i), "name"), MapUtils.getString(provincesList.get(j), "name"), MapUtils.getString(cityList.get(k), "name"));
                    for (int t = 0; t < countyList.size(); t++) {
                        String countyId = UUID.randomUUID().toString().replace("-", "");
                        sysCountry.setId(countyId);
                        sysCountry.setName(MapUtils.getString(countyList.get(t), "name"));
                        sysCountry.setValue(CommonUtils.numberAfterFillZero(MapUtils.getString(list.get(i), "value") + MapUtils.getString(provincesList.get(j), "value") + MapUtils.getString(cityList.get(k), "value") + MapUtils.getString(countyList.get(t), "value"), 8));
                        sysCountry.setParentId(cityId);
                        sysCountry.setPath(MapUtils.getString(list.get(i), "name") + "-" + MapUtils.getString(provincesList.get(j), "name") + "-" + MapUtils.getString(cityList.get(k), "name") + "-" + MapUtils.getString(countyList.get(t), "name"));
                        sysCountry.insert();
                    }
                }
            }
        }
    }
}
