package com.netwisd.base.log.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.netwisd.base.common.dict.vo.DictItemVo;
import com.netwisd.base.common.util.IdGenerator;
import com.netwisd.base.log.dto.SystemLogDto;
import com.netwisd.base.log.fegin.DictClient;
import com.netwisd.base.log.service.SystemLogService;
import com.netwisd.common.es.config.Page;
import com.netwisd.common.es.service.ElasticSearchService;
import com.netwisd.common.es.vo.ElasticVo;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SystemLogServiceImpl implements SystemLogService {

    @Autowired
    private ElasticSearchService elasticSearchService;

    @Autowired
    private DictClient dictClient;

    private static final String primaryKey = "id";

    private static final String INDEX_NAME = "incloud_system_log";

    private static final String INDEX_SQL_KEY = "indexs/system_log.json";

    private static final String SYSTEM_LOG_OPERATE_TYPE = "SYSTEM_LOG_OPERATE_TYPE";

    private static final String SYSTEM_LOG_TYPE = "SYSTEM_LOG_TYPE";

    private static final Integer DEFAULT_SEARCH_SIZE = 10000;

    @Async
    @Override
    public void insertSystemLog(String jsonLog) {
        if (StrUtil.isEmpty(jsonLog)) {
            return;
        }
        Map<String, Object> jsonMap = JSON.parseObject(jsonLog, Map.class);
        jsonMap.put("contentStd", jsonMap.get("content"));
        jsonMap.put("userAgentStd", jsonMap.get("userAgent"));
        jsonMap.put("paramsStd", jsonMap.get("params"));
        jsonMap.put("createUserNameStd", jsonMap.get("createUserName"));
        jsonMap.put("methodStd", jsonMap.get("method"));
        jsonMap.put("requestUriStd", jsonMap.get("requestUri"));
        jsonMap.put(primaryKey, IdGenerator.getIdGenerator());
        //驼峰转换为下划线
        //Map sourceMap = new HashMap();
        //jsonMap.forEach((k, v) -> sourceMap.put(StrUtil.toUnderlineCase(k), v));
        elasticSearchService.insertOrUpdateOne(INDEX_NAME, jsonMap);
    }

    @Override
    public Page<ElasticVo> searchSystemLogPage(SystemLogDto systemLogDto) {
        //分页信息
        Page page = systemLogDto.getPage();
        //查询条件
        FunctionScoreQueryBuilder functionScoreQueryBuilder = buildFunctionScoreQueryBuilder(systemLogDto);
        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .field("content")
                .field("contentStd")
                .field("createUserName")
                .field("createUserNameStd")
                .field("method")
                .field("methodStd")
                .field("params")
                .field("paramsStd")
                .field("userAgent")
                .field("userAgentStd")
                .field("requestUri")
                .field("requestUriStd");
        //SearchSourceBuilder
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(functionScoreQueryBuilder)
                .highlighter(highlightBuilder)
                .sort("_score", SortOrder.DESC)
                .sort("createTime", SortOrder.DESC)
                .from(getFrom(page))
                .size(page.getSize());

        log.info("全文检索json语句：{}", searchSourceBuilder);
        Page<ElasticVo> result = elasticSearchService.searchPage(null, INDEX_NAME, searchSourceBuilder, page);
        handleSystemLog(result.getRecords());
        return result;
    }

    @Override
    public List<ElasticVo> searchSystemLog(SystemLogDto systemLogDto) {
        //查询条件
        FunctionScoreQueryBuilder functionScoreQueryBuilder = buildFunctionScoreQueryBuilder(systemLogDto);
        //SearchSourceBuilder
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //构建相关参数
        searchSourceBuilder.query(functionScoreQueryBuilder)
                .sort("_score", SortOrder.DESC)
                .sort("createTime", SortOrder.DESC)
                .size(DEFAULT_SEARCH_SIZE);
        log.info("全文检索json语句：{}", searchSourceBuilder);
        List<ElasticVo> result = elasticSearchService.search(null, INDEX_NAME, searchSourceBuilder);
        handleSystemLog(result);
        return result;
    }

    //构建查询条件
    private FunctionScoreQueryBuilder buildFunctionScoreQueryBuilder(SystemLogDto systemLogDto) {
        String keyWords = systemLogDto.getKeyWords();
        //第一个bool条件中的match关键字
        BoolQueryBuilder matchnBuilder = QueryBuilders.boolQuery();
        if (StrUtil.isNotEmpty(keyWords)) {
            matchnBuilder.should(QueryBuilders.matchQuery("content", keyWords).boost(10))
                    .should(QueryBuilders.matchQuery("contentStd", keyWords).boost(3))
                    .should(QueryBuilders.matchQuery("createUserName", keyWords).boost(5))
                    .should(QueryBuilders.matchQuery("createUserNameStd", keyWords).boost(1))
                    .should(QueryBuilders.matchQuery("method", keyWords).boost(5))
                    .should(QueryBuilders.matchQuery("methodStd", keyWords).boost(1))
                    .should(QueryBuilders.matchQuery("params", keyWords).boost(6))
                    .should(QueryBuilders.matchQuery("paramsStd", keyWords).boost(1))
                    .should(QueryBuilders.matchQuery("userAgent", keyWords).boost(5))
                    .should(QueryBuilders.matchQuery("userAgentStd", keyWords).boost(1))
                    .should(QueryBuilders.matchQuery("requestUri", keyWords).boost(6))
                    .should(QueryBuilders.matchQuery("requestUriStd", keyWords).boost(2));
        }

        //第二个bool精确条件
        BoolQueryBuilder termBuilder = QueryBuilders.boolQuery();
        if (StrUtil.isNotEmpty(systemLogDto.getAppName())) {
            termBuilder.must(QueryBuilders.termQuery("appName", systemLogDto.getAppName()));
        }
        if (StrUtil.isNotEmpty(systemLogDto.getLogType())) {
            termBuilder.must(QueryBuilders.termQuery("logType", systemLogDto.getLogType()));
        }
        if (StrUtil.isNotEmpty(systemLogDto.getOperateType())) {
            termBuilder.must(QueryBuilders.termQuery("operateType", systemLogDto.getOperateType()));
        }
        if (StrUtil.isNotEmpty(systemLogDto.getRequestType())) {
            termBuilder.must(QueryBuilders.termQuery("requestType", systemLogDto.getRequestType()));
        }

        //第三个范围查找
        BoolQueryBuilder rangeBuilder = QueryBuilders.boolQuery();
        if (ObjectUtil.isNotEmpty(systemLogDto.getStartCreateTime()) && ObjectUtil.isNotEmpty(systemLogDto.getEndCreateTime())) {
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("createTime");
            rangeQueryBuilder.gte(localDateTimeFormat(systemLogDto.getStartCreateTime()));
            rangeQueryBuilder.lte(localDateTimeFormat(systemLogDto.getEndCreateTime()));
            rangeBuilder.must(rangeQueryBuilder);
        }
        if (ObjectUtil.isNotEmpty(systemLogDto.getStartExecTime()) && ObjectUtil.isNotEmpty(systemLogDto.getEndExecTime())) {
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("execTime");
            rangeQueryBuilder.gte(systemLogDto.getStartExecTime());
            rangeQueryBuilder.lte(systemLogDto.getEndExecTime());
            rangeBuilder.must(rangeQueryBuilder);
        }

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(matchnBuilder)
                .must(termBuilder)
                .must(rangeBuilder);

        //构建functionScore
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(boolQueryBuilder);
        functionScoreQueryBuilder.scoreMode(FunctionScoreQuery.ScoreMode.SUM);
        functionScoreQueryBuilder.boostMode(CombineFunction.SUM);

        return functionScoreQueryBuilder;
    }

    //构建结果集
    private void handleSystemLog(List<ElasticVo> records) {
        if (CollUtil.isEmpty(records)) {
            return;
        }
        Map<String, DictItemVo> logOperateTypeMap = dictClient.itemList(SYSTEM_LOG_OPERATE_TYPE).stream().collect(Collectors.toMap(DictItemVo::getDictItemCode, Function.identity(), (key1, key2) -> key2));
        Map<String, DictItemVo> logTypeMap = dictClient.itemList(SYSTEM_LOG_TYPE).stream().collect(Collectors.toMap(DictItemVo::getDictItemCode, Function.identity(), (key1, key2) -> key2));
        for (ElasticVo record : records) {
            Map map = record.getData();
            map.put("logTypeVo", ObjectUtil.isNull(logTypeMap.get(map.get("logType"))) ? "" : logTypeMap.get(map.get("logType")).getDictItemName());
            map.put("operateTypeVo", ObjectUtil.isNull(logOperateTypeMap.get(map.get("operateType"))) ? "" : logOperateTypeMap.get(map.get("operateType")).getDictItemName());
        }
    }

    @PostConstruct
    public void init() {
        String indexSql = ResourceUtil.readUtf8Str(INDEX_SQL_KEY);
        elasticSearchService.createIndex(INDEX_NAME, indexSql);
    }

}
