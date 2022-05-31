package com.mxy.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mxy.common.core.constant.BaseMessage;
import com.mxy.common.core.entity.SelfUserEntity;
import com.mxy.common.core.entity.SysEsData;
import com.mxy.common.core.utils.ServiceResult;
import com.mxy.security.common.util.SecurityUtil;
import com.mxy.system.entity.vo.SysEsDataVO;
import com.mxy.system.mapper.SysEsDataMapper;
import com.mxy.system.service.SysEsDataService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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


    @Override
    public String getList(SysEsDataVO sysEsDataVO) {
        List<SysEsData> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        try {
            SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            BoolQueryBuilder bool = new BoolQueryBuilder();

            // 精确查询
            if (StringUtils.isNotEmpty(sysEsDataVO.getPhone())) {
                // 手机号码
                bool.must(new TermQueryBuilder("phone", sysEsDataVO.getPhone()));
            }
            if (StringUtils.isNotEmpty(sysEsDataVO.getFieldOne())) {
                // 数据id
                bool.must(new TermQueryBuilder("fieldOne", sysEsDataVO.getFieldOne()));
            }
            if (StringUtils.isNotBlank(sysEsDataVO.getMinSalary())||StringUtils.isNotBlank(sysEsDataVO.getMaxSalary())) {
                if (StringUtils.isNotBlank(sysEsDataVO.getMinSalary())&&StringUtils.isNotBlank(sysEsDataVO.getMaxSalary())){
                    // 工资范围查询
                    RangeQueryBuilder salaryRange = new RangeQueryBuilder("salary");
                    salaryRange.gte(sysEsDataVO.getMinSalary());
                    salaryRange.lte(sysEsDataVO.getMaxSalary());
                    bool.must(salaryRange);
                }else if (StringUtils.isNotBlank(sysEsDataVO.getMinSalary())){
                    bool.must(new TermQueryBuilder("salary", sysEsDataVO.getMinSalary()));
                }else {
                    bool.must(new TermQueryBuilder("salary", sysEsDataVO.getMaxSalary()));
                }
            }
            if (StringUtils.isNotBlank(sysEsDataVO.getStatus())) {
                bool.must(new TermQueryBuilder("status.keyword", sysEsDataVO.getStatus()));
            }
            // 模糊查询
            if (StringUtils.isNotBlank(sysEsDataVO.getName())) {
                bool.must(QueryBuilders.wildcardQuery("name.keyword", "*" + sysEsDataVO.getName() + "*"));
            }
            if (StringUtils.isNotBlank(sysEsDataVO.getAddress())) {
                bool.must(QueryBuilders.wildcardQuery("address.keyword", "*" + sysEsDataVO.getAddress() + "*"));
            }
            if (StringUtils.isNotBlank(sysEsDataVO.getCompany())) {
                bool.must(QueryBuilders.wildcardQuery("company.keyword", "*" + sysEsDataVO.getCompany() + "*"));
            }
            //日期查询
            if (StringUtils.isNotBlank(sysEsDataVO.getStartTime()) && StringUtils.isNotBlank(sysEsDataVO.getEndTime())) {
                RangeQueryBuilder timeRange = new RangeQueryBuilder("createTime");
                timeRange.gte(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sysEsDataVO.getStartTime()));
                timeRange.lte(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sysEsDataVO.getEndTime()));
                bool.must(timeRange);
            }
            searchSourceBuilder.query(bool);
            // 分页
            searchSourceBuilder.from(Integer.parseInt(String.valueOf((sysEsDataVO.getCurrentPage() - 1) * sysEsDataVO.getPageSize())));
            searchSourceBuilder.size(Integer.parseInt(String.valueOf(sysEsDataVO.getPageSize())));
            // 时间排序
            searchSourceBuilder.sort("createTime", SortOrder.DESC);
            // 获取超过1w条数据 需要加上  "track_total_hits":true ，不然只能显示出9999条
            searchSourceBuilder.trackTotalHits(true);
            searchRequest.source(searchSourceBuilder);
            SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);

            SearchHit[] hits = search.getHits().getHits();
            long count = search.getHits().getTotalHits().value;
            Arrays.stream(hits).forEach(hit -> {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                SysEsData esData = BeanUtil.mapToBean(sourceAsMap, SysEsData.class, true);
                list.add(esData);
            });

            map.put("current", sysEsDataVO.getCurrentPage());
            map.put("size", sysEsDataVO.getPageSize());
            map.put("total", count);
            map.put("records", list);
        } catch (Exception e) {
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
