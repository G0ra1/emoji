package com.netwisd.common.es.config;

import com.alibaba.google.common.collect.Lists;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import lombok.Data;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

/**
 * @Description: canal server 相关配置
 * @Author: zouliming@netwisd.com
 * @Date: 2020/9/13 5:05 下午
 */
@Configuration
@ConfigurationProperties(prefix = "canal")
@Data
public class CanalConfig implements DisposableBean {
    private CanalConnector canalConnector;

    private String canalServer;
    private Integer canalPort;
    private String destination;
    private String userName;
    private String password;

    //@Bean
    public CanalConnector canalConnector(){
        canalConnector = CanalConnectors.newClusterConnector(Lists.newArrayList(
                new InetSocketAddress(canalServer, canalPort)),
                destination,userName,password
        );
        canalConnector.connect();
        //指定filter，格式{database}.{table},这个直接可以在canal里配置，不过我们自己使用程序实现黑白名单，这样最保险
        canalConnector.subscribe();
        //回滚寻找上次中断的为止
        canalConnector.rollback();
        return canalConnector;
    }

    //spring容器销毁时，关闭掉canal
    @Override
    public void destroy() throws Exception {
        if(canalConnector != null){
            canalConnector.disconnect();
        }
    }
}
