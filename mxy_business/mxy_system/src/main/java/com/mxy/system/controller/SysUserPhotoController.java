package com.mxy.system.controller;

import com.mxy.common.core.utils.ServiceResult;
import com.mxy.common.log.annotation.SysLog;
import com.mxy.common.core.entity.SysUser;
import com.mxy.system.service.impl.SysUserServiceImpl;
import com.mxy.system.utils.QiniuUploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description 用户头像上传
 * @Author 孟小耀
 * @Date 2021/7/28 17:18
 */
@Api(value = "用户头像上传", tags = "用户头像上传")
@RestController
@CrossOrigin
@RequestMapping("/system/sysUserPhoto")
public class SysUserPhotoController {

    @Autowired
    SysUserServiceImpl sysUserService;


    /**
     * 用户头像上传
     */
    @SysLog(module = "用户头像上传")
    @ApiOperation(value = "用户头像上传", notes = "用户头像上传")
    @PostMapping("/uploadPhoto")
    public String uploadImage(@RequestParam("userId") String userId, @RequestParam("imageUrl") MultipartFile file) throws Exception {
        Boolean b = false;
        SysUser sysUser = sysUserService.getById(userId);
        // 先删 七牛云 中同名文件
        QiniuUploadUtil.deleteImg(sysUser.getUserId());
        // 再修改 成新的图片
        String key = QiniuUploadUtil.upload(sysUser.getUserId(), file.getBytes());
        if (key != null) {
            sysUser.setAvatar(key);
            b = sysUser.updateById();
        }
        if (b) {
            return ServiceResult.success();
        } else {
            return ServiceResult.error();
        }
    }


}

