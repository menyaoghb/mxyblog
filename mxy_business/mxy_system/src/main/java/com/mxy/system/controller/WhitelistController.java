package com.mxy.system.controller;

import com.mxy.system.entity.vo.SysArticleVO;
import com.mxy.system.service.BeautifulWordsService;
import com.mxy.system.service.SysArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    public SysArticleService sysArticleService;

    /**
     * @Description 登录页随机获取一条句子
     * @author 孟小耀
     * @date 2022-02-07
     */
    @ApiOperation(value = "后台系统-首页随机句")
    @PostMapping("/getRandWord")
    public String getRandWord() {
        return beautifulWordsService.getRandWord();
    }


    /**
     * @Description 博客网站-首页文章列表展示
     * @author 孟小耀
     * @date 2022-02-09
     */
    @ApiOperation(value = "博客网站-文章列表展示")
    @PostMapping("/getList")
    public String getList(@RequestBody SysArticleVO sysArticleVO) {
        return sysArticleService.getList(sysArticleVO);
    }

    /**
     * @Description 博客网站-文章详细
     * @author 孟小耀
     * @date 2022-02-09
     */
    @ApiOperation(value = "博客网站-文章详细")
    @GetMapping("/getDataById")
    public String getDataById(@RequestParam(value = "id") String id) {
        return sysArticleService.getDataById(id);
    }
}
