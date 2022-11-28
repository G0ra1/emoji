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
import java.util.List;

/**
 * @Description 人员表达式定义 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-07-14 11:48:32
 */
@Data
@ApiModel(value = "人员表达式定义 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WfExpreUserDefDto extends IDto{

    /**
     * node_id
     * 节点ID
     */
    @ApiModelProperty(value="节点ID")
    @Valid(length = 20)
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long nodeDefId;

    /**
     * node_type
     * 节点类型
     */
    @ApiModelProperty(value="节点类型")
    @Valid(length = 2) 
    private Integer nodeType;

//    /**
//     * expre_id
//     * 表达式id
//     */
//    @ApiModelProperty(value="表达式id")
//    @Valid(length = 20)
//    @JsonDeserialize(using = IdTypeDeserializer.class)
//    private Long expreId;

    /**
     * expression
     * 表达式的值
     */
    @ApiModelProperty(value="表达式的值")
    @Valid(length = 255) 
    private String expression;

    /**
     * expression_name
     * 表达式名称
     */
    @ApiModelProperty(value="表达式名称")
    private String expressionName;

    /**
     * procdef_id
     * 流程定义ID
     */
    @ApiModelProperty(value="流程定义ID")
    @JsonDeserialize(using = IdTypeDeserializer.class)
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
     * camunda_node_def_id
     * camunda节点ID
     */
    @ApiModelProperty(value="camunda节点ID")
    private String camundaNodeDefId;

    /**
     * wfExpreUserParamDefDtoList
     * 解析xml 对应的 参数列表
     */
    @ApiModelProperty(value="表达式的值")
    @Valid(length = 255)
    private List<WfExpreUserParamDefDto> wfExpreUserParamDefDtoList;

    /**
     * biz_type
     * 业务基础类型
     */
    @ApiModelProperty(value="业务基础类型")
    private String bizType;

    /**
     * biz_id
     * 对应业务基础类型id
     */
    @ApiModelProperty(value="对应业务基础类型id")
    private String bizId;

}
