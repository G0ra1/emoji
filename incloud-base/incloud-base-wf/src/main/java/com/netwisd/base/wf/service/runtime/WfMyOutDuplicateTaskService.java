package com.netwisd.base.wf.service.runtime;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.wf.entity.WfMyOutDuplicateTask;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfMyOutDuplicateTaskDto;
import com.netwisd.base.common.vo.wf.WfMyOutDuplicateTaskVo;
import com.netwisd.base.wf.entity.WfProcDef;
import org.camunda.bpm.engine.repository.ProcessDefinition;

/**
 * @Description 我发起的传阅 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-09-18 16:16:11
 */
public interface WfMyOutDuplicateTaskService extends IService<WfMyOutDuplicateTask> {
    Page list(WfMyOutDuplicateTaskDto wfMyOutDuplicateTaskDto);
    Page lists(WfMyOutDuplicateTaskDto wfMyOutDuplicateTaskDto);
    WfMyOutDuplicateTaskVo get(Long id);
    Boolean save(WfMyOutDuplicateTaskDto wfMyOutDuplicateTaskDto);
    Boolean update(WfMyOutDuplicateTaskDto wfMyOutDuplicateTaskDto);
    Boolean delete(Long id);
    Boolean updateStateByProInsId(String processInstanceId,Integer state);
    Boolean deleteProcinsId(String processInstanceId);
    Boolean updateDefId(ProcessDefinition processDefinitionNew, WfProcDef wfProcDefNew, String processInstanceId);

    Page outDuplicateListForAd(WfMyOutDuplicateTaskDto wfMyOutDuplicateTaskDto);
}
