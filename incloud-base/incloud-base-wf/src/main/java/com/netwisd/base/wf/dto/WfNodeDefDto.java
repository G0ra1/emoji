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
import java.math.BigDecimal;
import java.util.List;

/**
 * @Description 流程定义-节点定义 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-15 10:39:52
 */
@Data
@ApiModel(value = "流程定义-节点定义 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WfNodeDefDto extends IDto{

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
    @Valid(length = 50) 
    private String nodeName;

    /**
     * node_type
     * 节点类型
     */
    @ApiModelProperty(value="节点类型")
    @Valid(length = 2) 
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
    @ApiModelProperty(value="流程定义ID")
    @Valid(length = 20)
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long procdefId;

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
     * xml解析对应的 事件信息
     */
    @ApiModelProperty(value="xml解析对应的 事件信息")
    private List<WfEventDefDto> wfEventDefDtoList;

    /**
     * xml解析对应的 表达式信息 以及对应的参数信息
     */
    @ApiModelProperty(value="xml解析对应的 事件信息")
    private List<WfExpreUserDefDto> wfExpreUserDefDtoList;


    /**
     * xml解析对应的 按钮信息 以及对应的参数信息
     */
    @ApiModelProperty(value="xml解析对应的 按钮信息")
    private List<WfButtonDefDto> wfButtonDefDtoListDto;

    /**
     * xml解析对应的 变量信息 以及对应的参数信息
     */
    @ApiModelProperty(value="xml解析对应的 变量信息")
    private List<WfVarDefDto> wfVarDefDtoList;

    /**
     * startNodeDefDtoList
     * camunda 子流程中的开始节点信息
     */
    @ApiModelProperty(value="子流程中的开始节点信息")
    private List<WfNodeDefDto> startNodeDefDtoList;

    /**
     * endNodeDefDtoList
     * camunda 子流程中的结束节点信息
     */
    @ApiModelProperty(value="子流程中的结束节点信息")
    private List<WfNodeDefDto> endNodeDefDtoList;

    /**
     * userTaskNodeDefDtoList
     * camunda 子流程中的UserTask节点信息
     */
    @ApiModelProperty(value="子流程中的UserTask节点信息")
    private List<WfNodeDefDto> userTaskNodeDefDtoList;

    /**
     * gatewayNodeDefDtoList
     * camunda 子流程中的网关信息
     */
    @ApiModelProperty(value="子流程中的网关信息")
    private List<WfNodeDefDto> gatewayNodeDefDtoList;

    /**
     * gatewayNodeDefDtoList
     * camunda 子流程中的序列流信息
     */
    @ApiModelProperty(value="子流程中的序列流信息")
    private List<WfSequDefDto> sequenceFlowDtoList;

    /**
     * camunda_parent_node_def_id
     * camunda 父级子流程nodeId
     */
    @ApiModelProperty(value="父级子流程nodeId")
    private String camundaParentNodeDefId;

    /**
     *
     * 子流程和主流程的关系信息
     */
    @ApiModelProperty(value="父级子流程nodeId")
    private WfProcDefRelDto wfProcDefRel;

    /**
     *
     * 外嵌子流程信息
     */
    @ApiModelProperty(value="外嵌子流程信息")
    private List<WfNodeDefDto> callActivityNodeDefDtoList;

    //-----------------4.0------------------------------
    /**
     * 表单信息
     */
    @ApiModelProperty(value="表单信息")
    private List<WfFormDefDto> wfFormDefDtos;

    /**
     * is_change
     * 是否变化
     */
    @ApiModelProperty(value="是否变化")
    private Integer isChange;

}
