package com.netwisd.base.wf.expression;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netwisd.base.wf.entity.WfProcess;
import com.netwisd.base.wf.feign.DynamicFeignClient;
import com.netwisd.base.wf.feign.RemoteApi;
import com.netwisd.base.wf.service.runtime.WfProcessService;
import com.netwisd.base.wf.starter.expression.ExpressionEntity;
import com.netwisd.base.wf.starter.expression.HandleExpressionParam;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.Result;
import feign.FeignException;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: 通用表达式处理
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/29 4:47 下午
 */
@Component
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Api(value = "wfConditionExpression", tags = "通用表达式处理")
public class WfConditionExpression<T> {

    /**
     * 动态获取feign客户端
     */
    @Autowired
    DynamicFeignClient<RemoteApi> client;

    @Autowired
    private TaskService taskService;

    @Autowired
    WfProcessService wfProcessService;

    /**
     * 条件表达式执行
     * @param taskId 传递过来taskId，用于查找相应的camunda信息
     * @param serviceId 服务名，后期可能会改用httpclient
     * @param bizTag 业务tag，实际对应的相应的业务方法名
     * @param wfArgs 此参数为了对应camunda的xml存储的原参数，里面可能包括动态参数的变量，而非实际值
     * @param wfArgVar 动态处理wfArgs作为字符串中的变量，然后存储到wfArgVar中
     * @return
     */
    public T notify(String taskId,String serviceId,String bizTag,String wfArgs,String wfArgVar){
        validation(serviceId,bizTag);
        RemoteApi api = null;
        Result<T> result = null;
        try {
            api = client.GetFeignClient(RemoteApi.class, serviceId);
        }catch (Exception e){
            throw new IncloudException("表达式通过 serviceId 找不到相应服务，请检查参数配置和服务运行状态！");
        }
        ExpressionEntity expressionEntity = initExpressionEntity(taskId,bizTag, wfArgVar);
        try {
             result = api.expressionConditionParser(expressionEntity);
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
        T data = result.getData();
        log.info("执行表达式获取的结果为-> {}",data);
        return data;
    }

    /**
     * 构建ExpressionEntity
     * @param taskId
     * @param bizTag
     * @param args
     * @return
     */
    ExpressionEntity initExpressionEntity(String taskId,String bizTag,String args){
        ExpressionEntity expressionEntity = new ExpressionEntity();
        try {
            if(StringUtils.isNotBlank(taskId)) {
                Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
                WfProcess wfProcess = wfProcessService.get(task.getProcessInstanceId());
                /*expressionEntity.setFormKey(wfProcess.getFormKey());*/
                expressionEntity.setProcdefTypeId(wfProcess.getProcdefTypeId());
                expressionEntity.setProcdefTypeName(wfProcess.getProcdefTypeName());
                expressionEntity.setProcdefVersion(wfProcess.getCamundaProcdefVersion());
                expressionEntity.setProcessDefinitionId(wfProcess.getCamundaProcdefId());
                expressionEntity.setProcessInstanceId(task.getProcessInstanceId());
                expressionEntity.setTaskDefinitionKey(task.getTaskDefinitionKey());
            }
            if(StrUtil.isNotEmpty(args)){
                List<HandleExpressionParam> handleExpressionParamList = JSONObject.parseArray(args, HandleExpressionParam.class);
                //Map map = JacksonUtil.parseObject(args, Map.class);
                Map map = handleExpressionParamList.stream().collect(Collectors.toMap(HandleExpressionParam::getParamId,d->d.getParamValue(),(key1, key2) -> key2));
                expressionEntity.setArgs(map);
                expressionEntity.setArgList(handleExpressionParamList);
            }
            if(StrUtil.isNotEmpty(bizTag)) {
                expressionEntity.setBizTag(bizTag);
            }
        }catch (Exception e){
            throw new IncloudException("args参数转换失败，请检查工作流的表达式参数配置是否正确！");
        }
        return expressionEntity;
    }

    /**
     *
     * @param serviceId
     * @param bizTag
     */
    private void validation(String serviceId,String bizTag){
        /*if(ObjectUtil.isEmpty(returnType)){
            throw new IncloudException("返回类型不能为空！");
        }*/
        if(ObjectUtil.isEmpty(serviceId)){
            throw new IncloudException("服务ID不能为空！");
        }
        if(ObjectUtil.isEmpty(bizTag)){
            throw new IncloudException("bizTag不能为空！");
        }
    }
}
