package com.netwisd.base.socket.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: swagger文档，增加Knife4j增强
 * @Author: zouliming
 * @Date: 2020/2/8 12:28 下午
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {

    private static final String splitter = ";";

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("SocketSwagger接口文档")
                .apiInfo(apiInfo())
                .select()
                .apis(basePackage("com.netwisd"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(token())
                .securityContexts(contexts());
    }

    private List<ApiKey> token() {
        List<ApiKey> keys = new ArrayList<>();
        keys.add(new ApiKey("Authorization", "Authorization", "header"));
        return keys;
    }

    private List<SecurityContext> contexts() {
        List<SecurityContext> contexts = new ArrayList<>();
        contexts.add(SecurityContext.builder()
                .securityReferences(auth())
                .forPaths(PathSelectors.any())
                .build());
        return contexts;
    }

    private List<SecurityReference> auth() {
        List<SecurityReference> auths = new ArrayList<>();
        auths.add(new SecurityReference("Authorization", new AuthorizationScope[0]));
        return auths;
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Programming Makes Us Happy")
                .description("Elizabeth")
                .contact(new Contact("Sunzhenix",
                        "https://xxxx.com",
                        "sunzhenxi@netwisd.com"))
                .version("1.0.0")
                .build();
    }

    public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return input -> declaringClass(input).transform(handlerPackage(basePackage)).or(true);
    }

    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
        return input -> {
            // 循环判断匹配
            for (String strPackage : basePackage.split(splitter)) {
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
    }
}
