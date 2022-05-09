package com.mxy.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mxy.common.core.constant.BaseMessage;
import com.mxy.common.core.entity.SelfUserEntity;
import com.mxy.common.core.entity.SysEsData;
import com.mxy.common.core.utils.ServiceResult;
import com.mxy.core.elasticsearch.EsServiceImpl;
import com.mxy.security.common.util.SecurityUtil;
import com.mxy.system.entity.vo.SysEsDataVO;
import com.mxy.system.mapper.SysEsDataMapper;
import com.mxy.system.service.SysEsDataService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.HttpAsyncResponseConsumerFactory;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 * ES数据存储 服务实现类
 * </p>
 *
 * @author 孟小耀
 * @since 2022-05-09
 */
@Service
public class SysEsDataServiceImpl extends ServiceImpl<SysEsDataMapper, SysEsData> implements SysEsDataService {

    private static String INDEX_NAME = "person";

    @Autowired
    public RestHighLevelClient client;

    protected static final RequestOptions COMMON_OPTIONS;

    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
        // 默认缓冲限制为100MB，此处修改为30MB。
        builder.setHttpAsyncResponseConsumerFactory(new HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory(30 * 1024 * 1024));
        COMMON_OPTIONS = builder.build();
    }

    @Resource
    private EsServiceImpl esService;

    @Override
    public String getList(SysEsDataVO sysEsDataVO) {
        List<SysEsData> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        try {
            SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.trackTotalHits(true);
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
            searchSourceBuilder.from(Integer.parseInt(String.valueOf((sysEsDataVO.getCurrentPage() - 1) * sysEsDataVO.getPageSize())));
            searchSourceBuilder.size(Integer.parseInt(String.valueOf(sysEsDataVO.getPageSize())));

            searchRequest.source(searchSourceBuilder);
            SearchResponse search = client.search(searchRequest, COMMON_OPTIONS);

            SearchHit[] hits = search.getHits().getHits();
            long count = search.getHits().getTotalHits().value;
            Arrays.stream(hits).forEach(hit -> {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                SysEsData esData = BeanUtil.mapToBean(sourceAsMap, SysEsData.class, true);
                list.add(esData);
            });

            map.put("current",sysEsDataVO.getCurrentPage());
            map.put("size",sysEsDataVO.getPageSize());
            map.put("total",count);
            map.put("records",list);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ServiceResult.success(map);
    }

    @Override
    public String add(SysEsDataVO sysEsDataVO) {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        SysEsData sysEsData = new SysEsData();
        BeanUtils.copyProperties(sysEsDataVO, sysEsData);
        sysEsData.setCreateUser(userDetails.getUsername());
        Boolean result = sysEsData.insert();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.INSERT_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.INSERT_FAIL);
        }
    }

    @Override
    public String edit(SysEsDataVO sysEsDataVO) {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        SysEsData sysEsData = new SysEsData();
        BeanUtils.copyProperties(sysEsDataVO, sysEsData);
        sysEsData.setUpdateUser(userDetails.getUsername());
        Boolean result = sysEsData.updateById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.UPDATE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.UPDATE_FAIL);
        }
    }

    @Override
    public String delete(SysEsDataVO sysEsDataVO) {
        SysEsData sysEsData = new SysEsData();
        BeanUtils.copyProperties(sysEsDataVO, sysEsData);
        Boolean result = sysEsData.deleteById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.DELETE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.DELETE_FAIL);
        }
    }

}
