package com.mxy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxy.common.core.constant.BaseMessage;
import com.mxy.common.core.utils.ServiceResult;
import com.mxy.system.entity.SysDictData;
import com.mxy.system.entity.vo.SysDictDataVO;
import com.mxy.system.mapper.SysDictDataMapper;
import com.mxy.system.service.SysDictDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

/**
 * <p>
 * 字典数据 服务实现类
 * </p>
 *
 * @author 孟小耀
 * @since 2021-07-25
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {

    @Override
    public String getList(SysDictDataVO sysDictDataVO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        Page<SysDictData> page = new Page<>();
        page.setCurrent(sysDictDataVO.getCurrentPage());
        page.setSize(sysDictDataVO.getPageSize());
        IPage<SysDictData> pageList = this.page(page, queryWrapper);
        return ServiceResult.success(pageList);
    }

    @Override
    public String add(SysDictDataVO sysDictDataVO) {
        SysDictData sysDictData = new SysDictData();
        BeanUtils.copyProperties(sysDictDataVO, sysDictData);
        Boolean result = sysDictData.insert();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.INSERT_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.INSERT_FAIL);
        }
    }

    @Override
    public String edit(SysDictDataVO sysDictDataVO) {
        SysDictData sysDictData = new SysDictData();
        BeanUtils.copyProperties(sysDictDataVO, sysDictData);
        Boolean result = sysDictData.updateById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.UPDATE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.UPDATE_FAIL);
        }
    }

    @Override
    public String delete(SysDictDataVO sysDictDataVO) {
        SysDictData sysDictData = new SysDictData();
        BeanUtils.copyProperties(sysDictDataVO, sysDictData);
        Boolean result = sysDictData.deleteById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.DELETE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.DELETE_FAIL);
        }
    }

}
