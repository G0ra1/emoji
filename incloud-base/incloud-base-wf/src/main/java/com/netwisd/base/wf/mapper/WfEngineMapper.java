package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Description 操作相关camunda的表
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-08-13 11:20:23
 */
@Mapper
public interface WfEngineMapper<T> extends BaseMapper<T> {

    @Update("update incloud_base_wf_act_hi_actinst set PROC_DEF_ID_ = '${processDefinitionId}' where PROC_INST_ID_ = '${processInstanceId}'")
    void updateActInst(@Param("processDefinitionId")String processDefinitionId,@Param("processInstanceId")String processInstanceId);

    @Update("update incloud_base_wf_act_hi_detail set PROC_DEF_ID_ = '${processDefinitionId}' where PROC_INST_ID_ = '${processInstanceId}'")
    void updateDetail(@Param("processDefinitionId")String processDefinitionId,@Param("processInstanceId")String processInstanceId);

    @Update("update incloud_base_wf_act_hi_identitylink set PROC_DEF_ID_ = '${processDefinitionId}' where ROOT_PROC_INST_ID_ = '${processInstanceId}'")
    void updateHiIdentitylink(@Param("processDefinitionId")String processDefinitionId,@Param("processInstanceId")String processInstanceId);

    @Update("update incloud_base_wf_act_hi_procinst set PROC_DEF_ID_ = '${processDefinitionId}' where ID_ = '${processInstanceId}'")
    void updateProcinst(@Param("processDefinitionId")String processDefinitionId,@Param("processInstanceId")String processInstanceId);

    @Update("update incloud_base_wf_act_hi_taskinst set PROC_DEF_ID_ = '${processDefinitionId}' where PROC_INST_ID_ = '${processInstanceId}'")
    void updateTaskinst(@Param("processDefinitionId")String processDefinitionId,@Param("processInstanceId")String processInstanceId);

    @Update("update incloud_base_wf_act_hi_varinst set PROC_DEF_ID_ = '${processDefinitionId}' where PROC_INST_ID_ = '${processInstanceId}'")
    void updateVarinst(@Param("processDefinitionId")String processDefinitionId,@Param("processInstanceId")String processInstanceId);

    @Update("update incloud_base_wf_act_ru_execution set PROC_DEF_ID_ = '${processDefinitionId}' where PROC_INST_ID_ = '${processInstanceId}'")
    void updateExecution(@Param("processDefinitionId")String processDefinitionId,@Param("processInstanceId")String processInstanceId);

    @Update("update incloud_base_wf_act_ru_task set PROC_DEF_ID_ = '${processDefinitionId}' where PROC_INST_ID_ = '${processInstanceId}'")
    void updateTask(@Param("processDefinitionId")String processDefinitionId,@Param("processInstanceId")String processInstanceId);

    @Update("update incloud_base_wf_act_ru_identitylink set PROC_DEF_ID_ = '${processDefinitionId}' where TASK_ID_ in '${taskIdList}'")
    void updateRuIdentitylink(@Param("processDefinitionId")String processDefinitionId,@Param("taskIdList") List<String> taskIdList);
}
