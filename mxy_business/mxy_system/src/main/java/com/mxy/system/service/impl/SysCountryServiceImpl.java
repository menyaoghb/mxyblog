package com.mxy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mxy.common.core.constant.BaseMessage;
import com.mxy.common.core.entity.SelfUserEntity;
import com.mxy.common.core.entity.SysCountry;
import com.mxy.common.core.utils.RedisUtil;
import com.mxy.common.core.utils.ServiceResult;
import com.mxy.security.common.util.SecurityUtil;
import com.mxy.system.entity.vo.SysCountryVO;
import com.mxy.system.mapper.SysCountryMapper;
import com.mxy.system.service.SysCountryService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * <p>
 * 国家省分地市区县 服务实现类
 * </p>
 *
 * @author 孟小耀
 * @since 2022-05-10
 */
@Service
public class SysCountryServiceImpl extends ServiceImpl<SysCountryMapper, SysCountry> implements SysCountryService {

    @Autowired
    private ThreadPoolExecutor executor;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public String getList(SysCountryVO sysCountryVO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByAsc("value");
        Page<SysCountry> page = new Page<>();
        page.setCurrent(sysCountryVO.getCurrentPage());
        page.setSize(sysCountryVO.getPageSize());
        IPage<SysCountry> pageList = this.page(page, queryWrapper);
        return ServiceResult.success(pageList);
    }

    @Override
    public String add(SysCountryVO sysCountryVO) {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        SysCountry sysCountry = new SysCountry();
        BeanUtils.copyProperties(sysCountryVO, sysCountry);
        sysCountry.setCreateUser(userDetails.getUsername());
        Boolean result = sysCountry.insert();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.INSERT_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.INSERT_FAIL);
        }
    }

    @Override
    public String edit(SysCountryVO sysCountryVO) {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        SysCountry sysCountry = new SysCountry();
        BeanUtils.copyProperties(sysCountryVO, sysCountry);
        sysCountry.setUpdateUser(userDetails.getUsername());
        Boolean result = sysCountry.updateById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.UPDATE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.UPDATE_FAIL);
        }
    }

    @Override
    public String delete(SysCountryVO sysCountryVO) {
        SysCountry sysCountry = new SysCountry();
        BeanUtils.copyProperties(sysCountryVO, sysCountry);
        Boolean result = sysCountry.deleteById();
        if (result) {
            return ServiceResult.successMsg(BaseMessage.DELETE_SUCCESS);
        } else {
            return ServiceResult.successMsg(BaseMessage.DELETE_FAIL);
        }
    }

    @Override
    public List<SysCountry> selectList() {
        QueryWrapper query = new QueryWrapper<>();
        query.orderByAsc("create_time,value");
        return this.baseMapper.selectList(query);
    }

    @Override
    public String worldTree() {
        List<SysCountry> listAll = this.selectList();
        List<Map<String, Object>> mapList = new LinkedList<>();
        boolean hasKey = redisUtil.hasKey("WORLD_TREE");
        if (hasKey) {
            mapList = (List<Map<String, Object>>) redisUtil.get("WORLD_TREE");
        } else {
            List<SysCountry> list = listAll.stream().filter(k -> "0".equals(k.getParentId())).sorted(Comparator.comparing(SysCountry::getCreateTime)).sorted(Comparator.comparing(SysCountry::getValue)).collect(Collectors.toList());
            List<Map<String, Object>> finalMapList = new LinkedList<>();
            List<CompletableFuture<Map<String, Object>>> futures = new ArrayList<>();
            list.forEach(m -> {
                CompletableFuture<Map<String, Object>> mapCompletableFuture = CompletableFuture.supplyAsync(() -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("label", m.getName());
                    map.put("value", m.getValue());
                    List<SysCountry> childrenList = listAll.stream().filter(k -> k.getParentId().equals(m.getId())).sorted(Comparator.comparing(SysCountry::getValue)).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(childrenList)) {
                        map.put("children", data(listAll, childrenList));
                    }
                    return map;
                });
                futures.add(mapCompletableFuture);
            });
            futures.forEach(p -> {
                try {
                    Map<String, Object> map = p.get();
                    finalMapList.add(map);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            mapList = finalMapList;
            redisUtil.set("WORLD_TREE", mapList, 1800);
        }
        return ServiceResult.success(mapList);

    }


    public List<Map<String, Object>> data(List<SysCountry> listAll, List<SysCountry> childrenList) {
        List<Map<String, Object>> childList = new ArrayList<>();
        childrenList.forEach(m -> {
            Map<String, Object> map = new HashMap<>();
            map.put("label", m.getName());
            map.put("value", m.getValue());
            List<SysCountry> children = listAll.stream().filter(k -> k.getParentId().equals(m.getId())).sorted(Comparator.comparing(SysCountry::getValue)).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(children)) {
                map.put("children", data(listAll, children));
            }
            childList.add(map);
        });
        return childList;
    }
}
