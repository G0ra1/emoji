package com.netwisd.base.wf.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description 流程定义-节点定义 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-15 10:39:52
 */
@Data
@ApiModel(value = "流程定义-节点定义 Vo")
public class WfNodeDefVo extends IVo{
    /**
     * camunda_node_def_id
     * camunda节点ID
     */
    @ApiModelProperty(value="camunda节点ID")
    private String camundaNodeDefId;
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
     * due_date
     * 过期时间
     */
    @ApiModelProperty(value="过期时间")
    private Double dueDate;
    /**
     * follow_up_date
     * 跟踪时间
     */
    @ApiModelProperty(value="跟踪时间")
    private Double followUpDate;
    /**
     * priority
     * 任务重要程度
     */
    @ApiModelProperty(value="任务重要程度")
    private Integer priority;
    /**
     * procdef_id
     * 流程定义ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="流程定义ID")
    private Long procdefId;
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
     * is_multi_task
     * 是否多任务节点
     */
    @ApiModelProperty(value="是否多任务节点")
    private Integer isMultiTask;

    /**
     * select_rule
     * 选人规则
     */
    @ApiModelProperty(value="选人规则")
    private Integer selectRule;
    /**
     * batch_rule
     * 批量审批规则
     */
    @ApiModelProperty(value="批量审批规则")
    private Integer batchRule;
    /**
     * cancel_rule
     * 是否支持撤回
     */
    @ApiModelProperty(value="是否支持撤回")
    private Integer cancelRule;
    /**
     * return_rule
     * 是否支持驳回
     */
    @ApiModelProperty(value="是否支持驳回")
    private Integer returnRule;
    /**
     * passing_rate
     * 会签通过率
     */
    @ApiModelProperty(value="会签通过率")
    private BigDecimal passingRate;
    /**
     * passing_handle
     * 会签通过处理方式
     */
    @ApiModelProperty(value="会签通过处理方式")
    private Integer passingHandle;
    /**
     * unpassing_handle
     * 会签不通过处理方式
     */
    @ApiModelProperty(value="会签不通过处理方式")
    private Integer unpassingHandle;

    /**
     * camunda_sub_process_node_def_id
     * camunda 父级子流程nodeId
     */
    @ApiModelProperty(value="父级子流程nodeId")
    private String camundaParentNodeDefId;
}
