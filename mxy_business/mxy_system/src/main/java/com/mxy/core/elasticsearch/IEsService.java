package com.mxy.core.elasticsearch;

/**
 * @author mengyao
 * @date 2022-05-07 20:57
 */
public interface IEsService {

    /**
     * 创建索引库
     * @param index 索引名称
     */
    void createIndexRequest(String index);

    /**
     * 删除索引库
     * @param index 索引名称
     */
    void deleteIndexRequest(String index);


    /**
     * 更新索引文档
     * @param index 索引名称
     * @param id 文档id
     * @param object 更新对象
     */
    void updateRequest(String index, String id, Object object);


    /**
     * 新增索引文档
     * @param index 索引名称
     * @param id 文档id
     * @param object 新增对象
     */
    void insertRequest(String index, String id, Object object);


    /**
     * 删除索引文档
     * @param index 索引名称
     * @param id 文档id
     */
    void deleteRequest(String index, String id);
}
