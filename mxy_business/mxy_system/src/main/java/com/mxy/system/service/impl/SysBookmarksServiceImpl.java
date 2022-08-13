package com.mxy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mxy.common.core.constant.BaseMessage;
import com.mxy.common.core.entity.SelfUserEntity;
import com.mxy.common.core.entity.SysBookmarks;
import com.mxy.common.core.utils.ServiceResult;
import com.mxy.system.entity.vo.SysBookmarksVO;
import com.mxy.system.mapper.SysBookmarksMapper;
import com.mxy.security.common.util.SecurityUtil;
import com.mxy.system.service.SysBookmarksService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 收藏书签 服务实现类
 * </p>
 *
 * @author 孟小耀
 * @since 2022-03-07
 */
@Service
public class SysBookmarksServiceImpl extends ServiceImpl<SysBookmarksMapper, SysBookmarks> implements SysBookmarksService {

    @Override
    public String getList(SysBookmarksVO sysBookmarksVO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (StringUtils.isNotEmpty(sysBookmarksVO.getName())) {
            queryWrapper.like("name", sysBookmarksVO.getName());
        }
        queryWrapper.orderByDesc("create_time");
        Page<SysBookmarks> page = new Page<>();
        page.setCurrent(sysBookmarksVO.getCurrentPage());
        page.setSize(sysBookmarksVO.getPageSize());
        IPage<SysBookmarks> pageList = this.page(page, queryWrapper);
        return ServiceResult.success(pageList);
    }

    @Override
    public String add(SysBookmarksVO sysBookmarksVO) {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        SysBookmarks sysBookmarks = new SysBookmarks();
        BeanUtils.copyProperties(sysBookmarksVO, sysBookmarks);
        sysBookmarks.setCreateUser(userDetails.getUsername());
        Boolean result = sysBookmarks.insert();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.INSERT_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.INSERT_FAIL);
        }
    }

    @Override
    public String edit(SysBookmarksVO sysBookmarksVO) {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        SysBookmarks sysBookmarks = new SysBookmarks();
        BeanUtils.copyProperties(sysBookmarksVO, sysBookmarks);
        sysBookmarks.setUpdateUser(userDetails.getUsername());
        Boolean result = sysBookmarks.updateById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.UPDATE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.UPDATE_FAIL);
        }
    }

    @Override
    public String delete(SysBookmarksVO sysBookmarksVO) {
        SysBookmarks sysBookmarks = new SysBookmarks();
        BeanUtils.copyProperties(sysBookmarksVO, sysBookmarks);
        Boolean result = sysBookmarks.deleteById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.DELETE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.DELETE_FAIL);
        }
    }


    public static void main(String[] args) {
        int a = 0;
        a += 1;
        a += 0;
        a += 0;
        a += 1;
        a += 0;
        System.out.println(a);
    }

}
