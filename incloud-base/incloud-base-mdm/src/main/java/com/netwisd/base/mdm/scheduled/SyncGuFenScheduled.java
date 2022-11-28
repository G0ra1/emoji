package com.netwisd.base.mdm.scheduled;

import com.netwisd.base.mdm.config.ApplicationContextUti;
import com.netwisd.base.mdm.service.QyWechatApiService;
import com.netwisd.base.mdm.service.SyncGuFenService;
import com.netwisd.base.mdm.service.impl.RedisQyWeChatCodeServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Configuration
@EnableScheduling
@Slf4j
public class SyncGuFenScheduled {
    @Scheduled(cron = "0 30 6,21 * * ?") //每天早晨6点30 晚上21点30 处理机构
    private void configureTasks1() {
        log.debug("=============同步股份数据定时器开始执行===========");
        SyncGuFenService syncGuFenService = (SyncGuFenService) ApplicationContextUti.getBean("syncGuFenService");
        // 错开同步  只同步机构、岗位
        syncGuFenService.syncGuFenOrgAndPostIncrement(getYesterDay());
//        //同步增量  人员
//        syncGuFenService.syncGuFenUserAndMasterPostIncrement(getYesterDay());
//        //同步 兼岗
//        syncGuFenService.syncGuFenPosition();
//        //处理机构的过期
//        syncGuFenService.handleOrgPastDue();
//        //处理岗位过期
//        syncGuFenService.handlePostPastDue();
        log.debug("=============同步股份数据定时器执行结束===========");
    }


    @Scheduled(cron = "0 10 7,22 * * ?") //每天早晨7点10 晚上22点10 处理人员
    private void configureTasks2() {
        log.debug("=============同步股份数据定时器开始执行===========");
        SyncGuFenService syncGuFenService = (SyncGuFenService) ApplicationContextUti.getBean("syncGuFenService");
//      //同步增量  机构、岗位
//      syncGuFenService.syncGuFenOrgAndPostIncrement(getYesterDay());
        //同步增量  人员
        syncGuFenService.syncGuFenUserAndMasterPostIncrement(getYesterDay());
        //同步 兼岗
        syncGuFenService.syncGuFenPosition();
        //处理机构的过期
        syncGuFenService.handleOrgPastDue();
        //处理岗位过期
        syncGuFenService.handlePostPastDue();
        log.debug("=============同步股份数据定时器执行结束===========");
    }

    public static String getYesterDay() {
        Date today = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String yesterday = simpleDateFormat.format(today);//获取昨天日期
        return yesterday;
    }

    @Scheduled(cron = "0 40 7 * * ?") //每天凌晨7点40
    private void qyWechatSync(){
        try {
            log.debug("=============刷新企业微信AccessToken定时器开始执行===========");
            RedisQyWeChatCodeServices redisQyWeChatCodeServices = (RedisQyWeChatCodeServices) ApplicationContextUti.getBean("redisQyWeChatCodeServices");
            redisQyWeChatCodeServices.getAccessTokenByHttp();
            log.debug("=============刷新企业微信AccessToken定时器执行结束===========");

            QyWechatApiService qyWechatApiService = (QyWechatApiService) ApplicationContextUti.getBean("qyWechatApiService");
            log.debug("=============同步企业微信部门定时器开始执行===========");
            qyWechatApiService.syncAllDept();
            log.debug("=============同步企业微信部门定时器结束执行===========");

            log.debug("=============同步企业微信人员定时器开始执行===========");
            qyWechatApiService.syncAllUser();
            log.debug("=============同步企业微信人员定时器结束执行===========");
        }catch (Exception e){
            log.debug("刷新企业微信AccessToken失败============================"+e.getMessage());
        }

    }
}
