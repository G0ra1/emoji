package com.netwisd.base.wf.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.base.common.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @Description 我委托的待办 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2022-03-02 15:19:21
 */
@Data
@ApiModel(value = "我委托的待办 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WfDelegationTaskDto extends IDto{

    public WfDelegationTaskDto(Args args){
        super(args);
    }
    /**
     * camunda_procdef_id
     * camunda流程定义ID
     */
    @Valid(length = 64)
    @ApiModelProperty(value="camunda流程定义ID")
    private String camundaProcdefId;

    /**
     * camunda_procdef_key
     * camunda流程定义key
     */
    @Valid(length = 50)
    @ApiModelProperty(value="camunda流程定义key")
    private String camundaProcdefKey;

    /**
     * camunda_procdef_version
     * camunda流程定义版本
     */
    @Valid(length = 10)
    @ApiModelProperty(value="camunda流程定义版本")
    private Integer camundaProcdefVersion;

    /**
     * camunda_procins_id
     * camunda流程实例ID
     */
    @Valid(length = 64)
    @ApiModelProperty(value="camunda流程实例ID")
    private String camundaProcinsId;

    /**
     * camunda_task_id
     * camunda流程任务ID
     */
    @Valid(length = 64)
    @ApiModelProperty(value="camunda流程任务ID")
    private String camundaTaskId;

    /**
     * camunda_node_key
     * camunda流程节点key
     */
    @Valid(length = 50)
    @ApiModelProperty(value="camunda流程节点key")
    private String camundaNodeKey;

    /**
     * camunda_node_name
     * camunda流程节点name
     */
    @Valid(length = 255)
    @ApiModelProperty(value="camunda流程节点name")
    private String camundaNodeName;

    /**
     * camunda_node_type
     * 任务节点类型
     */
    @Valid(length = 10)
    @ApiModelProperty(value="任务节点类型")
    private Integer camundaNodeType;

    /**
     * procins_id
     * 流程实例ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Valid(length = 19)
    @ApiModelProperty(value="流程实例ID")
    private Long procinsId;

    /**
     * procins_name
     * 流程实例名称
     */
    @Valid(length = 255)
    @ApiModelProperty(value="流程实例名称")
    private String procinsName;

    /**
     * ownner
     * 被委托人
     */
    @ApiModelProperty(value="被委托人")
    private String ownner;

    /**
     * ownner_name
     * 被委托人名称
     */
    @ApiModelProperty(value="被委托人名称")
    private String ownnerName;

    /**
     * assignee
     * 委托办理人
     */
    @ApiModelProperty(value="委托办理人")
    private String assignee;

    /**
     * assignee_name
     * 委托办理人
     */
    @ApiModelProperty(value="委托办理人")
    private String assigneeName;

    /**
     * procdef_id
     * 流程定义ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Valid(length = 19)
    @ApiModelProperty(value="流程定义ID")
    private Long procdefId;

    /**
     * procdef_name
     * 流程定义名称
     */
    @Valid(length = 50)
    @ApiModelProperty(value="流程定义名称")
    private String procdefName;

    /**
     * procdef_type_id
     * 流程分类ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Valid(length = 19)
    @ApiModelProperty(value="流程分类ID")
    private Long procdefTypeId;

    /**
     * procdef_type_name
     * 流程分类名称
     */
    @Valid(length = 50)
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
     * 状态
     */
    @ApiModelProperty(value="状态")
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
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Valid(length = 19)
    @ApiModelProperty(value="开始起草人ID")
    private Long starterId;

    /**
     * starter_name
     * 起草人名称
     */
    @Valid(length = 50)
    @ApiModelProperty(value="起草人名称")
    private String starterName;

    /**
     * starter_dept_id
     * 起草人部门ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Valid(length = 19)
    @ApiModelProperty(value="起草人部门ID")
    private Long starterDeptId;

    /**
     * starter_dept_name
     * 起草人部门名称
     */
    @Valid(length = 50)
    @ApiModelProperty(value="起草人部门名称")
    private String starterDeptName;

    /**
     * starter_org_id
     * 起草人机构ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Valid(length = 19)
    @ApiModelProperty(value="起草人机构ID")
    private Long starterOrgId;

    /**
     * starter_org_name
     * 起草人机构名称
     */
    @Valid(length = 50)
    @ApiModelProperty(value="起草人机构名称")
    private String starterOrgName;

    /**
     * biz_key
     * 业务key
     */
    @ApiModelProperty(value="业务key")
    private String bizKey;

    /**
     * is_duplicated
     * 是否已阅
     */
    @ApiModelProperty(value="是否已阅")
    private Integer isDuplicated;

    /**
     * is_call_activity
     * 是否子流程
     */
    @Valid(length = 10)
    @ApiModelProperty(value="是否子流程")
    private Integer isCallActivity;

    /**
     * is_clone
     * 是否clone
     */
    @ApiModelProperty(value="是否clone")
    private Integer isClone;

    /**
     * camunda_parent_procins_id
     * camunda 父流程实例ID
     */
    @ApiModelProperty(value="camunda 父流程实例ID")
    private String camundaParentProcinsId;

    /**
     * parent_procins_id
     * 父流程实例ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="父流程实例ID")
    private Long parentProcinsId;

    /**
     * is_cloned_by_camunda_procdef_id
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
     * create_user_id
     * 创建人ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="创建人ID")
    private Long createUserId;

    /**
     * create_user_name
     * 创建人名称
     */
    @ApiModelProperty(value="创建人名称")
    private String createUserName;

    /**
     * create_user_parent_org_id
     * 创建人父级机构ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="创建人父级机构ID")
    private Long createUserParentOrgId;

    /**
     * create_user_parent_org_name
     * 创建人父级机构名称
     */
    @ApiModelProperty(value="创建人父级机构名称")
    private String createUserParentOrgName;

    /**
     * create_user_parent_dept_id
     * 创建人父级部门ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="创建人父级部门ID")
    private Long createUserParentDeptId;

    /**
     * create_user_parent_dept_name
     * 创建人父级部门名称
     */
    @ApiModelProperty(value="创建人父级部门名称")
    private String createUserParentDeptName;

    /**
     * create_user_org_full_id
     * 创建人父级组织全路径ID
     */
    @ApiModelProperty(value="创建人父级组织全路径ID")
    private String createUserOrgFullId;

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

}
