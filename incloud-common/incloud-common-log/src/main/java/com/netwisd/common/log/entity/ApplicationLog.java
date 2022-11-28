package com.netwisd.common.log.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class ApplicationLog {

    private String appName;
    private String body;
    private LocalDateTime createTime;
    private String threadName;
    private String className;
    private String level;
    private String host = getIp();

    public ApplicationLog(String appName, String body, LocalDateTime createTime, String threadName, String className, String level) {
        this.appName = appName;
        this.body = body;
        this.createTime = createTime;
        this.threadName = threadName;
        this.className = className;
        this.level = level;
    }

    public ApplicationLog() {
    }

    String getIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            //不处理
        }
        return "UnknownHost";
    }

}
