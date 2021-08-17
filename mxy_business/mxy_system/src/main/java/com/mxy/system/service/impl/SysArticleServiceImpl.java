package com.mxy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxy.common.core.constant.BaseMessage;
import com.mxy.common.core.utils.ServiceResult;
import com.mxy.common.core.entity.SysArticle;
import com.mxy.system.entity.vo.SysArticleVO;
import com.mxy.system.mapper.SysArticleMapper;
import com.mxy.system.service.SysArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

/**
 * <p>
 * 文章 服务实现类
 * </p>
 *
 * @author 孟小耀
 * @since 2021-08-17
 */
@Service
public class SysArticleServiceImpl extends ServiceImpl<SysArticleMapper, SysArticle> implements SysArticleService {

    @Override
    public String getList(SysArticleVO sysArticleVO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        Page<SysArticle> page = new Page<>();
        page.setCurrent(sysArticleVO.getCurrentPage());
        page.setSize(sysArticleVO.getPageSize());
        IPage<SysArticle> pageList = this.page(page, queryWrapper);
        return ServiceResult.success(pageList);
    }

    @Override
    public String add(SysArticleVO sysArticleVO) {
        SysArticle sysArticle = new SysArticle();
        BeanUtils.copyProperties(sysArticleVO, sysArticle);
        Boolean result = sysArticle.insert();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.INSERT_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.INSERT_FAIL);
        }
    }

    @Override
    public String edit(SysArticleVO sysArticleVO) {
        SysArticle sysArticle = new SysArticle();
        BeanUtils.copyProperties(sysArticleVO, sysArticle);
        Boolean result = sysArticle.updateById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.UPDATE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.UPDATE_FAIL);
        }
    }

    @Override
    public String delete(SysArticleVO sysArticleVO) {
        SysArticle sysArticle = new SysArticle();
        BeanUtils.copyProperties(sysArticleVO, sysArticle);
        Boolean result = sysArticle.deleteById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.DELETE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.DELETE_FAIL);
        }
    }

}
