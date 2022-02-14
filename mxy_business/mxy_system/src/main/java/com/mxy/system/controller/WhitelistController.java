package com.mxy.system.controller;

import com.mxy.common.core.utils.ServiceResult;
import com.mxy.system.entity.vo.SysArticleVO;
import com.mxy.system.service.BeautifulWordsService;
import com.mxy.system.service.SysArticleService;
import com.mxy.system.service.SysPictureService;
import com.mxy.system.utils.QiniuUploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    @Autowired
    public SysPictureService sysPictureService;

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


    /**
     * @Description: 图片上传
     * @Author: 孟小耀
     * @Date: 2021/8/23
     */
    @ApiOperation(value = "图片上传", notes = "图片上传")
    @PostMapping("/uploadPicture")
    public String uploadPicture(@RequestParam("fileName") String fileName,@RequestParam("fileType") String fileType, @RequestParam("file") MultipartFile file) {
        Map<String,Object> map = new HashMap<>();
        map.put("fileName",fileName);
        map.put("fileType",fileType);
        return sysPictureService.uploadPicture(map, file);
    }

    /**
     * 用户头像上传
     */
    @ApiOperation(value = "用户头像上传", notes = "用户头像上传")
    @PostMapping("/uploadPhoto")
    public String uploadImage(@RequestParam("imageUrl") MultipartFile file) throws Exception {
        String id = UUID.randomUUID().toString();
        // 先删 七牛云 中同名文件
        QiniuUploadUtil.deleteImg(id);
        // 再修改 成新的图片
        String key = QiniuUploadUtil.upload(id, file.getBytes());
        if (key != null) {
            return ServiceResult.success(key);
        } else {
            return ServiceResult.error();
        }
    }

}
