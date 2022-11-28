package com.netwisd.base.wf.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
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
 * @Description $流程实例 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-08-06 13:49:51
 */
@Data
@Table(value = "incloud_base_wf_process",comment = "流程实例")
@TableName("incloud_base_wf_process")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "流程实例 Entity")
public class WfProcess extends IModel<WfProcess> {
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
     * camunda_parent_proc_ins_id
     * camunda 父流程实例ID
     */
    @ApiModelProperty(value="camunda 父流程实例ID")
    @TableField(value="camunda_parent_procins_id")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "camunda 父流程实例ID")
    private String camundaParentProcinsId;
    /**
     * camunda_call_activity_def_id
     * camunda callActivity定义ID
     */
    @ApiModelProperty(value="camunda callActivity定义ID")
    @TableField(value="camunda_call_activity_def_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "camunda callActivity定义ID")
    private String camundaCallActivityDefId;
    /**
     * camunda_parent_act_ins_id
     * camunda父节点实例ID
     */
    @ApiModelProperty(value="camunda父节点实例ID")
    @TableField(value="camunda_parent_act_ins_id")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "camunda父节点实例ID")
    private String camundaParentActInsId;
    /**
     * parent_proc_ins_id
     * 父流程实例ID
     */
    @ApiModelProperty(value="父流程实例ID")
    @TableField(value="parent_procins_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "父流程实例ID")
    private Long parentProcinsId;
    /**
     * procins_name
     * 流程实例名称
     */
    @ApiModelProperty(value="流程实例名称")
    @TableField(value="procins_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "流程实例名称")
    private String procinsName;
    /**
     * biz_key
     * 业务key
     */
    @ApiModelProperty(value="业务key")
    @TableField(value="biz_key")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "业务key")
    private String bizKey;
    /**
     * start_time
     * 流程启动时间
     */
    @ApiModelProperty(value="流程启动时间")
    @TableField(value="start_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "流程启动时间")
    private LocalDateTime startTime;
    /**
     * end_time
     * 流程结束时间
     */
    @ApiModelProperty(value="流程结束时间")
    @TableField(value="end_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "流程结束时间")
    private LocalDateTime endTime;
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
     * reason
     * 事由
     */
    @ApiModelProperty(value="事由")
    @TableField(value="reason")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "事由")
    private String reason;
    /**
     * biz_priority
     * 优先级
     */
    @ApiModelProperty(value="优先级")
    @TableField(value="biz_priority")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "优先级")
    private String bizPriority;
    /**
     * apply_time
     * 申请时间
     */
    @ApiModelProperty(value="申请时间")
    @TableField(value="apply_time")
    @Column(type = DataType.DATETIME, length = 0, isNull = true, comment = "申请时间")
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
     * state
     * 流程状态
     */
    @ApiModelProperty(value="流程状态")
    @TableField(value="state")
    @Column(type = DataType.INT, length = 2,  isNull = false, comment = "流程状态")
    private Integer state;
    /**
     * isClone
     * 是否clone
     */
    @ApiModelProperty(value="是否clone")
    @TableField(value="is_clone")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "是否clone")
    private Integer isClone = 0;
    /**
     * is_call_activity
     * 是否外部流程
     */
    @ApiModelProperty(value="是否外部流程")
    @TableField(value="is_call_activity")
    @Column(type = DataType.INT, length = 1,  isNull = false, comment = "是否子流程")
    private Integer isCallActivity;
    //2020.11.6新加字段
    /**
     * be_cloned_From_camunda_procdef_id
     * 如果被clone的话，记录被clone的流程定义ID
     */
    @ApiModelProperty(value="如果被clone的话，记录被clone的流程定义ID")
    @TableField(value="be_cloned_From_camunda_procdef_id")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "如果被clone的话，记录被clone的流程定义ID")
    private String beClonedFromCamundaProcdefId;
    /**
     * current_activity_id
     * 当前活动ID
     */
    @ApiModelProperty(value="当前活动ID")
    @TableField(value="current_activity_id",updateStrategy = FieldStrategy.IGNORED)
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "当前活动ID")
    private String currentActivityId;
    /**
     * current_activity_name
     * 当前活动名称
     */
    @ApiModelProperty(value="当前活动名称")
    @TableField(value="current_activity_name",updateStrategy = FieldStrategy.IGNORED)
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "当前活动名称")
    private String currentActivityName;
    /**
     * current_activity_assignee
     * 当前活动办理人
     */
    @ApiModelProperty(value="当前活动办理人")
    @TableField(value="current_activity_assignee",updateStrategy = FieldStrategy.IGNORED)
    @Column(type = DataType.VARCHAR, length = 1000,  isNull = true, comment = "当前活动办理人")
    private String currentActivityAssignee;
    /**
     * current_activity_assignee_name
     * 当前活动办理人名称
     */
    @ApiModelProperty(value="当前活动办理人名称")
    @TableField(value="current_activity_assignee_name",updateStrategy = FieldStrategy.IGNORED)
    @Column(type = DataType.VARCHAR, length = 1000,  isNull = true, comment = "当前活动办理人名称")
    private String currentActivityAssigneeName;
}