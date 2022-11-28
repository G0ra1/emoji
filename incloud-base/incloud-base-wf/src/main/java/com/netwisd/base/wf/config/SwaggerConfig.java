package com.netwisd.base.wf.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description: swagger文档，增加Knife4j增强
 * @Author: zouliming
 * @Date: 2020/2/8 12:28 下午
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {

	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("工作流swagger接口文档")
				.apiInfo(new ApiInfoBuilder().title("工作流swagger接口文档")
						.contact(new Contact("云数网讯", "", "zouliming@netwisd.com")).version("3.0").build())
				.select().paths(PathSelectors.any()).build();
	}
}
