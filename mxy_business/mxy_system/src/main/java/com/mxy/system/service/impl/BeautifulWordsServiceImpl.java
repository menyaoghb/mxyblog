package com.mxy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxy.common.core.constant.BaseMessage;
import com.mxy.common.core.utils.ServiceResult;
import com.mxy.common.core.entity.BeautifulWords;
import com.mxy.system.entity.vo.BeautifulWordsVO;
import com.mxy.system.mapper.BeautifulWordsMapper;
import com.mxy.system.service.BeautifulWordsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 优美的句子 服务实现类
 * </p>
 *
 * @author 孟小耀
 * @since 2021-08-06
 */
@Service
public class BeautifulWordsServiceImpl extends ServiceImpl<BeautifulWordsMapper, BeautifulWords> implements BeautifulWordsService {

    @Override
    public String getList(BeautifulWordsVO beautifulWordsVO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (StringUtils.isNotEmpty(beautifulWordsVO.getBeautifulWords())) {
            queryWrapper.like("beautiful_words", beautifulWordsVO.getBeautifulWords());
        }
        if (StringUtils.isNotEmpty(beautifulWordsVO.getInspiration())) {
            queryWrapper.eq("inspiration", beautifulWordsVO.getInspiration());
        }
        if (StringUtils.isNotEmpty(beautifulWordsVO.getLabel())) {
            queryWrapper.eq("label", beautifulWordsVO.getLabel());
        }
        queryWrapper.orderByDesc("update_time,create_time");
        Page<BeautifulWords> page = new Page<>();
        page.setCurrent(beautifulWordsVO.getCurrentPage());
        page.setSize(beautifulWordsVO.getPageSize());
        IPage<BeautifulWords> pageList = this.page(page, queryWrapper);
        return ServiceResult.success(pageList);
    }

    @Override
    public String add(BeautifulWordsVO beautifulWordsVO) {
        BeautifulWords beautifulWords = new BeautifulWords();
        BeanUtils.copyProperties(beautifulWordsVO, beautifulWords);
        Boolean result = beautifulWords.insert();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.INSERT_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.INSERT_FAIL);
        }
    }

    @Override
    public String edit(BeautifulWordsVO beautifulWordsVO) {
        BeautifulWords beautifulWords = new BeautifulWords();
        BeanUtils.copyProperties(beautifulWordsVO, beautifulWords);
        Boolean result = beautifulWords.updateById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.UPDATE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.UPDATE_FAIL);
        }
    }

    @Override
    public String delete(BeautifulWordsVO beautifulWordsVO) {
        BeautifulWords beautifulWords = new BeautifulWords();
        BeanUtils.copyProperties(beautifulWordsVO, beautifulWords);
        Boolean result = beautifulWords.deleteById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.DELETE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.DELETE_FAIL);
        }
    }

    @Override
    public String getAdviceList(BeautifulWordsVO beautifulWordsVO) {
        // 查询 标签 字段
        List<Map<String, String>> labMap = this.baseMapper.selectLabData();
        // 查询 来源 字段
        List<Map<String, String>> insMap = this.baseMapper.selectInsData();
        Map<String, Object> map = new HashMap<>();
        map.put("labMap", labMap);
        map.put("insMap", insMap);
        return ServiceResult.success(map);
    }

}
