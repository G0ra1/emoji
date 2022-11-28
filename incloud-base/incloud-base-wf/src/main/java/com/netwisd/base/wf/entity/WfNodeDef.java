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
import java.math.BigDecimal;

/**
 * @Description $流程定义-节点定义 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-15 10:39:52
 */
@Data
@Table(value = "incloud_base_wf_node_def",comment = "流程定义-节点定义")
@TableName("incloud_base_wf_node_def")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "流程定义-节点定义 Entity")
public class WfNodeDef extends IModel<WfNodeDef> {
    /**
     * camunda_node_def_id
     * camunda节点ID
     */
    @ApiModelProperty(value="camunda节点ID")
    @TableField(value="camunda_node_def_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = false, comment = "camunda节点ID")
    private String camundaNodeDefId;
    /**
     * node_name
     * 节点名称
     */
    @ApiModelProperty(value="节点名称")
    @TableField(value="node_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "节点名称")
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
     * due_date
     * 过期时间
     */
    @ApiModelProperty(value="过期时间")
    @TableField(value="due_date")
    @Column(type = DataType.DOUBLE, length = 3, precision =1,  isNull = true, comment = "过期时间")
    private Double dueDate;
    /**
     * follow_up_date
     * 跟踪时间
     */
    @ApiModelProperty(value="跟踪时间")
    @TableField(value="follow_up_date")
    @Column(type = DataType.DOUBLE, length = 3, precision =1,  isNull = true, comment = "跟踪时间")
    private Double followUpDate;
    /**
     * priority
     * 任务重要程度
     */
    @ApiModelProperty(value="任务重要程度")
    @TableField(value="priority")
    @Column(type = DataType.INT, length = 2,  isNull = true, comment = "任务重要程度")
    private Integer priority;
    /**
     * procdef_id
     * 流程定义ID
     */
    @ApiModelProperty(value="流程定义ID")
    @TableField(value="procdef_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "流程定义ID")
    private Long procdefId;
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
     * is_multi_task
     * 是否多任务节点
     */
    @ApiModelProperty(value="是否多任务节点")
    @TableField(value="is_multi_task")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "是否多任务节点")
    private Integer isMultiTask;

    /**
     * select_rule
     * 选人规则
     */
    @ApiModelProperty(value="选人规则")
    @TableField(value="select_rule")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "选人规则")
    private Integer selectRule;
    /**
     * batch_rule
     * 批量审批规则
     */
    @ApiModelProperty(value="批量审批规则")
    @TableField(value="batch_rule")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "批量审批规则")
    private Integer batchRule;
    /**
     * cancel_rule
     * 是否支持撤回
     */
    @ApiModelProperty(value="是否支持撤回")
    @TableField(value="cancel_rule")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "是否支持撤回")
    private Integer cancelRule;
    /**
     * return_rule
     * 是否支持驳回
     */
    @ApiModelProperty(value="是否支持驳回")
    @TableField(value="return_rule")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "是否支持驳回")
    private Integer returnRule;
    /**
     * passing_rate
     * 会签通过率
     */
    @ApiModelProperty(value="会签通过率")
    @TableField(value="passing_rate")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "会签通过率")
    private BigDecimal passingRate;
    /**
     * passing_handle
     * 会签通过处理方式
     */
    @ApiModelProperty(value="会签通过处理方式")
    @TableField(value="passing_handle")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "会签通过处理方式")
    private Integer passingHandle;
    /**
     * unpassing_handle
     * 会签不通过处理方式
     */
    @ApiModelProperty(value="会签不通过处理方式")
    @TableField(value="unpassing_handle")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "会签不通过处理方式")
    private Integer unpassingHandle;

    /**
     * camunda_sub_process_node_def_id
     * camunda 父级子流程nodeId
     */
    @ApiModelProperty(value="父级子流程nodeId")
    @TableField(value="camunda_parent_node_def_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "父级子流程nodeId")
    private String camundaParentNodeDefId;
}
