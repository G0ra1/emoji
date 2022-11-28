package com.netwisd.base.common.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/11/04 18:47:00
 */
public class PermitAllUrl {
    /**
     * 监控中心和swagger需要访问的url
     */
    private static final String[] ENDPOINTS = {"/actuator/**","/budgetproProject/getProjectBycode",
            "/v2/api-docs/**", "/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/transcation/**"};

    /*private static final String[] ENDPOINTS = {"/actuator/**","/actuator/configprops","/actuator/beans","/actuator/mappings",
            "/actuator/health", "/actuator/env", "/actuator/metrics/**", "/actuator/trace", "/actuator/dump",
            "/actuator/jolokia", "/actuator/info", "/actuator/logfile", "/actuator/refresh", "/actuator/flyway", "/actuator/liquibase",
            "/actuator/heapdump", "/actuator/loggers", "/actuator/auditevents", "/actuator/env/PID", "/actuator/jolokia/**",
            "/v2/api-docs/**", "/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/transcation/**"};*/

    /**
     * 需要放开权限的url
     *
     * @param urls 自定义的url
     * @return 自定义的url和监控中心需要访问的url集合
     */
    public static String[] permitAllUrl(String... urls) {
        if (urls == null || urls.length == 0) {
            return ENDPOINTS;
        }

        Set<String> set = new HashSet<>();
        Collections.addAll(set, ENDPOINTS);
        Collections.addAll(set, urls);

        return set.toArray(new String[set.size()]);
    }

}
