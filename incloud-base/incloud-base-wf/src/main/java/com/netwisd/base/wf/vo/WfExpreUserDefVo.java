package com.netwisd.base.wf.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description 人员表达式定义 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-07-14 11:48:32
 */
@Data
@ApiModel(value = "人员表达式定义 Vo")
public class WfExpreUserDefVo extends IVo{
    /**
     * node_id
     * 节点ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="节点ID")
    private Long nodeDefId;
    /**
     * node_type
     * 节点类型
     */
    @ApiModelProperty(value="节点类型")
    private Integer nodeType;
//    /**
//     * expre_id
//     * 表达式id
//     */
//    @JsonSerialize(using = IdTypeSerializer.class)
//    @ApiModelProperty(value="表达式id")
//    private Long expreId;
    /**
     * expression
     * 表达式的值
     */
    @ApiModelProperty(value="表达式的值")
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
     * camunda_node_def_id
     * camunda节点ID
     */
    @ApiModelProperty(value="camunda节点ID")
    private String camundaNodeDefId;

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
