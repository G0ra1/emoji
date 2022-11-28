package com.netwisd.base.wf.service.runtime;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.common.vo.wf.WfProcessVo;
import com.netwisd.base.wf.entity.WfProcDef;
import com.netwisd.base.wf.entity.WfProcess;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfProcessDto;
import org.camunda.bpm.engine.repository.ProcessDefinition;

import java.util.List;

/**
 * @Description 流程实例 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-08-06 13:49:51
 */
public interface WfProcessService extends IService<WfProcess> {
    Page list(WfProcessDto wfProcessDto);
    Page lists(WfProcessDto wfProcessDto);
    WfProcessVo get(Long id);
    Boolean save(WfProcessDto wfProcessDto);
    Boolean update(WfProcessDto wfProcessDto);
    Boolean update(WfProcess wfProcess);
    Boolean updateStateByProInsId(String processInstanceId,Integer state);
    Boolean delete(Long id);
    Boolean delete(String processInstanceId);
    WfProcess get(String camundaProcessId);
    WfProcess getAndCheck(String camundaProcessId);
    Boolean updateDefId(ProcessDefinition processDefinitionNew, WfProcDef wfProcDefNew, String processInstanceId);
    void checkProcessState(String processId);
    Boolean getIsExistInstProcByCamundaProcdefId(String camundaProcdefId);
}
