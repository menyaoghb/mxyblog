package com.mxy.security.controller;

import com.mxy.common.core.entity.SysCountry;
import com.mxy.core.elasticsearch.EsServiceImpl;
import com.mxy.system.service.SysCountryService;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 初始页面
 * @Author Mxy
 * @CreateTime 2022/01/2 15:11
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    public RestHighLevelClient client;

    @Autowired
    public SysCountryService sysEsDataService;

    @Resource
    private EsServiceImpl esService;

    /**
     * 首页
     * @Author Mxy
     * @CreateTime 2022/01/2 15:23
     * @Return Map<String,Object> 返回数据MAP
     */
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public void userLogin(){

    }

    @RequestMapping(value = "/insert",method = RequestMethod.GET)
    public void insert(){
        List<SysCountry> sysCountryList = sysEsDataService.selectList();
        for (SysCountry sysCountry : sysCountryList) {
            esService.insertRequest("country", sysCountry.getId(), sysCountry);
        }
    }

}
