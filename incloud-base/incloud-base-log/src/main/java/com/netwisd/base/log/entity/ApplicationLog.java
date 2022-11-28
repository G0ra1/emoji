package com.netwisd.base.log.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ApplicationLog {

    private String appName;
    private String body;
    private String threadName;
    private String className;
    private String level;
    private String host;
}
