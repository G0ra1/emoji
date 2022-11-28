package com.netwisd.base.socket.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jiguang")
public class JiguangConfig {
    private String appKey;

    private String masterSecret;

    private Integer tempId;

    private Integer signId;
}
