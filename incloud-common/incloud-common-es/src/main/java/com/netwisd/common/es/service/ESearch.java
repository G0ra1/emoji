package com.netwisd.common.es.service;

import com.netwisd.common.es.config.Page;
import com.netwisd.common.es.index.Index2Table;
import com.netwisd.common.es.search.ElasticSearchMapper;
import com.netwisd.common.es.vo.ElasticVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zouliming@netwisd.com
 * @description
 * @date 2022/1/7 17:21
 */
public interface ESearch<T extends Index2Table,E extends ElasticSearchMapper> extends EIndex<T>{
    Log logger= LogFactory.getLog(ESearch.class);

    default int getFrom(Page page) {
        int current = page.getCurrent();
        int size = page.getSize();
        int from = (current-1) * size;
        return from;
    }

    default Page<ElasticVo> eSearch(String indexPrefix, ElasticSearchMapper elasticSearchMapper) {
        String indexName = elasticSearchMapper.getIndex();
        //分页
        Page page = elasticSearchMapper.getPage();
        //SearchSourceBuilder
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //构建相关参数
        searchSourceBuilder.query(elasticSearchMapper.functionScoreQueryBuilder())
                .highlighter(elasticSearchMapper.highlightBuilder())
                .sort("_score", SortOrder.DESC)
                .from(getFrom(page))
                .size(page.getSize());
        logger.info("全文检索json语句："+searchSourceBuilder);
        Page<ElasticVo> result = elasticSearchService.searchPage(indexPrefix,indexName, searchSourceBuilder,page);
        return result;
    }
}
