package com.netwisd.base.log.service.impl;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.util.IdGenerator;
import com.netwisd.base.log.dto.ApplicationLogDto;
import com.netwisd.base.log.entity.ApplicationLog;
import com.netwisd.base.log.mapper.ApplicationLogMapper;
import com.netwisd.base.log.service.ApplicationLogService;
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
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ApplicationLogServiceImpl extends ServiceImpl<ApplicationLogMapper, ApplicationLog> implements ApplicationLogService {

    @Autowired
    private ElasticSearchService elasticSearchService;

    private static final String primaryKey = "id";

    private static final String INDEX_NAME = "incloud_application_log";

    private static final String INDEX_SQL_KEY = "indexs/application_log.json";

    private static final Integer DEFAULT_SEARCH_SIZE = 50;

    @Override
    public void insertApplicationLog(ApplicationLog applicationLog) {
        this.baseMapper.createSuperTable(applicationLog.getAppName());
        this.baseMapper.insertOne(applicationLog);
    }

    @Override
    public void insertApplicationLog(String jsonLog) {
        if (StrUtil.isEmpty(jsonLog)) {
            return;
        }
        Map<String, Object> jsonMap = JSON.parseObject(jsonLog, Map.class);
        jsonMap.put(primaryKey, IdGenerator.getIdGenerator());
        elasticSearchService.insertOrUpdateOne(INDEX_NAME, jsonMap);

    }

    @Override
    public List<ElasticVo> searchApplicationLog(ApplicationLogDto applicationLogDto) {
        //查询条件
        FunctionScoreQueryBuilder functionScoreQueryBuilder = buildFunctionScoreQueryBuilder(applicationLogDto);
        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .field("body")
                .field("className")
                .field("threadName");
        //SearchSourceBuilder
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //构建相关参数
        searchSourceBuilder.query(functionScoreQueryBuilder)
                .highlighter(highlightBuilder)
                //.sort("_score", SortOrder.DESC)
                .sort("createTime", SortOrder.DESC)
                .size(ObjectUtil.isNull(applicationLogDto.getSize()) ? DEFAULT_SEARCH_SIZE : applicationLogDto.getSize());
        log.info("全文检索json语句：{}", searchSourceBuilder);
        List<ElasticVo> result = elasticSearchService.search(null, INDEX_NAME, searchSourceBuilder);
        return result;
    }

    //构建查询条件
    private FunctionScoreQueryBuilder buildFunctionScoreQueryBuilder(ApplicationLogDto applicationLogSearchDto) {
        //关键字查询
        String keyword = applicationLogSearchDto.getKeyword();
        BoolQueryBuilder matchnBuilder = QueryBuilders.boolQuery();
        if (StrUtil.isNotEmpty(keyword)) {
            matchnBuilder.should(QueryBuilders.wildcardQuery("body", "*" + keyword + "*"))
                    .should(QueryBuilders.wildcardQuery("className", "*" + keyword + "*"))
                    .should(QueryBuilders.wildcardQuery("threadName", "*" + keyword + "*"));
        }

        //bool精确条件
        BoolQueryBuilder termBuilder = QueryBuilders.boolQuery();
        if (StrUtil.isNotEmpty(applicationLogSearchDto.getAppName())) {
            termBuilder.must(QueryBuilders.termQuery("appName", applicationLogSearchDto.getAppName()));
        }
        if (StrUtil.isNotEmpty(applicationLogSearchDto.getHost())) {
            termBuilder.must(QueryBuilders.termQuery("host", applicationLogSearchDto.getHost()));
        }
        if (StrUtil.isNotEmpty(applicationLogSearchDto.getLevel())) {
            termBuilder.must(QueryBuilders.termQuery("level", applicationLogSearchDto.getLevel()));
        }
        //范围查找
        BoolQueryBuilder rangeBuilder = QueryBuilders.boolQuery();
        if (ObjectUtil.isNotEmpty(applicationLogSearchDto.getStartCreateTime()) && ObjectUtil.isNotEmpty(applicationLogSearchDto.getEndCreateTime())) {
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("createTime");
            rangeQueryBuilder.gte(localDateTimeFormat(applicationLogSearchDto.getStartCreateTime()) + " 000");
            rangeQueryBuilder.lte(localDateTimeFormat(applicationLogSearchDto.getEndCreateTime()) + " 999");
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

    @PostConstruct
    public void init() {
        String indexSql = ResourceUtil.readUtf8Str(INDEX_SQL_KEY);
        elasticSearchService.createIndex(INDEX_NAME, indexSql);
    }
}
