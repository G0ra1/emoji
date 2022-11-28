package com.netwisd.base.wf.service.runtime;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.wf.entity.WfProcessLog;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfProcessLogDto;
import com.netwisd.base.wf.vo.WfProcessLogVo;

import java.util.List;

/**
 * @Description 流程日志 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-08-14 17:42:43
 */
public interface WfProcessLogService extends IService<WfProcessLog> {
    Page list(WfProcessLogDto wfProcessLogDto);
    Page lists(WfProcessLogDto wfProcessLogDto);
    WfProcessLogVo get(Long id);
    Boolean save(WfProcessLogDto wfProcessLogDto);
    Boolean update(WfProcessLogDto wfProcessLogDto);
    Boolean update(WfProcessLog wfProcessLog);
    Boolean delete(Long id);
    Boolean delete(String processInstanceId);
    Boolean delWfTodoTaskByProInsAndTaskId(String procInstanceId,String camundaTaskId,Integer logType);
    WfProcessLog get(String camundaTaskId,Integer logType);
    WfProcessLog getByCurrentActInsId(String currentActInsId,Integer logType);
    List<WfProcessLogVo> getList(String camundaProcinsId,boolean isAll,boolean isInCludeMulti);
    WfProcessLogVo getLastNodeInfo(String camundaProcinsId,String camundaDefKey);
    WfProcessLogVo getLastRejectNodeInfo(String camundaProcinsId,String camundaDefKey);
    List<WfProcessLogVo> getRejectAllList(String camundaProcinsId);//驳回日志
    List<WfProcessLogVo> getRejectAllToList(String camundaProcinsId);//驳回列表

    /**
     * 根据父流程实例id 和 callActivityId 获取流程日志
     * @param camundaProcinsId
     * @param callActivityId
     * @return
     */
    List<WfProcessLogVo> getLogList(String camundaProcinsId, String callActivityId);

    List<WfProcessLogVo> getchildProcessLogActInsId(String camundaCurrentActInsId);//获取所有的流程日志

    /**
     * 判断是否是驳回的草稿状态
     * @param camundaProcinsId
     * @return
     */
    boolean isDraftByLog(String camundaProcinsId);

    /**
     * 根据父流程实例id 和 nodeId 获取流程日志
     * @param camundaProcinsId 流程实例id
     * @param nodeId 节点id
     * @return
     */
    List<WfProcessLogVo> getLogsByInsIdAndNodeId(String camundaProcinsId, String nodeId);
}
