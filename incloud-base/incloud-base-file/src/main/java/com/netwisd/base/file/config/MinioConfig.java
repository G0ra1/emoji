package com.netwisd.base.file.config;

import io.minio.MinioClient;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * minio配置
 * @author 云数网讯 zouliming@netwisd.com
 */
@AllArgsConstructor
@Configuration
@EnableConfigurationProperties({MinioYmal.class})
public class MinioConfig {
    private MinioYmal minioYmal;

    @Bean
    @SneakyThrows
    public MinioClient minioClient(){
        MinioClient minioClient = new MinioClient(minioYmal.getUrl(),minioYmal.getAccessKey(),minioYmal.getSecretKey());
        return minioClient;
    }

}

