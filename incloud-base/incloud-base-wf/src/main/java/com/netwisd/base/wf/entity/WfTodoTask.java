package com.netwisd.base.wf.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.anntation.Table;
import com.netwisd.common.db.data.DataType;
import com.netwisd.common.db.data.IModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
    import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description $待办任务 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-08-06 16:38:05
 */
@Data
@Table(value = "incloud_base_wf_todo_task",comment = "待办任务")
@TableName("incloud_base_wf_todo_task")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "待办任务 Entity")
public class WfTodoTask extends IModel<WfTodoTask> implements WfTask{

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
     * camunda任务ID
     */
    @ApiModelProperty(value="camunda任务ID")
    @TableField(value="camunda_task_id")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = false, comment = "camunda任务ID")
    private String camundaTaskId;
    /**
     * camunda_exection_id
     * camunda exection实例ID
     */
    @ApiModelProperty(value="camunda exection实例ID")
    @TableField(value="camunda_exection_id")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = false, comment = "camunda exection实例ID")
    private String camundaExectionId;
    /**
     * camunda_act_ins_id
     * camunda节点实例id
     */
    @ApiModelProperty(value="camunda节点实例id")
    @TableField(value="camunda_act_ins_id")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = false, comment = "camunda节点实例id")
    private String camundaActInsId;
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
     * task_name
     * 任务名称
     */
    @ApiModelProperty(value="任务名称")
    @TableField(value="task_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "任务名称")
    private String taskName;
    /**
     * node_key
     * 节点定义key，ID
     */
    @ApiModelProperty(value="节点定义key，ID")
    @TableField(value="node_key")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = false, comment = "节点定义key，ID")
    private String nodeKey;
    /**
     * node_name
     * 节点名称
     */
    @ApiModelProperty(value="节点名称")
    @TableField(value="node_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "节点名称")
    private String nodeName;
    /**
     * node_type
     * 节点类型
     */
    @ApiModelProperty(value="节点类型")
    @TableField(value="node_type")
    @Column(type = DataType.INT, length = 2,  isNull = false, comment = "节点类型")
    private Integer nodeType;
    /**
     * decision
     * 流程决策标识
     */
    @ApiModelProperty(value="流程决策标识")
    @TableField(value="decision")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "流程决策标识")
    private Integer decision;
    /**
     * is_agree
     * 是否同意
     */
    @ApiModelProperty(value="是否同意")
    @TableField(value="is_agree")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "是否同意")
    private Integer isAgree;
    /**
     * description
     * 处理意见
     */
    @ApiModelProperty(value="处理意见")
    @TableField(value="description")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "处理意见")
    private String description;
    /**
     * ownner
     * 任务拥有人
     */
    @ApiModelProperty(value="任务拥有人")
    @TableField(value="ownner")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "任务拥有人")
    private String ownner;
    /**
     * assignee
     * 任务办理人
     */
    @ApiModelProperty(value="任务办理人")
    @TableField(value="assignee")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "任务办理人")
    private String assignee;
    /**
     * assigneeName
     * 任务办理人名称
     */
    @ApiModelProperty(value="任务办理人名称")
    @TableField(value="assignee_name")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "任务办理人名称")
    private String assigneeName;

    /**
     * priority
     * 重要程度
     */
    @ApiModelProperty(value="重要程度")
    @TableField(value="priority")
    @Column(type = DataType.INT, length = 2,  isNull = true, comment = "重要程度")
    private Integer priority;
    /**
     * due_date
     * 任务过期日期
     */
    @ApiModelProperty(value="任务过期日期")
    @TableField(value="due_date")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "任务过期日期")
    private LocalDateTime dueDate;
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
    @ApiModelProperty(value="任务状态")
    @TableField(value="state")
    @Column(type = DataType.INT, length = 2,  isNull = true, comment = "任务状态")
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
     * biz_key
     * 业务key
     */
    @ApiModelProperty(value="业务key")
    @TableField(value="biz_key")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "业务key")
    private String bizKey;

    /**
     * candidates
     * 候选人集合
     */
    @ApiModelProperty(value="候选人集合")
    @TableField(value="candidates")
    @Column(type = DataType.VARCHAR, length = 4000,  isNull = true, comment = "候选人集合")
    private String candidates;

    /**
     * is_draft
     * 是否草稿数据
     */
    @ApiModelProperty(value="是否草稿数据")
    @TableField(value="is_draft")
    @Column(type = DataType.INT, length = 1,  isNull = false, comment = "是否草稿数据")
    private Integer isDraft;

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

    /**
     * biz_priority
     * 业务优先级
     */
    @ApiModelProperty(value="业务优先级")
    @TableField(value="biz_priority")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "业务优先级")
    private String bizPriority;
}
