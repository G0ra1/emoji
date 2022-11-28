package com.netwisd.base.socket.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.netwisd.base.socket.constant.SocketUserManager;
import com.netwisd.base.socket.dto.ApplicationLogDto;
import com.netwisd.base.socket.entity.ApplicationLog;
import com.netwisd.base.socket.service.ApplicationLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ApplicationLogServiceImpl implements ApplicationLogService {

    @Override
    public void sendLogByCond(String message) {
        SocketUserManager.getInstance().getLogMapUserAll().forEach((k, v) -> {
            ApplicationLogDto applicationLogDto = JSON.parseObject(v.getAttributes().get("cond").toString(), ApplicationLogDto.class);
            if (applicationLogDto.isOpen() && whetherToSend(applicationLogDto, message)) {
                syncSendMsg(v, message);
            }
        });
    }

    //判断是否可以发送
    private boolean whetherToSend(ApplicationLogDto searchDto, String jsonLog) {
        String appName = searchDto.getAppName();
        String host = searchDto.getHost();
        String level = searchDto.getLevel();
        String keyword = searchDto.getKeyword();
        if (StrUtil.isEmpty(appName) && StrUtil.isEmpty(host) && StrUtil.isEmpty(level) && StrUtil.isEmpty(keyword)) {
            return true;
        }
        ApplicationLog applicationLog = JSON.parseObject(jsonLog, ApplicationLog.class);
        boolean appNameResult = true, hostResult = true, levelResult = true, keyWordResult = true;
        if (StrUtil.isNotEmpty(appName) && !appName.equals(applicationLog.getAppName())) appNameResult = false;
        if (StrUtil.isNotEmpty(host) && !host.equals(applicationLog.getHost())) hostResult = false;
        if (StrUtil.isNotEmpty(level) && !level.equals(applicationLog.getLevel())) levelResult = false;
        if (StrUtil.isNotEmpty(keyword) && !StrUtil.contains(keyword, applicationLog.getBody())) keyWordResult = false;
        return (appNameResult && hostResult && levelResult && keyWordResult);
    }
}
