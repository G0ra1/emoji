package com.netwisd.base.wf.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.wf.feign.DynamicFeignClient;
import com.netwisd.base.wf.feign.RemoteApi;
import com.netwisd.common.core.exception.IncloudException;
import feign.FeignException;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Description:  获取服务节点的tag标签
 * @Author: XHL
 * @Date: 2022/2/24 3:28 下午
 */
@Slf4j
@RestController
@RequestMapping("/procTag")
@RefreshScope
public class ProcTagController {

    @Autowired
    DynamicFeignClient<RemoteApi> client;

    @ApiOperation(value = "根据服务节点ID获取业务任务事件tag标签", notes = "根据服务节点ID获取业务任务事件tag标签")
    @RequestMapping(value = "/getTaskTagByServiceId", method = RequestMethod.GET)
    public List<String> getTagByServiceId(String serviceId){
        if(StringUtils.isBlank(serviceId)) {
            throw new IncloudException("服务Id不能为空！");
        }
        RemoteApi api = null;
        try {
            api = client.GetFeignClient(RemoteApi.class, serviceId);
        } catch (Exception e){
            throw new IncloudException("通过 serviceId 找不到相应服务，请检查参数配置和服务运行状态！");
        }
        try {
            List<String> methodsNames = api.getTaskTagByServiceId();
            if(CollectionUtil.isNotEmpty(methodsNames)) {
                return methodsNames;
            }
        }catch (FeignException e){
            String content = new String(e.content());
            ObjectMapper mapper = new ObjectMapper();
            try {
                Map<String,String> map = mapper.readValue(content, Map.class);
                String msg = map.get("msg");
                log.error("调用目标接口失败！{}",msg);
                throw new IncloudException(msg);
            } catch (JsonProcessingException jsonProcessingException) {
                jsonProcessingException.printStackTrace();
            }
        }
        return null;
    }

    @ApiOperation(value = "根据服务节点ID获取业务执行事件tag标签", notes = "根据服务节点ID获取业务执行事件tag标签")
    @RequestMapping(value = "/getExeTagByServiceId", method = RequestMethod.GET)
    public List<String> getExeTagByServiceId(String serviceId){
        if(StringUtils.isBlank(serviceId)) {
            throw new IncloudException("服务Id不能为空！");
        }
        RemoteApi api = null;
        try {
            api = client.GetFeignClient(RemoteApi.class, serviceId);
        } catch (Exception e){
            throw new IncloudException("通过 serviceId 找不到相应服务，请检查参数配置和服务运行状态！");
        }
        try {
            List<String> methodsNames = api.getExeTagByServiceId();
            if(CollectionUtil.isNotEmpty(methodsNames)) {
                return methodsNames;
            }
        }catch (FeignException e){
            String content = new String(e.content());
            ObjectMapper mapper = new ObjectMapper();
            try {
                Map<String,String> map = mapper.readValue(content, Map.class);
                String msg = map.get("msg");
                log.error("调用目标接口失败！{}",msg);
                throw new IncloudException(msg);
            } catch (JsonProcessingException jsonProcessingException) {
                jsonProcessingException.printStackTrace();
            }
        }
        return null;
    }

    @ApiOperation(value = "根据服务节点ID获取业务条件表达式tag标签", notes = "根据服务节点ID获取业务条件表达式tag标签")
    @RequestMapping(value = "/getConditionTagByServiceId", method = RequestMethod.GET)
    public List<String> getConditionTagByServiceId(String serviceId){
        if(StringUtils.isBlank(serviceId)) {
            throw new IncloudException("服务Id不能为空！");
        }
        RemoteApi api = null;
        try {
            api = client.GetFeignClient(RemoteApi.class, serviceId);
        } catch (Exception e){
            throw new IncloudException("通过 serviceId 找不到相应服务，请检查参数配置和服务运行状态！");
        }
        try {
            List<String> methodsNames = api.getConditionTagByServiceId();
            if(CollectionUtil.isNotEmpty(methodsNames)) {
                return methodsNames;
            }
        }catch (FeignException e){
            String content = new String(e.content());
            ObjectMapper mapper = new ObjectMapper();
            try {
                Map<String,String> map = mapper.readValue(content, Map.class);
                String msg = map.get("msg");
                log.error("调用目标接口失败！{}",msg);
                throw new IncloudException(msg);
            } catch (JsonProcessingException jsonProcessingException) {
                jsonProcessingException.printStackTrace();
            }
        }
        return null;
    }
}
