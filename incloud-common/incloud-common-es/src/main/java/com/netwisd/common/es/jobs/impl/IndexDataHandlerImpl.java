package com.netwisd.common.es.jobs.impl;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.netwisd.common.core.util.IncloudClassHandler;
import com.netwisd.common.core.util.SpringContextUtils;
import com.netwisd.common.es.index.Index2Table;
import com.netwisd.common.es.jobs.IndexDataHandler;
import com.netwisd.common.es.util.IndexUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Description: 业务数据处理器
 * @Author: zouliming@netwisd.com
 * @Date: 2020/9/14 11:21 上午
 */
@Slf4j
@Component
public class IndexDataHandlerImpl<T extends Index2Table> implements IndexDataHandler, IncloudClassHandler<T> {
    private static String prefix = Index2Table.database_prefix;
    private static final String primaryKey = "id";
    private static final String mappings = "mappings";
    private static final String properties = "properties";
    private static List<Set<String>> keyList = new ArrayList<>();
    private static List<String> keys = new ArrayList<>();

    @SneakyThrows
    public IndexDataHandlerImpl() {
        T t = null;
        Class<T> clazz = getTClass();
        if (clazz == null) {
            return;
        }
        try {
            t = SpringContextUtils.getBean(clazz);
        } catch (Exception e) {
            logger.warn("通过bean类型：" + clazz + " 找不到相应的bean,将通过反射方法来创建对象！");
            t = clazz.getDeclaredConstructor().newInstance();
        }
        prefix += t.getModulePrefix();
        keys = IndexUtil.getIndex(null, t.getMap());
        keys.forEach(index -> {
            String jsonFile = StrUtil.subAfter(index, prefix, true);
            String idxEQL = ResourceUtil.readUtf8Str("index/" + jsonFile + ".json");
            JSONObject jsonObject = (JSONObject) ((JSONObject) JSONUtil.parseObj(idxEQL).get(mappings)).get(properties);
            keyList.add(jsonObject.keySet());
        });
    }

    /**
     * 处理相应索引数据
     *
     * @param columns
     * @param database
     * @param table
     * @return
     */
    @Override
    public Map<String, Object> handle(List<CanalEntry.Column> columns, String database, String table) {
        /**
         * 这里分成两种情况去处理；
         * 1.如果es中对应的某一个索引数据，来自于工作流当中多张表的聚合数据，那么就需要通过feignClient去调相关接口联查回新数据；
         * 2.如果es中对应的某一个索引数据，等同于工作流当中对应一个张表的中数据，那么就直接处理columns；
         */
        Map<String, Object> map = unAggregationData(columns, database, table);
        return map;
    }

    /**
     * 直接根据传入数据做数据处理转换
     *
     * @param columns
     * @param database
     * @param table
     * @return
     */
    private Map<String, Object> unAggregationData(List<CanalEntry.Column> columns, String database, String table) {
        String index = database + "_" + table;
        if (keys.contains(index)) {
            log.info("命中的index为：{}", index);
            Map<String, Object> data = new HashMap<>();
            columns.forEach(column -> {
                data.put(column.getName(), column.getValue());
            });
            Map<String, Object> result = new HashMap<>();
            keyList.forEach(keys -> {
                keys.forEach(key -> {
                    initData(key, data, result);
                });
            });
            return result;
        }
        return null;
    }

    private void initData(String key, Map<String, Object> data, Map<String, Object> result) {
        Object value = getValue(key, data);
        String valueJson = ObjectUtil.isEmpty(value) ? null : JSONUtil.toJsonStr(value);
        result.put(key, StrUtil.isEmpty(valueJson) ? null : value);
    }

    private Object getValue(String key, Map<String, Object> data) {
        if (key.contains("_smt")) {
            String exSmt = key.substring(0, key.lastIndexOf("_smt"));
            return data.get(exSmt);
        }
        return data.get(key);
    }

    /**
     * 根据不同表数据变动，对相应业务表做关联查询，组装数据；
     *
     * @param columns
     * @param database
     * @param table
     * @return
     */
    private Map<String, Object> aggregationData(List<CanalEntry.Column> columns, String database, String table) {
        List<Long> ids = new ArrayList<>();
        columns.forEach(column -> {
            String id = column.getIsKey()
                    && primaryKey.equals(column.getName()) ? column.getValue() : null;
            ids.add(Long.valueOf(id));
        });
        //....调用feignClient相关方法做数据处理；
        return null;
    }
}
