package com.netwisd.base.wf.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.util.IdTypeDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * @Description 已办任务 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-08-13 11:20:23
 */
@Data
@ApiModel(value = "已办任务 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WfDoneTaskDto extends IDto{
    /**
     * camunda_procdef_id
     * camunda流程定义ID
     */
    @ApiModelProperty(value="camunda流程定义ID")
    @Valid(length = 64) 
    private String camundaProcdefId;
    /**
     * camunda_procdef_key
     * camunda流程定义key
     */
    @ApiModelProperty(value="camunda流程定义key")
    @Valid(length = 50) 
    private String camundaProcdefKey;
    /**
     * camunda_procdef_version
     * camunda流程定义版本
     */
    @ApiModelProperty(value="camunda流程定义版本")
    @Valid(length = 2) 
    private Integer camundaProcdefVersion;
    /**
     * camunda_procins_id
     * camunda流程实例ID
     */
    @ApiModelProperty(value="camunda流程实例ID")
    @Valid(length = 64) 
    private String camundaProcinsId;
    /**
     * camunda_task_id
     * camunda任务ID
     */
    @ApiModelProperty(value="camunda任务ID")
    @Valid(length = 64) 
    private String camundaTaskId;
    /**
     * camunda_exection_id
     * camunda exection实例ID
     */
    @ApiModelProperty(value="camunda exection实例ID")
    @Valid(length = 64) 
    private String camundaExectionId;
    /**
     * camunda_act_ins_id
     * camunda节点实例id
     */
    @ApiModelProperty(value="camunda节点实例id")
    @Valid(length = 64)
    private String camundaActInsId;
    /**
     * camunda_parent_proc_ins_id
     * camunda 父流程实例ID
     */
    @ApiModelProperty(value="camunda 父流程实例ID")
    private String camundaParentProcinsId;
    /**
     * parent_proc_ins_id
     * 父流程实例ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="父流程实例ID")
    private Long parentProcinsId;
    /**
     * procins_id
     * 流程实例ID
     */
    @ApiModelProperty(value="流程实例ID")
    @Valid(length = 20)
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long procinsId;
    /**
     * procins_name
     * 流程实例名称
     */
    @ApiModelProperty(value="流程实例名称")
    @Valid(length = 255) 
    private String procinsName;
    /**
     * task_name
     * 任务名称
     */
    @ApiModelProperty(value="任务名称")
    private String taskName;
    /**
     * node_key
     * 节点定义key，ID
     */
    @ApiModelProperty(value="节点定义key，ID")
    @Valid(length = 50) 
    private String nodeKey;
    /**
     * node_name
     * 节点名称
     */
    @ApiModelProperty(value="节点名称")
    private String nodeName;
    /**
     * decision
     * 流程决策标识
     */
    @ApiModelProperty(value="流程决策标识")
    private Integer decision;
    /**
     * isAgree
     * 是否同意
     */
    @ApiModelProperty(value="是否同意")
    @Valid
    private Integer isAgree;
    /**
     * description
     * 处理意见
     */
    @ApiModelProperty(value="处理意见")
    private String description;
    /**
     * ownner
     * 任务拥有人
     */
    @ApiModelProperty(value="任务拥有人")
    private String ownner;
    /**
     * assignee
     * 任务办理人
     */
    @ApiModelProperty(value="任务办理人")
    private String assignee;
    /**
     * priority
     * 重要程度
     */
    @ApiModelProperty(value="重要程度")
    private Integer priority;
    /**
     * due_date
     * 任务过期日期
     */
    @ApiModelProperty(value="任务过期日期")
    private LocalDateTime dueDate;
    /**
     * procdef_id
     * 流程定义ID
     */
    @ApiModelProperty(value="流程定义ID")
    @Valid(length = 20)
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long procdefId;
    /**
     * procdef_name
     * 流程定义名称
     */
    @ApiModelProperty(value="流程定义名称")
    @Valid(length = 50) 
    private String procdefName;
    /**
     * procdef_type_id
     * 流程分类ID
     */
    @ApiModelProperty(value="流程分类ID")
    @Valid(length = 20)
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long procdefTypeId;
    /**
     * procdef_type_name
     * 流程分类名称
     */
    @ApiModelProperty(value="流程分类名称")
    @Valid(length = 50) 
    private String procdefTypeName;
    /**
     * cliam_time
     * 签收时间
     */
    @ApiModelProperty(value="签收时间")
    private LocalDateTime cliamTime;
    /**
     * state
     * 任务状态
     */
    @ApiModelProperty(value="任务状态")
    private Integer state;
    /**
     * reason
     * 事由
     */
    @ApiModelProperty(value="事由")
    private String reason;
    /**
     * apply_time
     * 申请开始时间
     */
    @ApiModelProperty(value="申请开始时间")
    private LocalDateTime applyStartTime;
    /**
     * apply_time
     * 申请结束时间
     */
    @ApiModelProperty(value="申请结束时间")
    private LocalDateTime applyEndTime;
    /**
     * starter_id
     * 开始起草人ID
     */
    @ApiModelProperty(value="开始起草人ID")
    @Valid(length = 20)
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long starterId;

    /**
     * starter_name
     * 起草人名称
     */
    @ApiModelProperty(value="起草人名称")
    @Valid(length = 50)
    private String starterName;

    /**
     * starter_dept_id
     * 起草人部门ID
     */
    @ApiModelProperty(value="起草人部门ID")
    @Valid(length = 32)
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long starterDeptId;

    /**
     * starter_dept_name
     * 起草人部门名称
     */
    @ApiModelProperty(value="起草人部门名称")
    @Valid(length = 50)
    private String starterDeptName;

    /**
     * starter_org_id
     * 起草人机构ID
     */
    @ApiModelProperty(value="起草人机构ID")
    @Valid(length = 20)
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long starterOrgId;

    /**
     * starter_org_name
     * 起草人机构名称
     */
    @ApiModelProperty(value="起草人机构名称")
    @Valid(length = 50)
    private String starterOrgName;
    /**
     * biz_key
     * 业务key
     */
    @ApiModelProperty(value="业务key")
    private String bizKey;
    /**
     * currentUserId
     * 当前用户ID
     */
    @ApiModelProperty(value="当前用户ID")
    private String currentUserId;
    /**
     * is_callActivity
     * 是否子流程
     */
    @ApiModelProperty(value="是否子流程")
    private Integer isCallActivity;
    /**
     * isClone
     * 是否clone
     */
    @ApiModelProperty(value="是否clone")
    private Integer isClone = 0;
    //2020.11.6新加字段
    /**
     * is_cloned_by_procdef_id
     * 如果被clone的话，记录被clone的流程定义ID
     */
    @ApiModelProperty(value="如果被clone的话，记录被clone的流程定义ID")
    private String beClonedFromCamundaProcdefId;
    /**
     * current_activity_id
     * 当前活动ID
     */
    @ApiModelProperty(value="当前活动ID")
    private String currentActivityId;
    /**
     * current_activity_name
     * 当前活动名称
     */
    @ApiModelProperty(value="当前活动名称")
    private String currentActivityName;
    /**
     * current_activity_assignee
     * 当前活动办理人
     */
    @ApiModelProperty(value="当前活动办理人")
    private String currentActivityAssignee;
    /**
     * current_activity_assignee_name
     * 当前活动办理人名称
     */
    @ApiModelProperty(value="当前活动办理人名称")
    private String currentActivityAssigneeName;
    /**
     * start_time
     * 开始时间
     */
    @ApiModelProperty(value="开始时间")
    private LocalDateTime startTime;
    /**
     * end_time
     * 结束时间
     */
    @ApiModelProperty(value="结束时间")
    private LocalDateTime endTime;
    /**
     * biz_priority
     * 业务优先级
     */
    @ApiModelProperty(value="优先级")
    private String bizPriority;
}
