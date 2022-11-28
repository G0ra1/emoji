package com.netwisd.base.common.center.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 待办任务  功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-06-22 14:48:16
 */
@Data
@ApiModel(value = "待办任务  Vo")
public class TodoVo extends IVo{

    /**
     * sys_code
     * 子系统
     */
    
    @ApiModelProperty(value="子系统")
    private String sysCode;
    /**
     * sys_name
     * 子系统
     */
    
    @ApiModelProperty(value="子系统")
    private String sysName;
    /**
     * task_source
     * 任务来源
     */
    
    @ApiModelProperty(value="任务来源")
    private String taskSource;
    /**
     * task_start_userid
     * 任务发起人
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="任务发起人")
    private Long taskStartUserid;
    /**
     * task_start_user_name
     * 任务发起人
     */
    
    @ApiModelProperty(value="任务发起人")
    private String taskStartUserName;
    /**
     * task_start_time
     * 任务发起时间
     */
    
    @ApiModelProperty(value="任务发起时间")
    private LocalDateTime taskStartTime;
    /**
     * task_end_time
     * 任务结束时间
     */
    
    @ApiModelProperty(value="任务结束时间")
    private LocalDateTime taskEndTime;
    /**
     * task_real_start_time
     * 任务实际开始时间
     */
    
    @ApiModelProperty(value="任务实际开始时间")
    private String taskRealStartTime;
    /**
     * task_real_end_time
     * 任务实际结束时间
     */
    
    @ApiModelProperty(value="任务实际结束时间")
    private LocalDateTime taskRealEndTime;
    /**
     * task_hours
     * 办理时长
     */
    
    @ApiModelProperty(value="办理时长")
    private Long taskHours;
    /**
     * task_name
     * 任务节点名称
     */
    
    @ApiModelProperty(value="任务节点名称")
    private String taskName;
    /**
     * task_todo_person_type
     * 任务办理人类型 1人2角色3职位4岗位
     */
    
    @ApiModelProperty(value="任务办理人类型 1人2角色3职位4岗位")
    private Integer taskTodoPersonType;
    /**
     * task_todo_type_id
     * 任务办理类型id
     */

    @ApiModelProperty(value="任务办理类型id")
    private Long taskTodoTypeId;
    /**
     * task_todo_person_id
     * 任务办理人
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="任务办理人")
    private Long taskTodoPersonId;
    /**
     * task_todo_person_name
     * 任务办理人
     */
    
    @ApiModelProperty(value="任务办理人")
    private String taskTodoPersonName;
    /**
     * task_todo_org_name
     * 任务办理机构
     */
    
    @ApiModelProperty(value="任务办理机构")
    private String taskTodoOrgName;
    /**
     * task_todo_org_id
     * 任务办理机构
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="任务办理机构")
    private Long taskTodoOrgId;
    /**
     * task_todo_dept_name
     * 任务办理部门
     */
    
    @ApiModelProperty(value="任务办理部门")
    private String taskTodoDeptName;
    /**
     * task_todo_dept_id
     * 任务办理部门
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="任务办理部门")
    private Long taskTodoDeptId;
    /**
     * task_inst_id
     * 任务实例Id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="任务实例Id")
    private Long taskInstId;
    /**
     * parent_task_node_id
     * 父任务节点Id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="父任务节点Id")
    private Long parentTaskNodeId;
    /**
     * task_state
     * 任务状态 0.未开始1处理中2完成3.超期
     */
    
    @ApiModelProperty(value="任务状态 0.未开始1处理中2完成3.超期")
    private Integer taskState;
    /**
     * task_progress
     * 任务进度 每个任务节点返回的状态
     */
    
    @ApiModelProperty(value="任务进度 每个任务节点返回的状态")
    private String taskProgress;
    /**
     * apply_code
     * 申请单号
     */
    
    @ApiModelProperty(value="申请单号")
    private String applyCode;
    /**
     * apply_wf_id
     * 申请流程id
     */
    
    @ApiModelProperty(value="申请流程id")
    private String applyWfId;
    /**
     * apply_reason
     * 申请说明
     */
    
    @ApiModelProperty(value="申请说明")
    private String applyReason;

}
