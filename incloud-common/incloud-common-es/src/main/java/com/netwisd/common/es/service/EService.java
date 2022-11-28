package com.netwisd.common.es.service;

import com.netwisd.common.es.index.Index2Table;
import com.netwisd.common.es.search.ElasticSearchMapper;

/**
 * @author zouliming@netwisd.com
 * @description 操作关于es相关的搜索、全文检索等相关功能
 * @date 2022/1/6 9:49
 */
public interface EService<T extends Index2Table,E extends ElasticSearchMapper> extends ESearch<T,E> {

}
