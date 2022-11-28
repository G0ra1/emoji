package com.netwisd.base.wf.service.runtime;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.wf.entity.WfProcDef;
import com.netwisd.base.wf.entity.WfTodoTask;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfTodoTaskDto;
import com.netwisd.base.common.vo.wf.WfTodoTaskVo;
import com.netwisd.common.core.util.Result;
import org.camunda.bpm.engine.repository.ProcessDefinition;

import java.util.List;

/**
 * @Description 待办任务 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-08-06 16:38:05
 */
public interface WfTodoTaskService extends IService<WfTodoTask> {
    Page list(WfTodoTaskDto wfTodoTaskDto);
    Page draftList(WfTodoTaskDto wfTodoTaskDto);
    Page lists(WfTodoTaskDto wfTodoTaskDto);
    WfTodoTaskVo get(Long id);
    Boolean save(WfTodoTaskDto wfTodoTaskDto);
    Boolean update(WfTodoTask wfTodoTask);
    Boolean updateStateByProInsId(String processInstanceId,Integer state);
    Boolean delete(Long id);
    Boolean delete(String camundaTaskId);
    Boolean deleteProcinsId(String processInstanceId);
    Boolean delWfTodoTaskByProInsAdnNodeKey(String procInstanceId,String nodeKey);
    Boolean delWfTodoTaskByProInsAdnTaskId(String procInstanceId,String taskId);
    WfTodoTask get(String camundaTaskId);
    WfTodoTask getAndCheck(String camundaTaskId);
    WfTodoTaskVo getFirstOne(String camundaProcInsId);
    List<WfTodoTaskVo> getTodoList(String camundaProcInsId);
    Boolean updateDefId(ProcessDefinition processDefinitionNew, WfProcDef wfProcDefNew, String processInstanceId);
    //根据身份证号待办数量
    Result queryTodoTaskNum(String idCard);
    Page draftListForAd(WfTodoTaskDto wfTodoTaskDto);
    Page todoListForAd(WfTodoTaskDto wfTodoTaskDto);
    //修改办理人
    Boolean updateAssigneeByTodoId(String id,String assignees);
    //根据身份证号 以及路程定义id查询 待办数据量
    Result queryTodoTaskNumByCmdProcDefId(String idCard,String camundaProcdefId);

    //查询当前登录人和流程定义key 查询待办数量
    Result queryTodokNumByCondition(String camundaProcdefKey);
}
