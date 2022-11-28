package com.netwisd.common.es.service;

import com.netwisd.common.es.config.Page;
import com.netwisd.common.es.entity.ElasticEntity;
import com.netwisd.common.es.vo.ElasticVo;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Description: current package com.netwisd.log.config.service.impl
 * @Author: zouliming@netwisd.com
 * @Date: 2020/2/17 5:56 下午
 */
public interface ElasticSearchService {
    void createIndex(String idxName,String idxSQL);

    /**
     * 判断某个index是否存在
     * @param idxName 索引名称
     * @return
     * @throws Exception
     */
    //boolean indexExist(String idxName) throws Exception;

    /**
     * 判断某个index是否存在
     * @param idxName 索引名称
     * @return
     * @throws Exception
     */
    boolean isExistsIndex(String idxName) throws Exception;

    /**
     * 设置分片
     * @param request
     */
    void buildSetting(CreateIndexRequest request);

    /**
     * 在相应索引中插入或更新对象
     * @param idxName
     * @param entity
     */
    void insertOrUpdateOne(String idxName, ElasticEntity entity);

    /**
     * 在相应索引中插入或更新对象
     * @param idxName
     * @param source
     */
    void insertOrUpdateOne(String idxName, Map source);

    /**
     * 删除一个索引中的对象
     * @param idxName
     * @param entity
     */
    void deleteOne(String idxName, ElasticEntity entity);

    /**
     * 批量插入
     * @param idxName
     * @param list
     */
    void insertOrUpdateBatch(String idxName, List<ElasticEntity> list);

    /**
     * 批量插入
     * @param idxName
     * @param listMap
     */
    void insertOrUpdateMapBatch(String idxName, List<Map> listMap);

    /**
     * 批量插入
     * @param idxName
     */
    <T> void insertOrUpdateAnyBatch(String idxName, List<T> list);

    /**
     * 批量插入
     * @param idxName
     * @param list
     */
    void insertOrUpdateBatchTrueObj(String idxName, List<ElasticEntity> list);

    /**
     * 批量插入
     * @param idxName
     * @param listMap
     */
    void insertOrUpdateMapBatchTrueObj(String idxName, List<Map> listMap);

    /**
     * 批量删除
     * @param idxName
     * @param idList
     * @param <T>
     */
    <T> void deleteBatch(String idxName, Collection<T> idList);

    /**
     * 自定义查询
     * @param idxName
     * @param builder
     * @param c
     * @param <T>
     * @return
     */
    <T> List<T> search(String idxName, SearchSourceBuilder builder, Class<T> c);

    /**
     * 自定义查询
     * @param idxName
     * @param builder
     * @return
     */
     List<ElasticVo> search(String indexPrefix, String idxName, SearchSourceBuilder builder);

    /**
     * 自定义查询
     * @param idxName
     * @param builder
     * @return
     */
    Page<ElasticVo> searchPage(String indexPrefix, String idxName, SearchSourceBuilder builder,Page page);

    /**
     * 删除索引
     * @param idxName
     */
    void deleteIndex(String idxName);


    /**
     * 自定义删除
     * @param idxName
     * @param builder
     */
    void deleteByQuery(String idxName, QueryBuilder builder);
}
