package com.netwisd.common.td;

import com.netwisd.common.td.properties.TDEngineProperties;
import com.netwisd.common.td.util.TDUtil;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(TDEngineProperties.class)
public class TDAutoConfiguration {

    @Bean
    public TDUtil tdUtil() {
        return new TDUtil();
    }
}
