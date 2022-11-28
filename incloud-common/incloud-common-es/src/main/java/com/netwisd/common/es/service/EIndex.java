package com.netwisd.common.es.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Entity;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.netwisd.common.core.util.IncloudClassHandler;
import com.netwisd.common.core.util.Result;
import com.netwisd.common.core.util.SpringContextUtils;
import com.netwisd.common.db.config.JdbcDataSourceProvider;
import com.netwisd.common.es.index.Index2Table;
import com.netwisd.common.es.index.IndexDto;
import com.netwisd.common.es.util.IndexUtil;
import lombok.SneakyThrows;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zouliming@netwisd.com
 * @description
 * @date 2022/1/7 17:22
 */
public interface EIndex<T extends Index2Table> extends IncloudClassHandler<T> {
    Log logger= LogFactory.getLog(EIndex.class);

    ElasticSearchService elasticSearchService = SpringContextUtils.getBean(ElasticSearchService.class);

    DynamicDataSourceProvider dynamicDataSourceProvider = SpringContextUtils.getBean(DynamicDataSourceProvider.class);

    /**
     * 根据条件进行索引
     * @param indexDto
     * @return
     */
    @SneakyThrows
    default Result index(IndexDto indexDto) {
        T t = null;
        Class<T> clazz = getTClass();
        try {
            t = SpringContextUtils.getBean(clazz);
        }catch (Exception e){
            logger.warn("通过bean类型："+clazz+" 找不到相应的bean,将通过反射方法来创建对象！");
            t = clazz.getDeclaredConstructor().newInstance();
        }
        if(CollectionUtil.isEmpty(indexDto.getIndex())) indexDto.setIndex(IndexUtil.getIndex(null, t.getMap()));
        indexData(indexDto,t);
        return Result.success();
    }

    /**
     * 索引相应数据
     * @param indexDto
     * @param index2Table
     */
    default void indexData(IndexDto indexDto, Index2Table index2Table) {
        if(indexDto.getIsRebuild()){
            rebuild(indexDto.getIndex(), index2Table);
        }
        JdbcDataSourceProvider jdbcDataSourceProvider = (JdbcDataSourceProvider) dynamicDataSourceProvider;
        indexDto.getIndex().forEach(s -> {
            String index = StrUtil.subAfter(s, Index2Table.database_prefix, true);
            List<Entity> table = jdbcDataSourceProvider.getTable(index);
            List list = new ArrayList();
            table.forEach(entity -> {
                Class clazz = index2Table.get(entity.getTableName());
                Object obj = entity.toBean(clazz);
                JSONObject jsonObject = JSONUtil.parseObj(obj).setDateFormat("yyyy-MM-dd HH:mm:ss");
                String json = StrUtil.toUnderlineCase(JSONUtil.toJsonStr(jsonObject));
                Map map = JSONUtil.toBean(json, Map.class);
                list.add(map);
            });
            indexToEs(list, Index2Table.database_prefix,index);
        });
        logger.info("index完成！！！");
    }

    /**
     * 重建一个索引
     * @param indexList
     * @param index2Table
     */
    default void rebuild(List<String> indexList, Index2Table index2Table) {
        indexList.forEach(index ->{
            String jsonFile = StrUtil.subAfter(index, Index2Table.database_prefix + index2Table.getModulePrefix(),true);
            String idxSQL = ResourceUtil.readUtf8Str("index/"+jsonFile+".json");
            try {
                if(elasticSearchService.isExistsIndex(index)){
                    logger.info("删除索引："+index);
                    elasticSearchService.deleteIndex(index);
                }
                logger.info("初始化index的idxSQL为："+idxSQL);
                elasticSearchService.createIndex(index,idxSQL);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 索引到es，默认索引就是数据库名_表名的方式
     * @param dataList
     * @param database
     * @param table
     */
    default void indexToEs(List<Map> dataList,String database,String table){
        elasticSearchService.insertOrUpdateMapBatch(database+table,dataList);
    }
}
