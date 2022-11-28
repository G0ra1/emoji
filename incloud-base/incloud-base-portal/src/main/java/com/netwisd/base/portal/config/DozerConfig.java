package com.netwisd.base.portal.config;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/9/8 12:53 下午
 */
@Configuration
public class DozerConfig {
    @Bean
    public Mapper dozerMapper(){
        Mapper mapper = DozerBeanMapperBuilder.create()
                //指定 dozer mapping 的配置文件(放到 resources 类路径下即可)，可添加多个 xml 文件，用逗号隔开
                //.withMappingFiles("dozerBeanMapping.xml")
                .withMappingBuilder(beanMappingBuilder())
                .build();
        return mapper;
    }

    @Bean
    public BeanMappingBuilder beanMappingBuilder() {
        return new BeanMappingBuilder() {
            @Override
            protected void configure() {
                // 个性化配置添加在此
                mapping(Page.class,Page.class).exclude("records");
            }
        };
    }
}
