package com.netwisd.base.wf.event.delegate;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netwisd.base.wf.entity.WfProcess;
import com.netwisd.base.wf.feign.DynamicFeignClient;
import com.netwisd.base.wf.feign.RemoteApi;
import com.netwisd.base.wf.service.runtime.WfProcessService;
import com.netwisd.base.wf.starter.event.ExecutionEntity;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.Result;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description: 通用业务同步feign接口调用
 * @Author: zouliming@netwisd.com
 * @Date: 2021/3/19 11:34
 */
@Slf4j
@Component
@Setter
public class BizDelegate implements ExecutionListener {

    private Expression serviceId;

    private Expression bizTag;

    //非camunda正常调用时，反射赋值给这个属性，主要原因是我们自己给Expression赋值非常复杂，这个类的实现内容非常多；
    private String serviceIdStr;

    //非camunda正常调用时，反射赋值给这个属性，主要原因是我们自己给Expression赋值非常复杂，这个类的实现内容非常多；
    private String bizTagStr;

    @Autowired
    DynamicFeignClient<RemoteApi> client;

    @Autowired
    WfProcessService wfProcessService;

    @SneakyThrows
    @Override
    public void notify(DelegateExecution execution) {
        ExpressionAndBizTag expressionAndBizTag = check(execution);
        String serviceIdValue = expressionAndBizTag.getServiceIdValue();
        RemoteApi api = null;
        try {
            api = client.GetFeignClient(RemoteApi.class, serviceIdValue);
        }catch (Exception e){
            throw new IncloudException("通过："+serviceId+"找不到相应服务，请检查参数配置和服务运行状态！");
        }
        ExecutionEntity executionEntity = initExecutionEntity(execution,expressionAndBizTag);
        try {
            Result result = api.expressionExecutionParser(executionEntity);
            if(result.getCode() != 200){
                log.error("调用目标接口返回失败（非200）！");
                throw new IncloudException(result.getMsg());
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
    }

    ExpressionAndBizTag check(DelegateExecution execution){
        String serviceIdExp = null,bizTagExp = null;
        try {
            serviceIdExp = serviceId.getValue(execution).toString();
        }catch (Exception e){
            log.error("serviceId 获取值失败！");
            if(StrUtil.isEmpty(serviceIdStr)){
                throw new IncloudException("serviceId：事件参数为空或不匹配！");
            }
        }
        try {
            bizTagExp = bizTag.getValue(execution).toString();
        }catch (Exception e){
            log.error("bizTag 获取值失败！");
            if(StrUtil.isEmpty(bizTagStr)){
                throw new IncloudException("bizTag：事件参数为空或不匹配！");
            }
        }
        if(StrUtil.isEmpty(serviceIdExp)){
            if(StrUtil.isEmpty(serviceIdStr)){
                throw new IncloudException("serviceId：事件参数为空或不匹配！");
            }
            serviceIdExp = serviceIdStr;
        }
        if(StrUtil.isEmpty(bizTagExp)){
            if(StrUtil.isEmpty(bizTagStr)){
                throw new IncloudException("serviceId：事件参数为空或不匹配！");
            }
            bizTagExp = bizTagStr;
        }

        log.info("serviceId值为:-> {} ， bizTag值为:-> {}",serviceIdExp,bizTagExp);
        return new ExpressionAndBizTag(serviceIdExp,bizTagExp);
    }

    ExecutionEntity initExecutionEntity(DelegateExecution execution, ExpressionAndBizTag expressionAndBizTag){
        String processInstanceId = execution.getProcessInstanceId();
        ExecutionEntity executionEntity = new ExecutionEntity();
        executionEntity.setProcessDefinitionId(execution.getProcessDefinitionId());
        executionEntity.setProcessInstanceId(execution.getProcessInstanceId());
        executionEntity.setBizTag(expressionAndBizTag.getBizTagValue());
        executionEntity.setActivityId(execution.getCurrentActivityId());
        executionEntity.setActivityName(execution.getCurrentActivityName());
        WfProcess wfProcess = wfProcessService.get(processInstanceId);
        if(ObjectUtil.isEmpty(wfProcess)){
            throw new IncloudException("BizListener中，根据流程实例ID"+processInstanceId+"查找不到相应的流程实例WfProcess！");
        }
        /*executionEntity.setFormKey(wfProcess.getFormKey());*/
        return executionEntity;
    }

    @Data
    @AllArgsConstructor
    class ExpressionAndBizTag{
        private String serviceIdValue;
        private String bizTagValue;
    }
}