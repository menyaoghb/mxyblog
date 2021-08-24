package com.mxy.system.controller;

import com.mxy.common.core.entity.SysPicture;
import com.mxy.common.core.entity.SysUser;
import com.mxy.common.core.utils.ServiceResult;
import com.mxy.common.log.annotation.SysLog;
import com.mxy.common.log.enums.OperType;
import com.mxy.system.entity.vo.SysPictureVO;
import com.mxy.system.service.SysPictureService;
import com.mxy.system.utils.QiniuUploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 图片 前端控制器
 * </p>
 *
 * @author 孟小耀
 * @since 2021-08-18
 */
@Api(value = "图片", tags = "图片")
@RestController
@RequestMapping("/system/sysPicture")
public class SysPictureController {

    @Autowired
    public SysPictureService sysPictureService;

    /**
     * @Description 获取图片列表
     * @author 孟小耀
     * @date 2021-08-18
     */
    @SysLog(module = "获取图片列表")
    @ApiOperation(value = "获取图片列表")
    @PostMapping("/getList")
    public String getList(@RequestBody SysPictureVO sysPictureVO) {
        return sysPictureService.getList(sysPictureVO);
    }

    /**
     * @Description 新增图片
     * @author 孟小耀
     * @date 2021-08-18
     */
    @SysLog(module = "新增图片", operType = OperType.ADD)
    @ApiOperation(value = "新增图片")
    @PostMapping("/add")
    public String add(@RequestBody SysPictureVO sysPictureVO) {
        return sysPictureService.add(sysPictureVO);
    }

    /**
     * @Description 编辑图片
     * @author 孟小耀
     * @date 2021-08-18
     */
    @SysLog(module = "编辑图片", operType = OperType.UPDATE)
    @ApiOperation(value = "编辑图片")
    @PostMapping("/edit")
    public String edit(@RequestBody SysPictureVO sysPictureVO) {
        return sysPictureService.edit(sysPictureVO);
    }

    /**
     * @Description 删除图片
     * @author 孟小耀
     * @date 2021-08-18
     */
    @SysLog(module = "删除图片", operType = OperType.DELETE)
    @ApiOperation(value = "删除图片")
    @PostMapping("/delete")
    public String delete(@RequestBody SysPictureVO sysPictureVO) {
        return sysPictureService.delete(sysPictureVO);
    }

    /**
     * @Description: 图片上传
     * @Author: 孟小耀
     * @Date: 2021/8/23
     */
    @SysLog(module = "图片上传", operType = OperType.UPLOAD)
    @ApiOperation(value = "图片上传", notes = "图片上传")
    @PostMapping("/uploadPicture")
    public String uploadPicture(@RequestParam Map<String, Object> map, @RequestParam("file") MultipartFile[] file) throws Exception {
        Boolean b = false;
        for (int i = 0; i < file.length; i++) {
            MultipartFile multipartFile = file[i];
            String name = multipartFile.getOriginalFilename();
            String key = QiniuUploadUtil.upload(name, multipartFile.getBytes());
            if (key != null) {
                SysPicture sysPicture = new SysPicture();
                sysPicture.setPictureId(key);
                if (StringUtils.isNotEmpty(MapUtils.getString(map, "fileType"))) {
                    sysPicture.setType(MapUtils.getString(map, "fileType"));
                } else {
                    sysPicture.setType(MapUtils.getString(map, "缘分"));
                }
                if (StringUtils.isNotEmpty(MapUtils.getString(map, "fileName"))) {
                    sysPicture.setPictureName(MapUtils.getString(map, "fileName"));
                } else {
                    sysPicture.setPictureName(MapUtils.getString(map, name));
                }

                b = sysPicture.insert();
            }
        }
        if (b) {
            return ServiceResult.success();
        } else {
            return ServiceResult.error();
        }
    }


}

