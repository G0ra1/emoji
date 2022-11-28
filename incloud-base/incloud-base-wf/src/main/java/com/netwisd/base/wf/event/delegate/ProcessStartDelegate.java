package com.netwisd.base.wf.event.delegate;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.common.wf.eunm.WfProcessEnum;
import com.netwisd.base.wf.constants.BooleanEnum;
import com.netwisd.base.wf.constants.InnerVariableEnum;
import com.netwisd.base.wf.dto.WfProcessDto;
import com.netwisd.base.wf.feign.UserClient;
import com.netwisd.base.wf.service.*;
import com.netwisd.base.wf.service.procdef.WfNodeDefService;
import com.netwisd.base.wf.service.runtime.WfProcessLogService;
import com.netwisd.base.wf.service.runtime.WfProcessService;
import com.netwisd.base.wf.vo.WfNodeDefVo;
import com.netwisd.base.wf.vo.WfProcDefVo;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.db.cache.IncloudCache;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 流程实例创始化 exection listener的 start事件中触发
 * 为callActivity外链的流程定义本身提供
 * @Author: zouliming@netwisd.com
 * @Date: 2020/10/21 3:27 下午
 */
@Slf4j
@Component
public class ProcessStartDelegate implements ExecutionListener {

    private final static String nodeId = "firstNode";

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    WfProcessService wfProcessService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    WfNodeDefService wfNodeDefService;

    @Autowired
    WfProcdefService wfProcdefService;

    @Autowired
    UserClient userClient;

    @Autowired
    Mapper dozerMapper;

    @Autowired
    WfProcessLogService wfProcessLogService;

    @Autowired
    @Qualifier("incloudUserCache")
    private IncloudCache<MdmUserVo> incloudCache;

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        //这里的executionId，指的就是当前这个流程实例的ID——注意是当前这个流程
        String businessKey = execution.getBusinessKey();
        String executionId = execution.getId();
        //获取到办理人
        Object variable = runtimeService.getVariable(executionId, InnerVariableEnum.ASSIGNEE.getName());
        Object resonObj = runtimeService.getVariable(executionId, InnerVariableEnum.REASON.getName());
        Object bizpriorityObj = runtimeService.getVariable(executionId, InnerVariableEnum.BIZPRIORITY.getName());
        String userName = null;
        if(ObjectUtil.isNotEmpty(variable)){
            if(variable instanceof String){
                userName =  variable.toString();
            }else {
                List<String> wfAssigneeList = (List)variable;
                StringBuffer sb = new StringBuffer();
                wfAssigneeList.forEach(wfAssignee -> sb.append(wfAssignee).append(","));
                userName = sb.substring(0,sb.length()-1);
            }
        }
        Map<String, Object> variables = new HashMap<String,Object>();
        //如果是流程直接启动是不用再放一次的
        variables.put(InnerVariableEnum.ASSIGNEE.getName(), userName);

