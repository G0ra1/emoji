package com.netwisd.base.wf.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.anntation.Table;
import com.netwisd.common.db.data.DataType;
import com.netwisd.common.db.data.IModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @Description 我收到的知会
 * @author 云数网讯 XHL
 * @date 2022-02-14 09:39:14
 */
@Data
@Table(value = "incloud_base_wf_receive_notify_task",comment = "我收到的知会")
@TableName("incloud_base_wf_receive_notify_task")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "我收到的知会 Entity")
public class WfReceiveNotifyTask extends IModel<WfReceiveNotifyTask> {

    /**
     * camunda_procdef_id
     * camunda流程定义ID
     */
    @ApiModelProperty(value="camunda流程定义ID")
    @TableField(value="camunda_procdef_id")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = false, comment = "camunda流程定义ID")
    private String camundaProcdefId;
    /**
     * camunda_procdef_key
     * camunda流程定义key
     */
    @ApiModelProperty(value="camunda流程定义key")
    @TableField(value="camunda_procdef_key")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = false, comment = "camunda流程定义key")
    private String camundaProcdefKey;
    /**
     * camunda_procdef_version
     * camunda流程定义版本
     */
    @ApiModelProperty(value="camunda流程定义版本")
    @TableField(value="camunda_procdef_version")
    @Column(type = DataType.INT, length = 2,  isNull = false, comment = "camunda流程定义版本")
    private Integer camundaProcdefVersion;
    /**
     * camunda_procins_id
     * camunda流程实例ID
     */
    @ApiModelProperty(value="camunda流程实例ID")
    @TableField(value="camunda_procins_id")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = false, comment = "camunda流程实例ID")
    private String camundaProcinsId;

    /**
     * camunda_task_id
     * camunda流程任务ID
     */
    @ApiModelProperty(value="camunda流程任务ID")
    @TableField(value="camunda_task_id")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = false, comment = "camunda流程任务ID")
    private String camundaTaskId;
    /**
     * camunda_node_key
     * camunda流程节点key
     */
    @ApiModelProperty(value="发起传阅的任务节点ID")
    @TableField(value="camunda_node_key")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = false, comment = "camunda流程节点key")
    private String camundaNodeKey;
    /**
     * camunda_node_name
     * camunda流程节点name
     */
    @ApiModelProperty(value="任务节点名称")
    @TableField(value="camunda_node_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "camunda流程节点name")
    private String camundaNodeName;
    /**
     * camunda_node_type
     * 任务节点类型
     */
    @ApiModelProperty(value="任务节点类型")
    @TableField(value="camunda_node_type")
    @Column(type = DataType.INT, length = 2,  isNull = false, comment = "发起传阅的任务节点类型")
    private Integer camundaNodeType;
    /**
     * procins_id
     * 流程实例ID
     */
    @ApiModelProperty(value="流程实例ID")
    @TableField(value="procins_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "流程实例ID")
    private Long procinsId;
    /**
     * procins_name
     * 流程实例名称
     */
    @ApiModelProperty(value="流程实例名称")
    @TableField(value="procins_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "流程实例名称")
    private String procinsName;
    /**
     * ownner
     * 发起知会人
     */
    @ApiModelProperty(value="发起知会人")
    @TableField(value="ownner")
    @Column(type = DataType.VARCHAR, length = 20,  isNull = false, comment = "发起知会人")
    private String ownner;

    /**
     * ownner_name
     * 发起知会人名称
     */
    @ApiModelProperty(value="发起知会人名称")
    @TableField(value="ownner_name")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "发起知会人名称")
    private String ownnerName;

    /**
     * assignee
     * 知会办理人
     */
    @ApiModelProperty(value="知会办理人")
    @TableField(value="assignee")
    @Column(type = DataType.VARCHAR, length = 20,  isNull = false, comment = "知会办理人")
    private String assignee;

    /**
     * assignee_name
     * 知会办理人名称
     */
    @ApiModelProperty(value="知会办理人名称")
    @TableField(value="assignee_name")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "知会办理人名称")
    private String assigneeName;

    /**
     * procdef_id
     * 流程定义ID
     */
    @ApiModelProperty(value="流程定义ID")
    @TableField(value="procdef_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "流程定义ID")
    private Long procdefId;
    /**
     * procdef_name
     * 流程定义名称
     */
    @ApiModelProperty(value="流程定义名称")
    @TableField(value="procdef_name")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = false, comment = "流程定义名称")
    private String procdefName;
    /**
     * procdef_type_id
     * 流程分类ID
     */
    @ApiModelProperty(value="流程分类ID")
    @TableField(value="procdef_type_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "流程分类ID")
    private Long procdefTypeId;
    /**
     * procdef_type_name
     * 流程分类名称
     */
    @ApiModelProperty(value="流程分类名称")
    @TableField(value="procdef_type_name")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = false, comment = "流程分类名称")
    private String procdefTypeName;
    /**
     * cliam_time
     * 签收时间
     */
    @ApiModelProperty(value="签收时间")
    @TableField(value="cliam_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "签收时间")
    private LocalDateTime cliamTime;
    /**
     * state
     * 任务挂起状态
     */
    @ApiModelProperty(value="状态")
    @TableField(value="state")
    @Column(type = DataType.INT, length = 2,  isNull = true, comment = "状态")
    private Integer state;
    /**
     * reason
     * 事由
     */
    @ApiModelProperty(value="事由")
    @TableField(value="reason")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "事由")
    private String reason;
    /**
     * apply_time
     * 申请时间
     */
    @ApiModelProperty(value="申请时间")
    @TableField(value="apply_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "申请时间")
    private LocalDateTime applyTime;
    /**
     * starter_id
     * 开始起草人ID
     */
    @ApiModelProperty(value="开始起草人ID")
    @TableField(value="starter_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "开始起草人ID")
    private Long starterId;
    /**
     * starter_name
     * 起草人名称
     */
    @ApiModelProperty(value="起草人名称")
    @TableField(value="starter_name")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = false, comment = "起草人名称")
    private String starterName;
    /**
     * starter_dept_id
     * 起草人部门ID
     */
    @ApiModelProperty(value="起草人部门ID")
    @TableField(value="starter_dept_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "起草人部门ID")
    private Long starterDeptId;
    /**
     * starter_dept_name
     * 起草人部门名称
     */
    @ApiModelProperty(value="起草人部门名称")
    @TableField(value="starter_dept_name")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = false, comment = "起草人部门名称")
    private String starterDeptName;
    /**
     * starter_org_id
     * 起草人机构ID
     */
    @ApiModelProperty(value="起草人机构ID")
    @TableField(value="starter_org_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "起草人机构ID")
    private Long starterOrgId;
    /**
     * starter_org_name
     * 起草人机构名称
     */
    @ApiModelProperty(value="起草人机构名称")
    @TableField(value="starter_org_name")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = false, comment = "起草人机构名称")
    private String starterOrgName;
    /**
     * biz_key
     * 业务key
     */
    @ApiModelProperty(value="业务key")
    @TableField(value="biz_key")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "业务key")
    private String bizKey;

    /**
     * is_duplicated
     * 是否已阅
     */
    @ApiModelProperty(value="是否已阅")
    @TableField(value="is_duplicated")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "是否已阅")
    private Integer isDuplicated;

    /**
     * is_call_activity
     * 是否子流程
     */
    @ApiModelProperty(value="是否子流程")
    @TableField(value="is_call_activity")
    @Column(type = DataType.INT, length = 1,  isNull = false, comment = "是否子流程")
    private Integer isCallActivity;

    /**
     * isClone
     * 是否clone
     */
    @ApiModelProperty(value="是否clone")
    @TableField(value="is_clone")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "是否clone")
    private Integer isClone = 0;

    /**
     * camunda_parent_proc_ins_id
     * camunda 父流程实例ID
     */
    @ApiModelProperty(value="camunda 父流程实例ID")
    @TableField(value="camunda_parent_procins_id")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "camunda 父流程实例ID")
    private String camundaParentProcinsId;

    /**
     * parent_proc_ins_id
     * 父流程实例ID
     */
    @ApiModelProperty(value="父流程实例ID")
    @TableField(value="parent_procins_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "父流程实例ID")
    private Long parentProcinsId;
    //2020.11.6新加字段
    /**
     * is_cloned_by_procdef_id
     * 如果被clone的话，记录被clone的流程定义ID
     */
    @ApiModelProperty(value="如果被clone的话，记录被clone的流程定义ID")
    @TableField(value="is_cloned_by_camunda_procdef_id")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "如果被clone的话，记录被clone的流程定义ID")
    private String isClonedByCamundaProcdefId;

    /**
     * current_activity_id
     * 当前活动ID
     */
    @ApiModelProperty(value="当前活动ID")
    @TableField(value="current_activity_id")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "当前活动ID")
    private String currentActivityId;

    /**
     * current_activity_name
     * 当前活动名称
     */
    @ApiModelProperty(value="当前活动名称")
    @TableField(value="current_activity_name")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "当前活动名称")
    private String currentActivityName;

    /**
     * current_activity_assignee
     * 当前活动办理人
     */
    @ApiModelProperty(value="当前活动办理人")
    @TableField(value="current_activity_assignee")
    @Column(type = DataType.VARCHAR, length = 1000,  isNull = true, comment = "当前活动办理人")
    private String currentActivityAssignee;

    /**
     * current_activity_assignee_name
     * 当前活动办理人名称
     */
    @ApiModelProperty(value="当前活动办理人名称")
    @TableField(value="current_activity_assignee_name")
    @Column(type = DataType.VARCHAR, length = 1000,  isNull = true, comment = "当前活动办理人名称")
    private String currentActivityAssigneeName;

    /**
     * notify_opinion
     * 知会的意见
     */
    @ApiModelProperty(value="知会的意见")
    @TableField(value="notify_opinion")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "知会的意见")
    private String notifyOpinion;


    /**
     * submit_param
     * 提交参数
     */
    @ApiModelProperty(value="提交参数")
    @TableField(value="submit_param")
    @Column(type = DataType.TEXT,  isNull = true, comment = "提交参数")
    private String submitParam;

    /**
     * priority
     * 重要程度
     */
    @ApiModelProperty(value="重要程度")
    @TableField(value="priority")
    @Column(type = DataType.INT, length = 2,  isNull = true, comment = "重要程度")
    private Integer priority;

    /**
     * biz_priority
     * 业务优先级
     */
    @TableField(value="biz_priority")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "任务优先级")
    private String bizPriority;
}
