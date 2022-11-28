package com.netwisd.base.wf.expression;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netwisd.base.common.user.vo.expression.UserExpressionVO;
import com.netwisd.base.wf.constants.InnerUserExpressionEnum;
import com.netwisd.base.wf.entity.WfProcess;
import com.netwisd.base.wf.feign.MdmFeignClient;
import com.netwisd.base.wf.service.runtime.WfProcessService;
import com.netwisd.base.wf.starter.expression.ExpressionEntity;
import com.netwisd.base.wf.starter.expression.HandleExpressionParam;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.Result;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 人员表达式，调用主数据
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/29 4:47 下午
 */
@Component
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class WfUserExpression {
    @Autowired
    MdmFeignClient mdmFeignClient;

    @Autowired
    TaskService taskService;

    @Autowired
    WfProcessService wfProcessService;

    /**
     * 人员表达式执行
     * @param taskId 传递过来taskId，用于查找相应的camunda信息
     * @param bizTag 业务tag，实际对应的相应的业务方法名
     * @param wfArgs 此参数为了对应camunda的xml存储的原参数，里面可能包括动态参数的变量，而非实际值
     * @param wfArgVar 动态处理wfArgs作为字符串中的变量，然后存储到wfArgVar中
     * @return
     */
    public List<UserExpressionVO> notify(String taskId, String bizTag, String wfArgs, String wfArgVar){
        Result<List<UserExpressionVO>> result = null;
        ExpressionEntity expressionEntity = initExpressionEntity(taskId,bizTag, wfArgVar);
        try {
            result = mdmFeignClient.expressionUserParser(expressionEntity);
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
        List<UserExpressionVO> data = result.getData();
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
    ExpressionEntity initExpressionEntity(String taskId, String bizTag, String args){
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
                expressionEntity.setStarterId(wfProcess.getStarterId());
            }
            if(StrUtil.isNotEmpty(args)){
                List<HandleExpressionParam> handleExpressionParamList = JSONObject.parseArray(args, HandleExpressionParam.class);
                //Map map = JacksonUtil.parseObject(args, Map.class);
                Map map = handleExpressionParamList.stream().collect(Collectors.toMap(HandleExpressionParam::getParamId, d->d.getParamValue(),(key1, key2) -> key2));
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
     * 内部表达式
     * @param innerUserExp
     * @return
     */
    @Deprecated
    public InnerUserExpressionEnum innerExpression(String innerUserExp){
        if(StrUtil.isEmpty(innerUserExp)) throw new IncloudException("表达式不能为空");
        if(innerUserExp.equalsIgnoreCase(InnerUserExpressionEnum.START_USER.name())){
            return InnerUserExpressionEnum.START_USER;
        }if(innerUserExp.equalsIgnoreCase(InnerUserExpressionEnum.LAST_USER.name())){
            return InnerUserExpressionEnum.LAST_USER;
        }else {
            throw new IncloudException("类型:{}错误，请检查",innerUserExp);
        }
    }
}
