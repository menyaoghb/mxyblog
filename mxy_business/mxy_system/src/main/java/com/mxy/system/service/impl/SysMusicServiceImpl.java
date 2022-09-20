package com.mxy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxy.common.core.constant.BaseMessage;
import com.mxy.common.core.entity.SelfUserEntity;
import com.mxy.common.core.utils.ServiceResult;
import com.mxy.security.common.util.SecurityUtil;
import com.mxy.common.core.entity.SysMusic;
import com.mxy.system.entity.vo.SysMusicVO;
import com.mxy.system.mapper.SysMusicMapper;
import com.mxy.system.service.SysMusicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

/**
 * <p>
 * 音乐 服务实现类
 * </p>
 *
 * @author 孟小耀
 * @since 2022-09-13
 */
@Service
public class SysMusicServiceImpl extends ServiceImpl<SysMusicMapper, SysMusic> implements SysMusicService {

    @Override
    public String getList(SysMusicVO sysMusicVO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        Page<SysMusic> page = new Page<>();
        page.setCurrent(sysMusicVO.getCurrentPage());
        page.setSize(sysMusicVO.getPageSize());
        IPage<SysMusic> pageList = this.page(page, queryWrapper);
        return ServiceResult.success(pageList);
    }

    @Override
    public String add(SysMusicVO sysMusicVO) {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        SysMusic sysMusic = new SysMusic();
        BeanUtils.copyProperties(sysMusicVO, sysMusic);
        sysMusic.setCreateUser(userDetails.getUsername());
        sysMusic.setStatus("0");
        sysMusic.setIsDelete("0");
        Boolean result = sysMusic.insert();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.INSERT_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.INSERT_FAIL);
        }
    }

    @Override
    public String edit(SysMusicVO sysMusicVO) {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        SysMusic sysMusic = new SysMusic();
        BeanUtils.copyProperties(sysMusicVO, sysMusic);
        sysMusic.setUpdateUser(userDetails.getUsername());
        Boolean result = sysMusic.updateById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.UPDATE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.UPDATE_FAIL);
        }
    }

    @Override
    public String delete(SysMusicVO sysMusicVO) {
        SysMusic sysMusic = new SysMusic();
        BeanUtils.copyProperties(sysMusicVO, sysMusic);
        Boolean result = sysMusic.deleteById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.DELETE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.DELETE_FAIL);
        }
    }

}
