package com.mxy.common.core.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.*;

/**
 * @Description 选择地区工具，包含全国各地省级市级
 * @Date 2022/5/10 18:01
 */
public class LocalUtil {
    /**
     * 各地区xml⽂件路径
     */
    private static final String LOCAL_LIST_PATH = "D:\\LifeSoft\\Tim\\I18N\\2052\\LocList.xml";

    /**
     * 所有国家名称List
     */
    private static final List<Map<String, String>> COUNTRY_REGION = new ArrayList<>();
    private static LocalUtil localutil;
    private SAXReader reader;
    private Document document;

    /**
     * 根元素
     */
    private Element rootElement;


    /**
     * 初始化
     */
    private LocalUtil() {
        //1.读取
        reader = new SAXReader();
        try {
            document = reader.read(LOCAL_LIST_PATH);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        //2.获得根元素
        rootElement = document.getRootElement();
        //3.初始化所有国家名称列表
        Iterator it = rootElement.elementIterator();
        Element ele = null;
        while (it.hasNext()) {
            ele = (Element) it.next();
            Map<String, String> map = new HashMap<>();
            map.put("name",ele.attributeValue("Name"));
            map.put("value",ele.attributeValue("Code"));
            COUNTRY_REGION.add(map);
        }
    }

    /**
     * 获取所有国家名称
     */
    public List<Map<String, String>> getCountry() {
        return COUNTRY_REGION;
    }

    /**
     * 根据国家名获取该国所有省份
     */
    private List<Element> provinces(String countryName) {
        Iterator it = rootElement.elementIterator();
        List<Element> provinces = new ArrayList<Element>();
        Element ele = null;
        while (it.hasNext()) {
            ele = (Element) it.next();
            Map<String, String> map = new HashMap<>();
            map.put("name",ele.attributeValue("Name"));
            map.put("value",ele.attributeValue("Code"));
            COUNTRY_REGION.add(map);
            if (ele.attributeValue("Name").equals(countryName)) {
                provinces = ele.elements();
                break;
            }
        }
        return provinces;
    }

    /**
     * 根据国家名获取该国所有省份
     *
     * @param countryName 国家名，从getCountry()从取出
     * @return List<Element>
     */
    public List<Map<String, String>> getProvinces(String countryName) {
        List<Element> tmp = this.provinces(countryName);
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < tmp.size(); i++) {
            Map<String, String> map = new HashMap<>();
            map.put("name",tmp.get(i).attributeValue("Name"));
            map.put("value",tmp.get(i).attributeValue("Code"));
            list.add(map);
        }
        return list;
    }

    /**
     * 根据国家名和省份名，获取该省城市名列表
     *
     * @param countryName  国家名
     * @param provinceName 省份名
     * @return List<Element>
     */
    private List<Element> cities(String countryName, String provinceName) {
        List<Element> provinces = this.provinces(countryName);
        List<Element> cities = new ArrayList<Element>();
        // 没有这个城市
        if (provinces == null || provinces.size() == 0) {
            return cities;
        }

        for (int i = 0; i < provinces.size(); i++) {
            if (provinces.get(i).attributeValue("Name").equals(provinceName)) {
                cities = provinces.get(i).elements();
                break;
            }
        }
        return cities;
    }


    /**
     * 根据国家名和省份名获取城市列表
     *
     * @param countryName  国家名
     * @param provinceName 省份名
     * @return List<String>
     */
    public List<Map<String, String>> getCities(String countryName, String provinceName) {
        List<Element> tmp = this.cities(countryName, provinceName);
        List<Map<String, String>> cities = new ArrayList<>();
        for (int i = 0; i < tmp.size(); i++) {
            Map<String, String> map = new HashMap<>();
            map.put("name",tmp.get(i).attributeValue("Name"));
            map.put("value",tmp.get(i).attributeValue("Code"));
            cities.add(map);
        }
        return cities;
    }

    /**
     * 根据国家名和省份名和城市名，获取该城市县级名称列表
     *
     * @param countryName
     * @param provinceName
     * @param city
     * @return
     */
    private List<Element> county(String countryName, String provinceName, String city) {
        //获取城市名称
        List<Element> cities1 = this.cities(countryName, provinceName);
        List<Element> cities = new ArrayList<Element>();
        // 没有这个县
        if (cities1 == null || cities1.size() == 0) {
            return cities;
        }
        for (int i = 0; i < cities1.size(); i++) {
            if (cities1.get(i).attributeValue("Name").equals(city)) {
                cities = cities1.get(i).elements();
                break;
            }
        }
        return cities;
    }

    /**
     * 根据国家,省份和城市名获取县级名称
     *
     * @param countryName
     * @param provinceName
     * @param city
     * @return
     */
    public List<Map<String, String>> getcounty(String countryName, String provinceName, String city) {
        List<Element> tmp = this.county(countryName, provinceName, city);
        List<Map<String, String>> cities = new ArrayList<>();
        for (int i = 0; i < tmp.size(); i++) {
            Map<String, String> map = new HashMap<>();
            map.put("name",tmp.get(i).attributeValue("Name"));
            map.put("value",tmp.get(i).attributeValue("Code"));
            cities.add(map);
        }
        return cities;
    }

    public static LocalUtil getInstance() {
        if (localutil == null) {
            localutil = new LocalUtil();
        }
        return localutil;
    }
}


