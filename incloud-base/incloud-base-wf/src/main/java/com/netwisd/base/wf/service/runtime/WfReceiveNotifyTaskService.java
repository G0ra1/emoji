package com.netwisd.base.wf.service.runtime;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.common.vo.wf.WfReceiveNotifyTaskVo;
import com.netwisd.base.wf.dto.WfReceiveNotifyTaskDto;
import com.netwisd.base.wf.entity.WfReceiveNotifyTask;

import java.util.List;

/**
 * @Description 我收到的知会 功能描述...
 * @author 云数网讯 XHL
 * @date 2022-02-14 09:39:14
 */
public interface WfReceiveNotifyTaskService extends IService<WfReceiveNotifyTask> {
    Page list(WfReceiveNotifyTaskDto wfReceiveNotifyTaskDto);
    Page lists(WfReceiveNotifyTaskDto wfReceiveNotifyTaskDto);
    WfReceiveNotifyTaskVo get(Long id);
    Boolean save(WfReceiveNotifyTaskDto wfReceiveNotifyTaskDto);
    Boolean update(WfReceiveNotifyTaskDto wfReceiveNotifyTaskDto);
    Boolean updateStateByProInsId(String processInstanceId,Integer state);
    Boolean delete(Long id);
    Boolean claimNotifyId(Long claimNotifyId, String userId);
    Boolean deleteProcinsId(String processInstanceId);
    Boolean delNotifyByProInsAndTaskId(String processInstanceId,String camundaTaskId);
    List getList(String camundaTaskId);
    //根据流程定义id 和节点id 查询 当前节点所有知会信息
    List<WfReceiveNotifyTask> getNotifyTaskByProcinsIdAndNodeKey(String camundaProcinsId , String camundaNodeKey);

    //管理员查询接口
    Page receiveNotifyListForAd(WfReceiveNotifyTaskDto wfReceiveNotifyTaskDto);
}
