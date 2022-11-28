package com.netwisd.common.log.constant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationProperty {

    private static ApplicationProperty instance = new ApplicationProperty();

    //子系统名称
    private String appName;

    //RocketMq访问地址
    private String rocketMqUrl;

    //是否开启发送应用日志
    private String applicationLogIsSendRocket;

    private ApplicationProperty() {
    }

    public static ApplicationProperty getInstance() {
        return instance;
    }
}
