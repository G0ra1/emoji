package com.netwisd.common.es.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Description: esvo
 * @Author: zouliming@netwisd.com
 * @Date: 2020/4/15 1:22 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElasticVo<T> implements Serializable {

    /**
     * 主键标识，用于ES持久化的唯一标识
     */
    private String id;

    /**
     * 主键标识，用于ES持久化的唯一标识
     */
    private String index;

    /**
     * JSON对象，实际存储数据
     */
    private Map data;

    /**
     * 高亮
     */
    private Map<String, List<String>> highlightFields;
}
