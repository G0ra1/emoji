package com.netwisd.common.msg.rocket.handle;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.netwisd.common.core.util.SpringUtils;
import com.netwisd.common.msg.rocket.data.BinLogData;
import com.netwisd.common.msg.rocket.service.BinLogDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import java.util.Arrays;

@Slf4j
public class HandleBinLogData {

    private static final String[] SKIP_DATABASE = {"incloud_alibaba"};

    private static final String[] SKIP_TABLES = {"nw_irm_", "act_", "alibaba_", "incloud_base_task_blob_triggers",
            "incloud_base_task_calendars", "incloud_base_task_cron_triggers", "incloud_base_task_fired_triggers",
            "incloud_base_task_job_details", "incloud_base_task_locks", "incloud_base_task_paused_trigger_grps",
            "incloud_base_task_scheduler_state", "incloud_base_task_simple_triggers", "incloud_base_task_simprop_triggers",
            "incloud_base_task_triggers"};

    public void handle(String data) {
        BinLogData binLogData = JSONUtil.toBean(data, BinLogData.class, true);
        if (isSkip(binLogData.getDatabase(), binLogData.getTable())) {
            return;
        }
        //执行对应的单独实现类
        try {
            if (StrUtil.isEmpty(binLogData.getTable())) {
                log.error("为获取到对应的表", binLogData.getTable());
            }
            BinLogDataService binLogDataService = SpringUtils.getBean(binLogData.getTable());
            binLogDataService.handle(data);
        } catch (NoSuchBeanDefinitionException e) {
            log.error("未获取到对应的表定义实现类型");
        } catch (Exception e) {
            log.error("执行对应的单独实现类异常:{}", e);
        }
    }

    private Boolean isSkip(String database, String table) {
        if (StrUtil.isEmpty(database) || StrUtil.isEmpty(table)) {
            return false;
        }
        //黑名单--库
        if (Arrays.asList(SKIP_DATABASE).contains(database)) {
            return true;
        }
        //黑名单--表
        return Arrays.asList(SKIP_TABLES).stream().anyMatch(s -> table.startsWith(s));
    }
}
