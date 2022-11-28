package com.netwisd.common.es.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.JacksonUtil;
import com.netwisd.common.es.config.Page;
import com.netwisd.common.es.entity.ElasticEntity;
import com.netwisd.common.es.service.ElasticSearchService;
import com.netwisd.common.es.vo.ElasticVo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @Description: es基本操作service；
 * current package com.netwisd.log.config.service.impl
 * @Author: zouliming
 * @Date: 2020/2/13 11:25 上午
 */
@Slf4j
@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {
    @Autowired
    RestHighLevelClient esClient;

    private static final String primaryKey = "id";

    private static final String primaryGet = "getId";

    private static final String mappings = "mappings";

    private static final String settings = "settings";

    /**
     * 创建索引
     *
     * @param idxName 索引名称
     * @param idxSQL  索引描述
     */
    @Override
    public void createIndex(String idxName, String idxSQL) {
        try {
            if (this.isExistsIndex(idxName)) {
                log.error(" idxName={} 已经存在,idxSql={}", idxName, idxSQL);
                return;
            }
            CreateIndexRequest request = new CreateIndexRequest(idxName);

            String setting = null;
            String mapping = null;
            try {
                setting = JSONUtil.toJsonStr(JSONUtil.parseObj(idxSQL).get(settings));
            } catch (Exception e) {
                log.info("setting为空！");
            }
            try {
                mapping = JSONUtil.toJsonStr(JSONUtil.parseObj(idxSQL).get(mappings));
            } catch (Exception e) {
                log.error("mapping为空！");
                throw new IncloudException("mapping不能为空");
            }
            if (StrUtil.isNotEmpty(setting)) {
                //手工指定Setting
                request.settings(setting, XContentType.JSON);
            } else {
                //默认settings设置
                buildSetting(request);
            }

            request.mapping(mapping, XContentType.JSON);

            CreateIndexResponse res = esClient.indices().create(request, RequestOptions.DEFAULT);
            if (!res.isAcknowledged()) {
                throw new IncloudException("初始化失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * 判断某个index是否存在
     * @param idxName 索引名称
     * @return
     * @throws Exception
     */
    /*@Override
    @SneakyThrows
    public boolean indexExist(String idxName){
        GetIndexRequest request = new GetIndexRequest(idxName);
        request.local(false);
        request.humanReadable(true);
        request.includeDefaults(false);
        request.indicesOptions(IndicesOptions.lenientExpandOpen());
        return esClient.indices().exists(request, RequestOptions.DEFAULT);
    }*/

    /**
     * 判断某个index是否存在
     *
     * @param idxName 索引名称
     * @return
     * @throws Exception
     */
    @Override
    @SneakyThrows
    public boolean isExistsIndex(String idxName) {
        return esClient.indices().exists(new GetIndexRequest(idxName), RequestOptions.DEFAULT);
    }

    /**
     * 设置分片
     * 解释一下这个默认设置，正常情况下我们项目会做三个es集群的节点；然后主分片设置为3个；replicas设置为2份副本；
     * 三个节点的分片情况可能为：node1:p0,r1,r2 ; node2:p1,r0,r2 ; node3: p2,r0,r1;
     * 这种情况即可以达到负载均衡，又能达到数据安全；
     *
     * @param request
     */
    @Override
    public void buildSetting(CreateIndexRequest request) {
        request.settings(Settings.builder().put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2));
    }

    /**
     * 在相应索引中插入或更新对象
     *
     * @param idxName
     * @param entity
     */
    @Override
    public void insertOrUpdateOne(String idxName, ElasticEntity entity) {
        IndexRequest request = new IndexRequest(idxName);
        log.info("Data : id={},entity={}", entity.getId(), JSONUtil.parseObj(entity.getData()).toStringPretty());
        request.id(entity.getId());
        request.source(entity.getData(), XContentType.JSON);
        try {
            esClient.index(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new IncloudException(e);
        }
    }

    /**
     * 在相应索引中插入或更新对象
     *
     * @param idxName
     * @param source
     */
    @Override
    public void insertOrUpdateOne(String idxName, Map source) {
        IndexRequest request = new IndexRequest(idxName);
        log.info("Data : id={},entity={}", source.get(primaryKey), JSONUtil.parseObj(source).toStringPretty());
        request.id(String.valueOf(source.get(primaryKey)));
        request.source(source, XContentType.JSON);
        try {
            esClient.index(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new IncloudException(e);
        }
    }

    /**
     * 删除一个索引中的对象
     *
     * @param idxName
     * @param entity
     */
    @Override
    public void deleteOne(String idxName, ElasticEntity entity) {
        DeleteRequest request = new DeleteRequest(idxName);
        request.id(entity.getId());
        try {
            esClient.delete(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new IncloudException(e);
        }
    }

    /**
     * 批量插入
     *
     * @param idxName
     * @param list
     */
    @Override
    public void insertOrUpdateBatch(String idxName, List<ElasticEntity> list) {
        BulkRequest request = new BulkRequest();
        list.forEach(item -> request.add(new IndexRequest(idxName).id(item.getId())
                .source(JSONUtil.parseObj(item.getData()).toStringPretty(), XContentType.JSON)));
        try {
            esClient.bulk(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new IncloudException(e);
        }
    }

    /**
     * 批量插入
     *
     * @param idxName
     * @param listMap
     */
    @Override
    public void insertOrUpdateMapBatch(String idxName, List<Map> listMap) {
        if (ObjectUtil.isEmpty(listMap) || listMap.isEmpty()) {
            return;
        }
        BulkRequest request = new BulkRequest();
        listMap.forEach(item -> {
            log.info("insertOrUpdateMapBatch======{}", JSONUtil.toJsonStr(item));
            request.add(new IndexRequest(idxName).id(String.valueOf(item.get(primaryKey)))
                    .source(item, XContentType.JSON));
        });
        /*listMap.forEach(item -> request.add(new IndexRequest(idxName).id(String.valueOf(item.get(primaryKey)))
                .source(item, XContentType.JSON)));*/
        try {
            esClient.bulk(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new IncloudException(e);
        }
    }

    @SneakyThrows
    @Override
    public <T> void insertOrUpdateAnyBatch(String idxName, List<T> list) {
        BulkRequest request = new BulkRequest();
        for (T t : list) {
            Class<?> targetClass = t.getClass();
            //Field fieldId = ReflectionUtils.findField(targetClass, primaryKey);
            String id = null;
            try {
                Method method = targetClass.getMethod(primaryGet, null);
                Object o = ReflectionUtils.invokeMethod(method, t);
                id = String.valueOf(o);
            } catch (Exception e) {
                log.info("es转换实体没有相应ID，使用es默认ID策略！");
            }
            String json = JacksonUtil.toJSONString(t);
            IndexRequest indexRequest = new IndexRequest(idxName).source(json, XContentType.JSON);

            if (StrUtil.isNotEmpty(id)) {
                indexRequest.id(id);
            }
            request.add(indexRequest);
        }
        try {
            esClient.bulk(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new IncloudException(e);
        }
    }

    /**
     * 批量插入
     *
     * @param idxName
     * @param list
     */
    @Override
    public void insertOrUpdateBatchTrueObj(String idxName, List<ElasticEntity> list) {
        BulkRequest request = new BulkRequest();
        list.forEach(item -> request.add(new IndexRequest(idxName).id(item.getId())
                .source(item.getData(), XContentType.JSON)));
        try {
            esClient.bulk(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new IncloudException(e);
        }
    }

    /**
     * 批量插入
     *
     * @param idxName
     * @param listMap
     */
    @Override
    public void insertOrUpdateMapBatchTrueObj(String idxName, List<Map> listMap) {
        BulkRequest request = new BulkRequest();
        listMap.forEach(item -> request.add(new IndexRequest(idxName).id(String.valueOf(item.get(primaryKey)))
                .source(item, XContentType.JSON)));
        try {
            esClient.bulk(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量删除
     *
     * @param idxName
     * @param idList
     * @param <T>
     */
    @Override
    public <T> void deleteBatch(String idxName, Collection<T> idList) {
        BulkRequest request = new BulkRequest();
        if(ObjectUtil.isNotEmpty(idList) && !idList.isEmpty()){
            idList.forEach(item -> request.add(new DeleteRequest(idxName, item.toString())));
            try {
                esClient.bulk(request, RequestOptions.DEFAULT);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 自定义查询
     *
     * @param idxName
     * @param builder
     * @param c
     * @param <T>
     * @return
     */
    @Override
    public <T> List<T> search(String idxName, SearchSourceBuilder builder, Class<T> c) {
        SearchRequest request = new SearchRequest(idxName);
        request.source(builder);
        try {
            SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
            SearchHit[] hits = response.getHits().getHits();
            long totalHits = response.getHits().getTotalHits().value;

            List<T> res = new ArrayList<>(hits.length);
            for (SearchHit hit : hits) {
                String camelCase = StrUtil.toCamelCase(hit.getSourceAsString());
                log.info("转驼峰为：{}", camelCase);
                if (c == String.class) {
                    res.add((T) camelCase);
                    continue;
                }
                //这里用fastJson代替hutool JSONUtil,因为一般情况下c我们会用map，hutool会把map中的null值，处理成JSONNull;返回给前端序列化时使用的jackson，是识别不了JSONNull的
                res.add(JSON.parseObject(camelCase, c));
                //res.add(JSONUtil.toBean(hit.getSourceAsString(),c));
            }
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ElasticVo> search(String indexPrefix, String idxName, SearchSourceBuilder builder) {
        SearchRequest request = new SearchRequest(idxName);
        request.source(builder);
        try {
            SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
            SearchHit[] hits = response.getHits().getHits();
            List<ElasticVo> res = new ArrayList<>(hits.length);
            for (SearchHit hit : hits) {
                ElasticVo elasticVo = new ElasticVo();
                elasticVo.setId(hit.getId());
                String index = hit.getIndex();
                if (StrUtil.isNotEmpty(indexPrefix)) {
                    index = StrUtil.toCamelCase(StrUtil.subAfter(index, indexPrefix, true));
                }
                elasticVo.setIndex(index);
                //String camelCase = StrUtil.toCamelCase(hit.getSourceAsString());
                //log.info("转驼峰为：{}",camelCase);
                //这里用fastJson代替hutool JSONUtil,因为hutool会把map中的null值，处理成JSONNull;返回给前端序列化时使用的jackson，是识别不了JSONNull的
                //elasticVo.setData(JSON.parseObject(camelCase,Map.class));
                elasticVo.setData(hit.getSourceAsMap());
                res.add(elasticVo);
            }
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<ElasticVo> searchPage(String indexPrefix, String idxName, SearchSourceBuilder builder, Page page) {
        SearchRequest request = new SearchRequest(idxName);
        request.source(builder);
        try {
            SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
            SearchHit[] hits = response.getHits().getHits();
            long total = response.getHits().getTotalHits().value;
            List<ElasticVo> res = new ArrayList<>(hits.length);
            for (SearchHit hit : hits) {
                ElasticVo elasticVo = new ElasticVo();
                Map<String, List<String>> highlightFieldsMap = new HashMap<>();
                elasticVo.setId(hit.getId());
                String index = hit.getIndex();
                for (Map.Entry<String, HighlightField> entry : hit.getHighlightFields().entrySet()) {
                    List highlightFields = new ArrayList();
                    String key = StrUtil.toCamelCase(entry.getKey());
                    HighlightField highlightField = entry.getValue();
                    Text[] fragments = highlightField.getFragments();
                    for (Text text : fragments) {
                        log.info("高亮显示为：{}", text.string());
                        highlightFields.add(text.toString());
                    }
                    highlightFieldsMap.put(key, highlightFields);
                }
                elasticVo.setHighlightFields(highlightFieldsMap);
                if (StrUtil.isNotEmpty(indexPrefix)) {
                    index = StrUtil.toCamelCase(StrUtil.subAfter(index, indexPrefix, true));
                }
                elasticVo.setIndex(index);
                //String camelCase = StrUtil.toCamelCase(hit.getSourceAsString());
                //log.info("转驼峰为：{}",camelCase);
                //这里用fastJson代替hutool JSONUtil,因为hutool会把map中的null值，处理成JSONNull;返回给前端序列化时使用的jackson，是识别不了JSONNull的
                //elasticVo.setData(JSON.parseObject(hit.getSourceAsString(),Map.class));
                elasticVo.setData(hit.getSourceAsMap());
                res.add(elasticVo);
            }
            page.setTotal(total);
            page.setRecords(res);
            return page;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除索引
     *
     * @param idxName
     */
    @Override
    public void deleteIndex(String idxName) {
        try {
            /*if (this.isExistsIndex(idxName)) {
                log.error(" idxName={} 已经存在", idxName);
                return;
            }*/
            esClient.indices().delete(new DeleteIndexRequest(idxName), RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 自定义删除
     *
     * @param idxName
     * @param builder
     */
    @Override
    public void deleteByQuery(String idxName, QueryBuilder builder) {
        DeleteByQueryRequest request = new DeleteByQueryRequest(idxName);
        request.setQuery(builder);
        //设置批量操作数量,最大为10000
        request.setBatchSize(10000);
        request.setConflicts("proceed");
        try {
            esClient.deleteByQuery(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
