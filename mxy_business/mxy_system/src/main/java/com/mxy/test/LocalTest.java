package com.mxy.test;

import com.mxy.common.core.utils.LocalUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 地市测试类
 * @Date 2022/5/10 18:09
 */
public class LocalTest {

    @Test
    public void test1(){
        LocalUtil lu =  LocalUtil.getInstance();
        List<String> list =  lu.getCities("中国", "北京");
        for(int i=0; i<list.size(); i++){
            System.out.print(list.get(i) + " ");
        }
    }
    @Test
    public void test2(){
        LocalUtil lu =  LocalUtil.getInstance();
        List<String> list =  lu.getProvinces("中国");
        int j=0;
        for(int i=0; i<list.size(); i++){
            j++;
            System.out.print(list.get(i) + " "+"\n");
        }
        System.out.println(j);
    }
    @Test
    public  void test3(){
        LocalUtil lu = LocalUtil.getInstance();
        List<String> str = new ArrayList<>();
        List<String> list = lu.getcounty("中国", "浙江", "杭州");
        for (int i = 0; i < list.size(); i++) {
            str.add(list.get(i) + " ");
        }
        System.out.println(str);
    }

    @Test
    public  void test4(){
        LocalUtil lu = LocalUtil.getInstance();
        List<String> country = new ArrayList<>();
        List<String> provinces = new ArrayList<>();
        List<String> city = new ArrayList<>();
        List<String> county = new ArrayList<>();
        List<String> list = lu.getCountry();
        for (int i = 0; i < list.size(); i++) {
            country.add(list.get(i));
            List<String> provincesList =  lu.getProvinces(list.get(i));
            for(int j=0; j<provincesList.size(); j++){
                provinces.add(provincesList.get(j));
                List<String> cityList =  lu.getCities(list.get(i), provincesList.get(j));
                for(int k=0; k<cityList.size(); k++){
                    city.add(cityList.get(k));
                    List<String> countyList = lu.getcounty(list.get(i), provincesList.get(j), cityList.get(k));
                    for (int t = 0; t < countyList.size(); t++) {
                        county.add(countyList.get(t));
                    }
                }
            }
        }
    }
}
