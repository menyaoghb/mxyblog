package com.mxy.system.controller;

import com.mxy.system.service.BeautifulWordsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 孟耀
 */
@Api(value = "白名单请求",tags = "白名单请求")
@RestController
@CrossOrigin
@RequestMapping("/api/foreign")
public class WhitelistController {

    @Autowired
    public BeautifulWordsService beautifulWordsService;

    /**
     * @Description 登录页随机获取一条句子
     * @author 孟小耀
     * @date 2022-02-07
     */
    @ApiOperation(value = "首页随机句")
    @PostMapping("/getRandWord")
    public String getRandWord() {
        return beautifulWordsService.getRandWord();
    }
}
