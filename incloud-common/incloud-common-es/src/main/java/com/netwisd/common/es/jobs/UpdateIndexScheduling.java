package com.netwisd.common.es.jobs;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.es.service.ElasticSearchService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Description: 增量更新相关索引的数据
 * @Author: zouliming@netwisd.com
 * @Date: 2020/9/13 5:03 下午
 */
//@Component
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class UpdateIndexScheduling {

    private static int batchSize = 1000;

    private static final String[] dataBases = {"incloud4"};

    private static final String[] skipTables = {"nw_irm_","act_","alibaba_"};

    private static final String primaryKey = "id";

    @Autowired
    CanalConnector canalConnector;

    @Autowired
    ElasticSearchService elasticSearchService;

    @Autowired(required = false)
    IndexDataHandler indexDataHandler;

    /**
     * 一个spring的计划，用于定时去canal管道中取数据；
     */
    @Scheduled(fixedDelay = 1000)
    public void handler() {
        long batchId = -1;
        try{
            Message message = canalConnector.getWithoutAck(batchSize);
            batchId = message.getId();
            List<CanalEntry.Entry> entries = message.getEntries();
            if(batchId != -1 && entries.size() > 0){
                entries.forEach(entry -> {
                    if(entry.getEntryType() == CanalEntry.EntryType.ROWDATA){
                        //解析处理
                        publishCanalEvent(entry);
                    }
                });
            }
            canalConnector.ack(batchId);
        }catch(Exception e){
            e.printStackTrace();
            canalConnector.rollback(batchId);
        }
    }

    /**
     * 处理从canal管道中拿到的数据，并index到es中
     * @param entry
     */
    private void publishCanalEvent(CanalEntry.Entry entry) {
        String database = entry.getHeader().getSchemaName();
        String table = entry.getHeader().getTableName();
        if(isSkip(database,table)){
            return;
        }
        CanalEntry.RowChange rowChage = null;
        try {
            rowChage = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
        } catch (Exception e) {
            throw new IncloudException("RowChange转换失败, data:" + entry,
                    e);
        }
        CanalEntry.EventType eventType = rowChage.getEventType();
        List<Map> list = new ArrayList<>();
        List<Map<String,Object>> dels = new ArrayList<>();
        Map<String,Object> result = null;
        Map<String,Object> delResult = null;
        for (CanalEntry.RowData rowData : rowChage.getRowDatasList()) {
            if (eventType == CanalEntry.EventType.DELETE) {
                //printColumn(rowData.getBeforeColumnsList());
                delResult = BizIndexHandle(rowData.getBeforeColumnsList(),database,table);
            } else if (eventType == CanalEntry.EventType.INSERT) {
                //printColumn(rowData.getAfterColumnsList());
                result = BizIndexHandle(rowData.getAfterColumnsList(),database,table);
            } else if (eventType == CanalEntry.EventType.UPDATE) {
                //log.info("-------&gt; before");
                //printColumn(rowData.getBeforeColumnsList());
                //log.info("-------&gt; after");
                //printColumn(rowData.getAfterColumnsList());
                result = BizIndexHandle(rowData.getAfterColumnsList(),database,table);
            }else {
                return;
            }
            if(ObjectUtil.isNotEmpty(result) && !result.isEmpty()){
                list.add(result);
            }
            if(ObjectUtil.isNotEmpty(delResult) && !delResult.isEmpty()){
                dels.add(delResult);
            }
        }
        indexToEs(list,database,table);
        delFromEs(dels,database,table);
    }

    /**
     * 索引到es，默认索引就是数据库名_表名的方式
     * @param dataList
     * @param database
     * @param table
     */
    private void indexToEs(List<Map> dataList,String database,String table){
        elasticSearchService.insertOrUpdateMapBatch(database+"_"+table,dataList);
    }

    /**
     * 索引到es，默认索引就是数据库名_表名的方式
     * @param dataList
     * @param database
     * @param table
     */
    private void delFromEs(List<Map<String,Object>> dataList,String database,String table){
        List<Object> ids = new ArrayList<>();
        if(!dataList.isEmpty()){
            for (Map<String,Object> map : dataList){
                for (Map.Entry<String,Object> entry : map.entrySet()){
                    if(entry.getKey().equals(primaryKey)){
                        ids.add(entry.getValue());
                    }
                }
            }
        }
        elasticSearchService.deleteBatch(database+"_"+table,ids);
    }

    /**
     * 具体到业务中的数据处理
     * @param columns
     * @param database
     * @param table
     * @return
     */
    private Map<String,Object> BizIndexHandle(List<CanalEntry.Column> columns,String database,String table){
        Map<String, Object> handle = indexDataHandler.handle(columns, database, table);
        return handle;
    }

    /**
     * 打印列信息，做为调试
     * @param columns
     */
    private void printColumn(List<CanalEntry.Column> columns) {
        for (CanalEntry.Column column : columns) {
            log.info(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
        }
    }

    /**
     * 一些黑白名单的检验
     * @param database
     * @param table
     * @return
     */
    private Boolean isSkip(String database,String table){
        //白名单--只针对database
        if(!Arrays.asList(dataBases).contains(database)){
            return true;
        }
        //黑名单--表
        return Arrays.asList(skipTables).stream().anyMatch(s -> table.startsWith(s));
    }
}
