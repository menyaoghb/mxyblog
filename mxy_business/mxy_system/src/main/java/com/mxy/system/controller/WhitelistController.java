package com.mxy.system.controller;

import com.mxy.common.core.entity.SysPicture;
import com.mxy.common.core.utils.ServiceResult;
import com.mxy.common.log.annotation.SysLog;
import com.mxy.common.log.enums.OperType;
import com.mxy.system.entity.vo.SysArticleVO;
import com.mxy.system.entity.vo.SysBookmarksVO;
import com.mxy.system.entity.vo.SysMusicVO;
import com.mxy.system.service.*;
import com.mxy.system.utils.LogUtil;
import com.mxy.system.utils.QiniuUploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author mengyao
 */
@Api(value = "白名单请求", tags = "白名单请求")
@RestController
@CrossOrigin
@RequestMapping("/api/foreign")
public class WhitelistController {

    @Resource
    public BeautifulWordsService beautifulWordsService;

    @Resource
    public SysArticleService sysArticleService;

    @Resource
    public SysPictureService sysPictureService;

    @Resource
    public SysBookmarksService sysBookmarksService;

    @Resource
    public SysMusicService sysMusicService;

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
        sysArticleVO.setStatus("0");
        LogUtil.saveBlogLog("博客预览", String.valueOf(sysArticleVO), OperType.BOLG.ordinal());
        return sysArticleService.getWebList(sysArticleVO);
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
    public String uploadPicture(@RequestParam("fileName") String fileName, @RequestParam("fileType") String fileType, @RequestParam("file") MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        map.put("fileName", fileName);
        map.put("fileType", fileType);
        LogUtil.saveBlogLog("图片上传", fileName, OperType.UPLOAD.ordinal());
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
        //QiniuUploadUtil.deleteImg(id);
        // 再修改 成新的图片
        String key = QiniuUploadUtil.upload(id, file.getBytes());
        LogUtil.saveBlogLog("用户头像上传", id, OperType.UPLOAD.ordinal());
        if (key != null) {
            return ServiceResult.success(key);
        } else {
            return ServiceResult.error();
        }
    }

    /**
     * @Description: 图片下载Zip
     * @Author: 孟小耀
     * @Date: 2022/02/17
     */
    @ApiOperation(value = "图片下载", notes = "图片下载")
    @GetMapping("/downloadPicture")
    public void downloadPicture(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "id") String id) {
        SysPicture sysPicture = this.sysPictureService.getById(id);
        try {
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            String header = request.getHeader("User-Agent").toUpperCase();
            String fileName = sysPicture.getPictureName() + ".zip";
            if (!header.contains("MSIE") && !header.contains("TRIDENT") && !header.contains("EDGE")) {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            } else {
                fileName = URLEncoder.encode(fileName, "utf-8");
                fileName = fileName.replace("+", "%20");
            }
            response.setContentType("multipart/form-data");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());
            InputStream is = null;
            try {
                zipOut.putNextEntry(new ZipEntry(sysPicture.getPictureName() + "." + sysPicture.getFileType()));
                String urlName = sysPicture.getPictureId();
                URL url = new URL(urlName);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                // 设置是否向HttpURLConnection输出
                conn.setDoOutput(false);
                // 设置是否从httpUrlConnection读入
                conn.setDoInput(true);
                /** 设置连接方式：GET */
                conn.setRequestMethod("GET");
                /** 设置连接主机服务器超时时间：5000毫秒 */
                conn.setConnectTimeout(5000);
                /** 发送GET方式请求，使用connet方法建立和远程资源之间的实际连接即可 */
                conn.connect();
                int status = conn.getResponseCode();
                if (status != HttpURLConnection.HTTP_OK) {
                    is = conn.getErrorStream();
                } else {
                    is = conn.getInputStream();
                }
                int temp = 0;
                while ((temp = is.read()) != -1) {
                    zipOut.write(temp);
                }
                is.close();
                /** 关闭远程连接 */
                conn.disconnect();
            } catch (IOException e) {

            }
            zipOut.closeEntry();
            zipOut.close();
        } catch (Exception e) {
            throw new RuntimeException("图片下载失败");
        }
        LogUtil.saveBlogLog("图片下载", id, OperType.DOWNLOAD.ordinal());
    }

    /**
     * @Description 获取收藏书签列表
     * @author 孟小耀
     * @date 2022-03-07
     */
    @ApiOperation(value = "查询收藏书签列表")
    @PostMapping("/getBookmarkList")
    public String getBookmarkList(@RequestBody SysBookmarksVO sysBookmarksVO) {
        sysBookmarksVO.setStatus("0");
        LogUtil.saveBlogLog("博客收藏书签查询", String.valueOf(sysBookmarksVO), OperType.SELECT.ordinal());
        return sysBookmarksService.getList(sysBookmarksVO);
    }

    /**
     * @Description 查询音乐列表
     * @author 孟小耀
     * @date 2022-09-13
     */
    @ApiOperation(value = "查询音乐列表")
    @PostMapping("/getMusicList")
    public String getMusicList(@RequestBody SysMusicVO sysMusicVO) {
        return sysMusicService.getList(sysMusicVO);
    }

}
