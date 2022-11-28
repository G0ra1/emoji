package com.netwisd.base.common.vo.wf;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description 传阅任务 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-08-13 09:39:14
 */
@Data
@ApiModel(value = "传阅任务 Vo")
public class WfMyInDuplicateTaskVo extends IVo{

    /**
     * camunda_procdef_id
     * camunda流程定义ID
     */
    @ApiModelProperty(value="camunda流程定义ID")
    private String camundaProcdefId;
    /**
     * camunda_procdef_key
     * camunda流程定义key
     */
    @ApiModelProperty(value="camunda流程定义key")
    private String camundaProcdefKey;
    /**
     * camunda_procdef_version
     * camunda流程定义版本
     */
    @ApiModelProperty(value="camunda流程定义版本")
    private Integer camundaProcdefVersion;
    /**
     * camunda_procins_id
     * camunda流程实例ID
     */
    @ApiModelProperty(value="camunda流程实例ID")
    private String camundaProcinsId;
    /**
     * camunda_task_id
     * camunda流程任务ID——来自传阅发起任务
     */
    @ApiModelProperty(value="camunda流程任务ID——来自传阅发起任务")
    private String camundaTaskId;
    /**
     * camunda_node_key
     * camunda流程节点key——来自传阅发起任务
     */
    @ApiModelProperty(value="camunda流程节点key——来自传阅发起任务")
    private String camundaNodeKey;
    /**
     * camunda_node_name
     * camunda流程节点name——来自传阅发起任务
     */
    @ApiModelProperty(value="camunda流程节点name——来自传阅发起任务")
    private String camundaNodeName;
    /**
     * camunda_node_type
     * 发起传阅的任务节点类型
     */
    @ApiModelProperty(value="发起传阅的任务节点类型")
    private Integer camundaNodeType;
    /**
     * procins_id
     * 流程实例ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="流程实例ID")
    private Long procinsId;
    /**
     * proins_name
     * 流程实例名称
     */
    @ApiModelProperty(value="流程实例名称")
    private String procinsName;
    /**
     * ownner
     * 发起传阅人
     */
    @ApiModelProperty(value="发起传阅人")
    private String ownner;
    /**
     * ownner_name
     * 发起传阅人名称
     */
    @ApiModelProperty(value="发起传阅人名称")
    private String ownnerName;
    /**
     * assignee
     * 传阅办理人
     */
    @ApiModelProperty(value="传阅办理人")
    private String assignee;
    /**
     * assignee_name
     * 传阅办理人名称
     */
    @ApiModelProperty(value="传阅办理人名称")
    private String assigneeName;
    /**
     * procdef_id
     * 流程定义ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="流程定义ID")
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
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="流程分类ID")
    private Long procdefTypeId;
    /**
     * procdef_type_name
     * 流程分类名称
     */
    @ApiModelProperty(value="流程分类名称")
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
     * 申请时间
     */
    @ApiModelProperty(value="申请时间")
    private LocalDateTime applyTime;
    /**
     * starter_id
     * 开始起草人ID
     */
    @ApiModelProperty(value="开始起草人ID")
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long starterId;
    /**
     * starter_name
     * 起草人名称
     */
    @ApiModelProperty(value="起草人名称")
    private String starterName;
    /**
     * starter_dept_id
     * 起草人部门ID
     */
    @ApiModelProperty(value="起草人部门ID")
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long starterDeptId;
    /**
     * starter_dept_name
     * 起草人部门名称
     */
    @ApiModelProperty(value="起草人部门名称")
    private String starterDeptName;
    /**
     * starter_org_id
     * 起草人机构ID
     */
    @ApiModelProperty(value="起草人机构ID")
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long starterOrgId;
    /**
     * starter_org_name
     * 起草人机构名称
     */
    @ApiModelProperty(value="起草人机构名称")
    private String starterOrgName;
    /**
     * biz_key
     * 业务key
     */
    @ApiModelProperty(value="业务key")
    private String bizKey;

    /**
     * isDuplicated
     * 是否已阅
     */
    @ApiModelProperty(value="是否已阅")
    private Integer isDuplicated;

    /**
     * description
     * 传阅意见
     */
    @ApiModelProperty(value="传阅意见")
    private String description;

    /**
     * out_duplicate_task_id
     * 发起人传阅id
     */
    @ApiModelProperty(value="发起人传阅id")
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long outDuplicateTaskId;

    //2020.11.6新加字段
    /**
     * is_cloned_by_procdef_id
     * 如果被clone的话，记录被clone的流程定义ID
     */
    @ApiModelProperty(value="如果被clone的话，记录被clone的流程定义ID")
    private String isClonedByCamundaProcdefId;

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
     * priority
     * 重要程度
     */
    @ApiModelProperty(value="重要程度")
    private Integer priority;

    /**
     * biz_priority
     * 业务优先级
     */
    @ApiModelProperty(value="优先级")
    private String bizPriority;

}
