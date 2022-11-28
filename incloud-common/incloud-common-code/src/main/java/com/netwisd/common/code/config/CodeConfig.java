package com.netwisd.common.code.config;

import com.netwisd.common.code.service.GeneratorService;
import com.netwisd.common.code.service.impl.GeneratorServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.netwisd.common.code.mapper")
public class CodeConfig {

    @Bean
    public GeneratorService generatorService(){
        return new GeneratorServiceImpl();
    }
}
