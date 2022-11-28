package com.netwisd.common.log.entity;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class SystemLog {

    //应用名称
    private String appName;

    //日志类型
    private String logType;

    //操作类型
    private String operateType;

    private String content;

    private String remoteAddr;

    private String userAgent;

    private String requestUri;

    private String method;

    private String requestType;

    private String params;

    private long execTime;

    private String createUserId;

    private String createUserName;

    private LocalDateTime createTime;
}
