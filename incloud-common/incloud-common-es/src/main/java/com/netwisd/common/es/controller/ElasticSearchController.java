package com.netwisd.common.es.controller;

import com.netwisd.common.es.service.ElasticSearchService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 业务端通过feign调用的操作controller
 * @Author: zouliming@netwisd.com
 * @Date: 2020/2/17 5:54 下午
 */
@Slf4j
@RequestMapping("/es")
@RestController
@AllArgsConstructor
@RefreshScope
@Api(value = "elasticSearchController", tags = "ElasticSearchController")
public class ElasticSearchController {
    private final ElasticSearchService elasticSearchService;


}
