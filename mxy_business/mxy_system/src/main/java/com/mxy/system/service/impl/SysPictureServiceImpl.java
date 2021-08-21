package com.mxy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxy.common.core.constant.BaseMessage;
import com.mxy.common.core.entity.SysPicture;
import com.mxy.common.core.utils.ServiceResult;
import com.mxy.system.entity.vo.SysPictureVO;
import com.mxy.system.mapper.SysPictureMapper;
import com.mxy.system.service.SysPictureService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

/**
 * <p>
 * 图片 服务实现类
 * </p>
 *
 * @author 孟小耀
 * @since 2021-08-18
 */
@Service
public class SysPictureServiceImpl extends ServiceImpl<SysPictureMapper, SysPicture> implements SysPictureService {

    @Override
    public String getList(SysPictureVO sysPictureVO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        Page<SysPicture> page = new Page<>();
        page.setCurrent(sysPictureVO.getCurrentPage());
        page.setSize(sysPictureVO.getPageSize());
        IPage<SysPicture> pageList = this.page(page, queryWrapper);
        return ServiceResult.success(pageList);
    }

    @Override
    public String add(SysPictureVO sysPictureVO) {
        SysPicture sysPicture = new SysPicture();
        BeanUtils.copyProperties(sysPictureVO, sysPicture);
        Boolean result = sysPicture.insert();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.INSERT_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.INSERT_FAIL);
        }
    }

    @Override
    public String edit(SysPictureVO sysPictureVO) {
        SysPicture sysPicture = new SysPicture();
        BeanUtils.copyProperties(sysPictureVO, sysPicture);
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

}
