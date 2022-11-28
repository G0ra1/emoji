package com.netwisd.biz.mdm.scheduled;

import com.netwisd.biz.mdm.config.ApplicationContextUti;
import com.netwisd.biz.mdm.service.SyncGldService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.ParseException;

@Configuration
@EnableScheduling
@Slf4j
public class SyncGldScheduled {


    @Scheduled(cron = "0 0 1 * * ?") //每天凌晨一点
    //@Scheduled(cron = "*/20 * * * * ?") //每20秒
    //@Scheduled(cron = "0 */30 * * * ?") //每隔30分钟执行一次
    private void configureTasks()throws java.io.IOException, ParseException {
        log.debug("=============同步集采数据定时器开始执行===========");
        SyncGldService syncGldService = (SyncGldService) ApplicationContextUti.getBean("syncGldService");
        syncGldService.syncGldSupplier();
        syncGldService.syncGldPurchase();
        log.debug("=============同步集采数据定时器执行结束===========");
    }
}
