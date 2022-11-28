package com.netwisd.base.common.vo.wf;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * @Description 流程实例 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-08-06 13:49:51
 */
@Data
@ApiModel(value = "流程实例 Vo")
public class WfProcessVo extends IVo{
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
     * camunda_parent_proc_ins_id
     * camunda 父流程实例ID
     */
    @ApiModelProperty(value="camunda 父流程实例ID")
    private String camundaParentProcinsId;
    /**
     * camunda_call_activity_def_id
     * camunda callActivity定义ID
     */
    @ApiModelProperty(value="camunda callActivity定义ID")
    private String camundaCallActivityDefId;
    /**
     * camunda_parent_act_ins_id
     * camunda父节点实例ID
     */
    @ApiModelProperty(value="camunda父节点实例ID")
    private String camundaParentActInsId;
    /**
     * parent_proc_ins_id
     * 父流程实例ID
     */
    @ApiModelProperty(value="父流程实例ID")
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long parentProcinsId;
    /**
     * proins_name
     * 流程实例名称
     */
    @ApiModelProperty(value="流程实例名称")
    private String procinsName;
    /**
     * biz_key
     * 业务key
     */
    @ApiModelProperty(value="业务key")
    private String bizKey;
    /**
     * start_time
     * 流程启动时间
     */
    @ApiModelProperty(value="流程启动时间")
    private LocalDateTime startTime;
    /**
     * end_time
     * 流程结束时间
     */
    @ApiModelProperty(value="流程结束时间")
    private LocalDateTime endTime;
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
     * reason
     * 事由
     */
    @ApiModelProperty(value="事由")
    private String reason;
    /**
     * bizPriority
     * 优先级
     */
    @ApiModelProperty(value="优先级")
    private String bizPriority;
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
     * state
     * 流程状态
     */
    @ApiModelProperty(value="流程状态")
    private Integer state;
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
}