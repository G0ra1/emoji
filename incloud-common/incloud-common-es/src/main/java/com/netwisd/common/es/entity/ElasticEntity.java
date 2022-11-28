package com.netwisd.common.es.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @Description: es存储的实体类映射对象
 * @Author: zouliming@netwisd.com
 * @Date: 2020/4/15 1:22 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElasticEntity<T> implements Serializable {

    /**
     * 主键标识，用于ES持久化的唯一标识
     */
    private String id;

    /**
     * JSON对象，实际存储数据
     */
    private Map data;
}
