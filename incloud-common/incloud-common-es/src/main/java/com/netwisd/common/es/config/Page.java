package com.netwisd.common.es.config;

import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @Description: es的分页
 * @Author: zouliming@netwisd.com
 * @Date: 2020/9/18 5:30 下午
 */
@Data
public class Page<T> implements Serializable {

    /**
     * 查询数据列表
     */
    protected List<T> records = Collections.emptyList();

    /**
     * 总数
     */
    protected long total = 0;
    /**
     * 每页显示条数，默认 10
     */
    protected int size = 10;

    /**
     * 当前页
     */
    protected int current = 1;
}
