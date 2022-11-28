package com.netwisd.base.wf.service.impl.runtime;

import com.netwisd.base.wf.service.runtime.*;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zouliming@netwisd.com
 * @description
 * @date 2021/12/9 14:52
 */
@Service
@Slf4j
public class WfEngineDelServiceImpl implements WfEngineDelService {

    @Autowired
    WfProcessLogService wfProcessLogService;

    @Autowired
    WfMyInDuplicateTaskService wfMyInDuplicateTaskService;

    @Autowired
    WfMyOutDuplicateTaskService wfMyOutDuplicateTaskService;

    @Autowired
    WfTodoTaskService wfTodoTaskService;

    @Autowired
    WfDoneTaskService wfDoneTaskService;


    @Override
    public void delWfTodoTask(Task task){
        wfTodoTaskService.delete(task.getId());
        log.info("提交完成并删除当前人待办！");
    }

    @Override
    public void delWfDoneTask(String tasId){
        wfDoneTaskService.delete(tasId);
        log.info("提交完成并删除当前人已办！");
    }

    @Override
    public void delDuplicateByProInsAndTaskId(String processInstanceId,String camundaTaskId){
        wfMyInDuplicateTaskService.delDuplicateByProInsAndTaskId(processInstanceId,camundaTaskId);
        log.info("提交完成并删除当当前节点所有其他传阅！");
    }

    @Override
    public void delWfTodoTaskByProInsAdnTaskId(String processInstanceId,String taskId){
        wfTodoTaskService.delWfTodoTaskByProInsAdnTaskId(processInstanceId,taskId);
        log.info("提交完成并删除当当前节点所有其他待办！");
    }

    @Override
    public void delWfProcessLogByProInsAdnTaskId(String processInstanceId,String camundaTaskId,Integer logType){
        wfProcessLogService.delWfTodoTaskByProInsAndTaskId(processInstanceId,camundaTaskId,logType);
        log.info("提交完成并删除当当前节点所有其他待办对应的日志！");
    }
}
