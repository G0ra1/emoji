package com.netwisd.base.common.constants;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 需要放开权限的url
 *
 * @author 云数网讯
 */
public final class PermitAllUrl {

    /**
     * 监控中心和swagger需要访问的url
     */
    private static final String[] ENDPOINTS = {"/actuator/**",
            "/v2/api-docs/**", "/swagger-ui.html", "/swagger-resources/**","/portalPortal/getDefaultPortal", "/webjars/**"};

     /**
     * 需要放开权限的url
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
