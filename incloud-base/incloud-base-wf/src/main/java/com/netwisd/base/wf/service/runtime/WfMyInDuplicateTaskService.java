package com.netwisd.base.wf.service.runtime;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.wf.dto.WfMyInDuplicateTaskDto;
import com.netwisd.base.wf.entity.WfMyInDuplicateTask;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.vo.wf.WfMyInDuplicateTaskVo;
import com.netwisd.base.wf.entity.WfProcDef;
import org.camunda.bpm.engine.repository.ProcessDefinition;

import java.util.List;

/**
 * @Description 传阅任务 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-08-13 09:39:14
 */
public interface WfMyInDuplicateTaskService extends IService<WfMyInDuplicateTask> {
    Page list(WfMyInDuplicateTaskDto wfMyInDuplicateTaskDto);
    Page lists(WfMyInDuplicateTaskDto wfMyInDuplicateTaskDto);
    WfMyInDuplicateTaskVo get(Long id);
    Boolean save(WfMyInDuplicateTaskDto wfMyInDuplicateTaskDto);
    Boolean update(WfMyInDuplicateTaskDto wfMyInDuplicateTaskDto);
    Boolean updateStateByProInsId(String processInstanceId,Integer state);
    Boolean delete(Long id);
    Boolean claimDuplicate(Long duplicateTaskID, String userId);
    Boolean deleteProcinsId(String processInstanceId);
    Boolean delDuplicateByProInsAndTaskId(String processInstanceId,String camundaTaskId);
    List getList(String camundaTaskId);
    //被传阅人 设置已阅 并且添加意见
    Boolean replyDescription(Long duplicateTaskID, String description);
    Boolean updateDefId(ProcessDefinition processDefinitionNew, WfProcDef wfProcDefNew, String processInstanceId);
    //管理员查询页面
    Page inDuplicateListForAd(WfMyInDuplicateTaskDto wfMyInDuplicateTaskDto);
}
