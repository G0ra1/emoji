package com.netwisd.common.es.jobs;

import com.alibaba.otter.canal.protocol.CanalEntry;

import java.util.List;
import java.util.Map;

/**
 * @Description: 业务相关索引数据处理器
 * @Author: zouliming@netwisd.com
 * @Date: 2020/9/14 11:21 上午
 */
public interface IndexDataHandler {
    Map<String,Object> handle(List<CanalEntry.Column> columns,String database,String table);
}
