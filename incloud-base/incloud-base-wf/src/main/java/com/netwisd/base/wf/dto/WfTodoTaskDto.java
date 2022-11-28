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
 * @Description 待办任务 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-08-06 16:38:05
 */
@Data
@ApiModel(value = "待办任务 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WfTodoTaskDto extends IDto{
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
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="流程实例ID")
    @Valid(length = 20) 
    private Long procinsId;
    /**
     * proins_name
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
     * node_type
     * 节点类型
     */
    @ApiModelProperty(value="节点类型")
    private Integer nodeType;
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
     * descrption
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
     * assigneeName
     * 任务办理人名称
     */
    @ApiModelProperty(value="任务办理人名称")
    private String assigneeName;
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
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="流程定义ID")
    @Valid(length = 20) 
    private Long procdefId;
    /**
     * procdef_name
     * 流程定义名称
     */
    @ApiModelProperty(value="流程定义名称")
    private String procdefName;
    /**
     * procdef_type_id
     * 流程分类ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="流程分类ID")
    @Valid(length = 20) 
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
     * 任务挂起状态
     */
    @ApiModelProperty(value="任务挂起状态")
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
     * candidates
     * 候选人集合
     */
    @ApiModelProperty(value="候选人集合")
    private String candidates;
    /**
     * is_draft
     * 是否草稿数据
     */
    @ApiModelProperty(value="是否草稿数据")
    private Integer isDraft;
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
    /**
     * apply_time
     * 申请时间
     */
    @ApiModelProperty(value="申请时间")
    private LocalDateTime applyTime;

    /**
     * biz_priority
     * 业务优先级
     */
    @ApiModelProperty(value="优先级")
    private String bizPriority;
}
