package com.netwisd.base.file.config;

/**
 * @Description: current project incloud
 * current package com.netwisd.base.file.config
 * @Author: zouliming
 * @Date: 2020/2/9 4:14 下午
 */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * yaml中的minio配置读取类
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "file.minio")
public class MinioYmal {
    private String url;
    private String accessKey;
    private String secretKey;
    private String bucketName;
}
