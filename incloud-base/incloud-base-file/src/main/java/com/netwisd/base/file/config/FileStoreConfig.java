package com.netwisd.base.file.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: current project incloud
 * current package com.netwisd.base.file.config
 * @Author: zouliming
 * @Date: 2020/2/9 4:12 下午
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "file.enable")
public class FileStoreConfig {
    private String storeType;
}
