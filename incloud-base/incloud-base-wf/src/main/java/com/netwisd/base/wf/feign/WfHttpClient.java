package com.netwisd.base.wf.feign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.netwisd.base.common.util.AppUserUtil;
import io.seata.core.context.RootContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.Args;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：zouliming@netwisd.com
 * @date ：Created in 2022/2/14 10:54 AM
 * @description：封装的httpclient，主要应用于seata的远程调用，传递XID
 */
@Slf4j
@Component
public class WfHttpClient {
    @Autowired
    HttpClient httpClient;

    @SneakyThrows
    public <T, K> K executePost(String url, T paramObject, Class<K> returnType){
        notNull(url,returnType);
        HttpPost httpPost = new HttpPost(url);
        setHeaderAndEntity(httpPost,paramObject);
        return wrapHttpExecute(returnType, httpPost);
    }

    @SneakyThrows
    public <T, K> K executePut(String url,  T paramObject, Class<K> returnType){
        notNull(url,returnType);
        HttpPut httpPut = new HttpPut(url);
        setHeaderAndEntity(httpPut,paramObject);
        return wrapHttpExecute(returnType, httpPut);
    }

    @SneakyThrows
    public <T, K> K executeGet(String url,  T paramObject, Class<K> returnType){
        notNull(url,returnType);
        HttpGet httpGet = new HttpGet(url);
        setHeaderAndEntity(httpGet,paramObject);
        return wrapHttpExecute(returnType, httpGet);
    }

    <T> void setHeaderAndEntity(HttpUriRequest httpUriRequest, T paramObject){
        String accessToken = AppUserUtil.getAccessToken();
        httpUriRequest.setHeader("Content-Type", "application/json;charset=utf8");
        httpUriRequest.setHeader("Authorization",accessToken);
        StringEntity entity = handleEntity(paramObject);
        if (entity != null) {
            if(httpUriRequest instanceof HttpEntityEnclosingRequestBase){
                ((HttpEntityEnclosingRequestBase) httpUriRequest).setEntity(entity);
            }
        }
    }

    <K> void notNull(String url, Class<K> returnType){
        Args.notNull(returnType, "returnType");
        Args.notNull(url, "url");
    }

    <T> StringEntity handleEntity(T paramObject){
        StringEntity entity = null;
        if (paramObject != null) {
            String content;
            if (paramObject instanceof String) {
                String sParam = (String) paramObject;
                JSONObject jsonObject = null;
                try {
                    jsonObject = JSON.parseObject(sParam);
                    content = jsonObject.toJSONString();
                } catch (JSONException e) {
                    log.warn(e.getMessage());
                    content = sParam;
                }
            } else {
                content = JSON.toJSONString(paramObject);
            }
            entity = new StringEntity(content, ContentType.APPLICATION_JSON);
        }
        return buildEntity(entity, paramObject);
    }

    public <T> StringEntity buildEntity(StringEntity entity, T t) {
        return entity;
    }

    /**
     * 处理执行，在header中增加xid
     * @param returnType
     * @param httpUriRequest
     * @param <K>
     * @return
     */
    @SneakyThrows
    private <K> K wrapHttpExecute(Class<K> returnType, HttpUriRequest httpUriRequest){
        Map<String, String> headers = new HashMap<>();
        HttpResponse response;
        String xid = RootContext.getXID();
        if (xid != null) {
            headers.put(RootContext.KEY_XID, xid);
        }
        if (!headers.isEmpty()) {
            headers.forEach(httpUriRequest::addHeader);
        }

        response = httpClient.execute(httpUriRequest);
        int statusCode = response.getStatusLine().getStatusCode();
        /** 2xx is success. */
        if (statusCode < HttpStatus.SC_OK || statusCode > HttpStatus.SC_MULTI_STATUS) {
            log.error("wrapHttpExecute执行错误");
            /*throw new RuntimeException("Failed to invoke the http method "
                    + httpUriRequest.getURI() + " in the service "
                    + ". return status by: " + response.getStatusLine().getStatusCode());*/
        }

        return convertResult(response, returnType);
    }

    public <K> K convertResult(HttpResponse response, Class<K> clazz) {
        if (clazz == HttpResponse.class) {
            return (K) response;
        }
        return null;
    }
}
