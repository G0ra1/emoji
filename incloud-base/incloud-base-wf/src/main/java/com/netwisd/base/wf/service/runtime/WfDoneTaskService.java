package com.netwisd.base.wf.service.runtime;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.wf.dto.WfTodoTaskDto;
import com.netwisd.base.wf.entity.WfDoneTask;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfDoneTaskDto;
import com.netwisd.base.common.vo.wf.WfDoneTaskVo;
import com.netwisd.base.wf.entity.WfProcDef;
import org.camunda.bpm.engine.repository.ProcessDefinition;

import java.util.List;

/**
 * @Description 已办任务 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-08-13 11:20:23
 */
public interface WfDoneTaskService extends IService<WfDoneTask> {
    Page list(WfDoneTaskDto wfDoneTaskDto);
    Page lists(WfDoneTaskDto wfDoneTaskDto);
    WfDoneTaskVo get(Long id);
    Boolean save(WfDoneTaskDto wfDoneTaskDto);
    Boolean update(WfDoneTaskDto wfDoneTaskDto);
    Boolean update(WfDoneTask wfDoneTask);
    Boolean updateStateByProInsId(String processInstanceId,Integer state);
    Boolean delete(Long id);
    Boolean delete(String camundaTaskId);
    Boolean deleteProcinsId(String processInstanceId);
    WfDoneTask get(String camundaTaskId);
    List<WfDoneTask> getList(Long procinsId);
    Boolean delWfTodoTaskByProInsAdnNodeKey(String procInstanceId,String nodeKey);
    WfDoneTaskVo getFirstOne(String camundaProcInsId);
    Boolean updateDefId(ProcessDefinition processDefinitionNew, WfProcDef wfProcDefNew, String processInstanceId);

    //查询 我发起的流程
    Page myDraftList(WfDoneTaskDto wfDoneTaskDto);

    //查询 我发起的流程-管理员
    Page myDraftListForAd(WfDoneTaskDto wfDoneTaskDto);

    //查询 草稿-管理员
    Page doneListForAd(WfDoneTaskDto wfDoneTaskDto);
}
