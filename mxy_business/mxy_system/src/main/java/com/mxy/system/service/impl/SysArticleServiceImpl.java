package com.mxy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mxy.common.core.constant.BaseMessage;
import com.mxy.common.core.entity.SelfUserEntity;
import com.mxy.common.core.entity.SysArticle;
import com.mxy.common.core.utils.RedisUtil;
import com.mxy.common.core.utils.ServiceResult;
import com.mxy.system.entity.vo.SysArticleVO;
import com.mxy.system.mapper.SysArticleMapper;
import com.mxy.security.common.util.SecurityUtil;
import com.mxy.system.service.SysArticleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private RedisUtil redisUtil;

    @Override
    public String getList(SysArticleVO sysArticleVO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (StringUtils.isNotEmpty(sysArticleVO.getStatus())) {
            queryWrapper.eq("status", sysArticleVO.getStatus());
        }
        queryWrapper.orderByDesc("create_time");
        Page<SysArticle> page = new Page<>();
        page.setCurrent(sysArticleVO.getCurrentPage());
        page.setSize(sysArticleVO.getPageSize());
        IPage<SysArticle> pageList = this.page(page, queryWrapper);
        List<SysArticle> list = pageList.getRecords();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setContent(HtmlUtils.htmlUnescape(list.get(i).getContent()));
        }
        return ServiceResult.success(pageList);
    }

    @Override
    public String add(SysArticleVO sysArticleVO) {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        SysArticle sysArticle = new SysArticle();
        BeanUtils.copyProperties(sysArticleVO, sysArticle);
        HtmlUtils.htmlEscapeHex(sysArticle.getContent());
        sysArticle.setCreateUser(userDetails.getUsername());
        sysArticle.setPageViews(0);
        Boolean result = sysArticle.insert();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.INSERT_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.INSERT_FAIL);
        }
    }

    @Override
    public String edit(SysArticleVO sysArticleVO) {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        SysArticle sysArticle = new SysArticle();
        BeanUtils.copyProperties(sysArticleVO, sysArticle);
        sysArticle.setUpdateUser(userDetails.getUsername());
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

    @Override
    public String getDataById(String id) {
        SysArticle sysArticle = new SysArticle();
        boolean hasKey = redisUtil.hasKey(id);
        if (hasKey) {
            sysArticle = (SysArticle) redisUtil.get(id);
            sysArticle.setPageViews(sysArticle.getPageViews() == null ? 1 : sysArticle.getPageViews() + 1);
            sysArticle.setContent(HtmlUtils.htmlUnescape(sysArticle.getContent()));
            redisUtil.set(id, sysArticle,365);
        } else {
            sysArticle = this.baseMapper.selectById(id);
            sysArticle.setContent(HtmlUtils.htmlUnescape(sysArticle.getContent()));
            sysArticle.setPageViews(sysArticle.getPageViews() == null ? 1 : sysArticle.getPageViews() + 1);
            redisUtil.set(id, sysArticle,365);
        }
        // 每2次更新一次数据
        if (sysArticle.getPageViews() != 0) {
            sysArticle.updateById();
        }
        return ServiceResult.success(sysArticle);
    }
}