        DelegateExecution processInstance = execution.getProcessInstance();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(execution.getProcessDefinitionId()).singleResult();
        WfProcessDto wfProcessDto = null;
        //证明当前流程是做为子流程来启动的，否则的话，不处理；
        if(StrUtil.isNotEmpty(businessKey) && StrUtil.startWith(businessKey,"sub_")){
            //这两个是父流程实例ID
            String camundaParentId = runtimeService.getVariable(executionId, InnerVariableEnum.CAMUNDA_PROCESS_ID.getName()).toString();
            String callActivityDefId = runtimeService.getVariable(executionId, InnerVariableEnum.CALLACTIVITY_DEF_ID.getName()).toString();
            String parentActInsId = runtimeService.getVariable(executionId, InnerVariableEnum.ACT_INS_ID.getName()).toString();
            Long parentId = (Long) runtimeService.getVariable(executionId, InnerVariableEnum.PROCESS_ID.getName());
            //上面只是用来传递过来判断下，这里是子流程，还是要生产自己的bizKey 地~；
            businessKey = processDefinition.getKey()+"-"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MMdd-HHmmss"));
            //流程实例
            wfProcessDto = initProcess(processInstance, processDefinition, userName,
                    camundaParentId, parentId, businessKey,callActivityDefId, parentActInsId,null,null);
            /**
             * 这个需要特殊处理，非常重要-------------！！！！！！！！！！！！！
             * 需要把当前的流程的camunda实例和wfProcess实例ID，覆盖掉全局变量中；
             * 否则的话，如果当前子流程内部还有嵌套子流程的时，再传递过去传的是上一个父级的值，就有问题了；
             */
            variables.put(InnerVariableEnum.CAMUNDA_PROCESS_ID.getName(),wfProcessDto.getCamundaProcinsId());
        }else {
            String reason = null;
            if(resonObj != null && resonObj instanceof String){
                reason = (String)resonObj;
            }
            String bizpriority = null;
            if(bizpriorityObj != null && bizpriorityObj instanceof String){
                bizpriority = (String)bizpriorityObj;
            }
            wfProcessDto = initProcess(processInstance, processDefinition, userName,
                    null, null, businessKey,null, null,reason,bizpriority);
            /**
             * 这个需要特殊处理，非常重要-------------！！！！！！！！！！！！！
             * 需要把当前的流程的camunda实例和wfProcess实例ID，覆盖掉全局变量中；
             * 否则的话，如果当前子流程内部还有嵌套子流程的时，再传递过去传的是上一个父级的值，就有问题了；
             */
            variables.put(InnerVariableEnum.CAMUNDA_PROCESS_ID.getName(),executionId);
        }
        //处理过期时间
        dueDateHandle(execution.getProcessDefinitionId(),variables,nodeId);
        variables.put(InnerVariableEnum.PROCESS_ID.getName(),wfProcessDto.getId());
        //variables.remove(InnerVariableEnum.CALLACTIVITY_DEF_ID.getName());
        runtimeService.setVariables(executionId,variables);
    }

    /**
     * 过期日期处理
     * @param processDefinitionId
     * @param variables
     * @param camundaNodeDefId
     */
    private void dueDateHandle(String processDefinitionId, Map<String, Object> variables, String camundaNodeDefId){
        WfNodeDefVo entity = wfNodeDefService.getEntity(processDefinitionId, camundaNodeDefId);
        if(ObjectUtil.isEmpty(entity)){
            throw new IncloudException("未查询到节点");
        }
        if(ObjectUtil.isNotEmpty(entity.getDueDate())){
            Double dueDate = entity.getDueDate();
            double target = dueDate.doubleValue() * 24 * 3600 * 1000 + System.currentTimeMillis();
            DateTime date = DateUtil.date((long) target);
            variables.put(InnerVariableEnum.DUEDATE.getName(),date);
        }
    }

    /**
     * 启动流程，创建流程实例
     * @param instance
     * @param processDefinition
     * @param userName
     * @return
     */
    private WfProcessDto initProcess(DelegateExecution instance, ProcessDefinition processDefinition,String userName,
                                  String camundaParentId,Long parentId,String businessKey,String callActivityDefId,String parentActInsId,String reason,String bizpriority){

        ExecutionEntity executionEntity = (ExecutionEntity)instance;
        WfProcDefVo wfProcDefVo = wfProcdefService.getByDefIdAndVersion(processDefinition.getId(), processDefinition.getVersion());
        WfProcessDto wfProcess = new WfProcessDto();
        wfProcess.setBizKey(businessKey);
        wfProcess.setCurrentActivityId(executionEntity.getCurrentActivityId());
        ActivityInstance[] activityInstances = runtimeService.getActivityInstance(instance.getId()).getActivityInstances(executionEntity.getCurrentActivityId());
        if(ObjectUtil.isNotEmpty(activityInstances)){
            String activityName = Arrays.asList(activityInstances).stream().findFirst().get().getActivityName();
            wfProcess.setCurrentActivityName(activityName);
        }

        wfProcess.setCamundaProcdefId(instance.getProcessDefinitionId());
        wfProcess.setCamundaProcdefKey(processDefinition.getKey());
        wfProcess.setCamundaProcdefVersion(processDefinition.getVersion());
        wfProcess.setCamundaProcinsId(instance.getId());
        wfProcess.setProcdefId(wfProcDefVo.getId());
        wfProcess.setProcdefName(wfProcDefVo.getProcdefName());
        wfProcess.setProcdefTypeId(wfProcDefVo.getProcdefTypeId());
        wfProcess.setProcdefTypeName(wfProcDefVo.getProcdefTypeName());
        wfProcess.setProcinsName(processDefinition.getName());
        wfProcess.setReason(reason);
        wfProcess.setBizPriority(bizpriority);

        wfProcess.setIsCallActivity(ObjectUtil.isNotEmpty(parentId)?BooleanEnum.TRUE.getValue():BooleanEnum.FALSE.getValue());
        wfProcess.setIsClone(BooleanEnum.FALSE.getValue());
        wfProcess.setCamundaParentProcinsId(camundaParentId);
        wfProcess.setParentProcinsId(parentId);
        wfProcess.setCamundaCallActivityDefId(callActivityDefId);
        wfProcess.setCamundaParentActInsId(parentActInsId);

        MdmUserVo mdmUserVo = incloudCache.get(userName);
        wfProcess.setStarterId(mdmUserVo.getId());
        wfProcess.setStarterDeptId(mdmUserVo.getParentDeptId());
        wfProcess.setStarterOrgId(mdmUserVo.getParentOrgId());
        wfProcess.setStarterDeptName(mdmUserVo.getParentDeptName());
        wfProcess.setStarterName(mdmUserVo.getUserNameCh());
        wfProcess.setStarterOrgName(mdmUserVo.getParentOrgName());

        wfProcess.setStartTime(LocalDateTime.now());
        wfProcess.setCreateTime(LocalDateTime.now());
        wfProcess.setState(WfProcessEnum.RUNNING.getType());
        wfProcess.setApplyTime(LocalDateTime.now());
        log.info("流程实例为：{}", JSONUtil.toJsonStr(wfProcess));

        wfProcessService.save(wfProcess);
        log.info("流程实例保存成功！");
        return wfProcess;
    }
}
