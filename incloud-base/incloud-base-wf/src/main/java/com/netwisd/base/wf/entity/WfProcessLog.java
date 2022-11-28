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
import java.util.Date;

/**
 * @Description $流程日志 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-08-14 17:42:43
 */
@Data
@Table(value = "incloud_base_wf_process_log",comment = "流程日志")
@TableName("incloud_base_wf_process_log")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "流程日志 Entity")
public class WfProcessLog extends IModel<WfProcessLog> {

    /**
     * node_id
     * 节点ID
     */
    @ApiModelProperty(value="节点ID")
    @TableField(value="node_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "节点ID")
    private String nodeId;
    /**
     * node_name
     * 节点名称
     */
    @ApiModelProperty(value="节点名称")
    @TableField(value="node_name")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "节点名称")
    private String nodeName;
    /**
     * node_type
     * 节点类型
     */
    @ApiModelProperty(value="节点类型")
    @TableField(value="node_type")
    @Column(type = DataType.INT, length = 2,  isNull = true, comment = "节点类型")
    private Integer nodeType;
    /**
     * target_
     * 目标节点ID
     */
    @ApiModelProperty(value="目标节点ID")
    @TableField(value="target_node_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "目标节点ID")
    private String targetNodeId;
    /**
     * target_node_name
     * 目标节点名称
     */
    @ApiModelProperty(value="目标节点名称")
    @TableField(value="target_node_name")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "目标节点名称")
    private String targetNodeName;
    /**
     * target_node_type
     * 目标节点类型
     */
    @ApiModelProperty(value="目标节点类型")
    @TableField(value="target_node_type")
    @Column(type = DataType.INT, length = 2,  isNull = true, comment = "目标节点类型")
    private Integer targetNodeType;
    /**
     * user_name
     * 用户名账号
     */
    @ApiModelProperty(value="用户名账号")
    @TableField(value="user_name")
    @Column(type = DataType.VARCHAR, length = 300,  isNull = true, comment = "用户名账号")
    private String userName;
    /**
     * user_name_ch
     * 用户名称
     */
    @ApiModelProperty(value="用户名称")
    @TableField(value="user_name_ch")
    @Column(type = DataType.VARCHAR, length = 300,  isNull = true, comment = "用户名称")
    private String userNameCh;
    /**
     * dept_id
     * 部门ID
     */
    @ApiModelProperty(value="部门ID")
    @TableField(value="dept_id")
    @Column(type = DataType.VARCHAR, length = 300,  isNull = true, comment = "部门ID")
    private String deptId;
    /**
     * dept_name
     * 部门名称
     */
    @ApiModelProperty(value="部门名称")
    @TableField(value="dept_name")
    @Column(type = DataType.VARCHAR, length = 300,  isNull = true, comment = "部门名称")
    private String deptName;
    /**
     * org_id
     * 机构ID
     */
    @ApiModelProperty(value="机构ID")
    @TableField(value="org_id")
    @Column(type = DataType.VARCHAR, length = 300,  isNull = true, comment = "机构ID")
    private String orgId;
    /**
     * org_name
     * 机构名称
     */
    @ApiModelProperty(value="机构名称")
    @TableField(value="org_name")
    @Column(type = DataType.VARCHAR, length = 300,  isNull = true, comment = "机构名称")
    private String orgName;
    /**
     * start_time
     * 开始时间
     */
    @ApiModelProperty(value="开始时间")
    @TableField(value="start_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = false, comment = "开始时间")
    private LocalDateTime startTime;
    /**
     * end_time
     * 结束时间
     */
    @ApiModelProperty(value="结束时间")
    @TableField(value="end_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "结束时间")
    private LocalDateTime endTime;
    /**
     * type
     * 类型
     */
    @ApiModelProperty(value="日志类型")
    @TableField(value="type")
    @Column(type = DataType.INT, length = 2,  isNull = true, comment = "日志类型")
    private Integer type;
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
     * 意见
     */
    @ApiModelProperty(value="意见")
    @TableField(value="description")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "意见")
    private String description;
    /**
     * process_id
     * 流程实例ID
     */
    @ApiModelProperty(value="流程实例ID")
    @TableField(value="procins_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "流程实例ID")
    private Long procinsId;
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
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "camunda任务ID")
    private String camundaTaskId;

    /**
     * camunda_parent_act_ins_id
     * camunda父节点实例ID
     */
    @ApiModelProperty(value="camunda父节点实例ID")
    @TableField(value="camunda_parent_act_ins_id")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "camunda父节点实例ID")
    private String camundaParentActInsId;

    /**
     * camunda_current_act_ins_id
     * camunda当前节点实例ID
     */
    @ApiModelProperty(value="camunda当前节点实例ID")
    @TableField(value="camunda_current_act_ins_id")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "camunda当前节点实例ID")
    private String camundaCurrentActInsId;

    /**
     * camunda_call_activity_def_id
     * camunda callActivity定义ID
     */
    @ApiModelProperty(value="camunda callActivity定义ID")
    @TableField(value="camunda_call_activity_def_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "camunda callActivity定义ID")
    private String camundaCallActivityDefId;

    //--2020.11.3新加字段
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
     * procins_name
     * 流程实例名称
     */
    @ApiModelProperty(value="流程实例名称")
    @TableField(value="procins_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "流程实例名称")
    private String procinsName;

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
     * claim_status
     * 签收状态
     */
    @ApiModelProperty(value="签收状态")
    @TableField(value="claim_status")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "签收状态")
    private Integer claimStatus;

    /**
     * claim_time
     * 签收时间
     */
    @ApiModelProperty(value="签收时间")
    @TableField(value="claim_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "签收时间")
    private LocalDateTime claimTime;

    /**
     * apply_time
     * 申请时间
     */
    @ApiModelProperty(value="申请时间")
    @TableField(value="apply_time")
    @Column(type = DataType.DATETIME, length = 0, isNull = true, comment = "申请时间")
    private LocalDateTime applyTime;
}
