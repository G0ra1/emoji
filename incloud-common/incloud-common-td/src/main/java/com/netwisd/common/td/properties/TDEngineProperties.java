package com.netwisd.common.td.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "td")
public class TDEngineProperties {

    private String ip;
    private String port;
    private String schema;
    private String username;
    private String password;

    private String driverClassName = "com.taosdata.jdbc.TSDBDriver";
    private String url;
    //配置初始化大小、最小、最大
    private int initialSize = 50;
    private int minIdle = 50;
    private int maxActive = 50;
    //配置获取连接等待超时的时间
    private long maxWait = 6000L;
    //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
    private long timeBetweenEvictionRunsMillis = 60000L;
    //配置一个连接在池中最小生存的时间，单位是毫秒
    private long minEvictableIdleTimeMillis = 600000L;
    private long maxEvictableIdleTimeMillis = 900000L;
    private String validationQuery = "select server_status()";

    public String getUrl() {
        return "jdbc:TAOS://" + this.getIp() + ":" + this.getPort() + "/" + this.getSchema() + "?timezone=UTC-8&charset=UTF-8&locale=en_US.UTF-8";
    }
}
