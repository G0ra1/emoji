package com.netwisd.base.wf.service.impl.runtime;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.wf.constants.BooleanEnum;
import com.netwisd.base.wf.constants.InnerVariableEnum;
import com.netwisd.base.wf.service.WfProcdefService;
import com.netwisd.base.wf.service.runtime.*;
import com.netwisd.base.wf.starter.dto.WfDto;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.base.wf.vo.WfProcDefVo;
import com.netwisd.common.core.exception.IncloudException;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zouliming@netwisd.com
 * @description
 * @date 2021/12/13 10:38
 */
@Service
@Slf4j
public class WfEngineStartServiceImpl implements WfEngineStartService {
    @Autowired
    WfProcdefService wfProcdefService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    WfEngineClaimService wfEngineClaimService;

    @Autowired
    WfGetDtoService wfGetDtoService;

    @Override
    public WfDto startProcess(WfEngineDto.StartDto startDto) {
        String procdefKey = startDto.getCamundaProcdefKey();
        //根据流程定义key 查询出当前激活版本
        WfProcDefVo wfProcDefVo = wfProcdefService.queryCurrentVerByCamundaDefKey(procdefKey);
        if(ObjectUtil.isEmpty(wfProcDefVo)) {
            throw new IncloudException("没有查询出当前激活版本。");
        }
        Integer version = wfProcDefVo.getProcdefVersion();
        String userId = AppUserUtil.getLoginAppUserAndCheck().getUserName();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(procdefKey)
                .processDefinitionVersion(version)
                .singleResult();
        if(processDefinition == null){
            throw new IncloudException("流程定义未查询到！");
        }
        Map<String, Object> variables = new HashMap();
        variables.put(InnerVariableEnum.ASSIGNEE.getName(), userId);
        variables.put(InnerVariableEnum.REASON.getName(), startDto.getReason());
        variables.put(InnerVariableEnum.DRAFT.getName(), BooleanEnum.TRUE.getValue());
        variables.put(InnerVariableEnum.BIZPRIORITY.getName(), startDto.getBizPriority());//任务优先级

        String bizKey = "wf-"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MMdd-HHmmss"));
        ProcessInstance instance = runtimeService.startProcessInstanceById(processDefinition.getId(),bizKey,variables);
        String instanceId = instance.getId();
        log.info("instance实例id:{}，instance实例key:{} ",instanceId,instance.getBusinessKey());

        //启动流程后，自动调用创建待办的事件后，就可以干掉草稿标识了，免得后面污染变量逻辑；
        runtimeService.removeVariable(instanceId,InnerVariableEnum.DRAFT.getName());

        // 返回一个任务实例ID，因为我们约定了第一个任务节点就是发起人，所以在流程启动时，不需要考虑会签或多实例的情况；
        Task task = taskService.createTaskQuery().processInstanceId(instanceId)
                .taskCandidateUser(userId).singleResult();
        if(task == null){
            throw new IncloudException("查询任务失败！");
        }
        wfEngineClaimService.claimProcess(task.getId());
        return wfGetDtoService.returnData(task.getId(),instanceId);
    }
}