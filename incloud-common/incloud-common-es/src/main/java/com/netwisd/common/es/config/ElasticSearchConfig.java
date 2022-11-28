package com.netwisd.common.es.config;

import com.netwisd.common.es.service.ElasticSearchService;
import com.netwisd.common.es.service.impl.ElasticSearchServiceImpl;
import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: es配置
 * current package com.netwisd.log.config
 * @Author: zouliming
 * @Date: 2020/2/13 10:53 上午
 */
@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
@Data
public class ElasticSearchConfig {
    private String address;

    /**
     * 初始化restful客户端，这个可以做为了一个单节点用，也可以用做集群节点设置
     * @return
     */
    @Bean
    public RestHighLevelClient esClient(){
        String[] adds = address.split(":");
        String ip = adds[0];
        int port = Integer.valueOf(adds[1]);
        HttpHost httpHost = new HttpHost(ip,port,"http");
        return new RestHighLevelClient(RestClient.builder(new HttpHost[]{httpHost}));
    }

    @Bean
    public ElasticSearchService elasticSearchService(){
        ElasticSearchService elasticSearchService = new ElasticSearchServiceImpl();
        return elasticSearchService;
    }
}
