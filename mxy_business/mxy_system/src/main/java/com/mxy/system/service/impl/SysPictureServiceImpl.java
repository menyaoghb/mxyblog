package com.mxy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mxy.common.core.constant.BaseMessage;
import com.mxy.common.core.entity.SelfUserEntity;
import com.mxy.common.core.entity.SysPicture;
import com.mxy.common.core.utils.ServiceResult;
import com.mxy.system.entity.vo.SysPictureVO;
import com.mxy.system.mapper.SysPictureMapper;
import com.mxy.system.security.common.util.SecurityUtil;
import com.mxy.system.service.SysPictureService;
import com.mxy.system.utils.QiniuUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 图片 服务实现类
 * </p>
 *
 * @author 孟小耀
 * @since 2021-08-18
 */
@Slf4j
@Service
public class SysPictureServiceImpl extends ServiceImpl<SysPictureMapper, SysPicture> implements SysPictureService {

    @Override
    public String getList(SysPictureVO sysPictureVO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (StringUtils.isNotEmpty(sysPictureVO.getPictureName())) {
            queryWrapper.like("picture_name", sysPictureVO.getPictureName());
        }
        queryWrapper.orderByDesc("create_time");
        Page<SysPicture> page = new Page<>();
        page.setCurrent(sysPictureVO.getCurrentPage());
        page.setSize(sysPictureVO.getPageSize());
        IPage<SysPicture> pageList = this.page(page, queryWrapper);
        return ServiceResult.success(pageList);
    }

    @Override
    public String add(SysPictureVO sysPictureVO) {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        SysPicture sysPicture = new SysPicture();
        BeanUtils.copyProperties(sysPictureVO, sysPicture);
        sysPicture.setCreateUser(userDetails.getUsername());
        Boolean result = sysPicture.insert();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.INSERT_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.INSERT_FAIL);
        }
    }

    @Override
    public String edit(SysPictureVO sysPictureVO) {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        SysPicture sysPicture = new SysPicture();
        BeanUtils.copyProperties(sysPictureVO, sysPicture);
        sysPicture.setUpdateUser(userDetails.getUsername());
        Boolean result = sysPicture.updateById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.UPDATE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.UPDATE_FAIL);
        }
    }

    @Override
    public String delete(SysPictureVO sysPictureVO) {
        SysPicture sysPicture = new SysPicture();
        BeanUtils.copyProperties(sysPictureVO, sysPicture);
        Boolean result = sysPicture.deleteById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.DELETE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.DELETE_FAIL);
        }
    }

    @Override
    public String getFileTypeList(SysPictureVO sysPictureVO) {
        // 查询 图片标签 字段
        List<Map<String, String>> typeMap = this.baseMapper.selectFileTypeData();
        Map<String, Object> map = new HashMap<>();
        map.put("typeMap", typeMap);
        return ServiceResult.success(map);
    }

    @Override
    public String uploadPicture(Map<String, Object> map, MultipartFile[] file) {
        Boolean b = false;
        try {
            for (int i = 0; i < file.length; i++) {
                MultipartFile multipartFile = file[i];
                String id = UUID.randomUUID().toString();
                String name = multipartFile.getOriginalFilename();
                String key = QiniuUploadUtil.upload(id, multipartFile.getBytes());
                if (key != null) {
                    SysPicture sysPicture = new SysPicture();
                    sysPicture.setId(id);
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
        } catch (IOException e) {
            log.info("图片上传失败" + e.getMessage());
            e.printStackTrace();
        }
        if (b) {
            return ServiceResult.success();
        } else {
            return ServiceResult.error();
        }
    }

}
