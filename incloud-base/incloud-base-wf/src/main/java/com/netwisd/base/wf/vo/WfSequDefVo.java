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
 * @Description 流程定义-序列流 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-21 16:21:33
 */
@Data
@ApiModel(value = "流程定义-序列流 Vo")
public class WfSequDefVo extends IVo{
    /**
     * camunda_sequ_id
     * camunda节点ID
     */
    @ApiModelProperty(value="camunda节点ID")
    private String camundaSequId;
    /**
     * sequ_name
     * camunda节点名称
     */
    @ApiModelProperty(value="camunda节点名称")
    private String sequName;
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
     * expression
     * 网关表达式
     */
    @ApiModelProperty(value="网关表达式")
    private String expression;
    /**
     * expression_name
     * 网关表达式名称
     */
    @ApiModelProperty(value="网关表达式名称")
    private String expressionName;
    /**
     * camunda_parent_node_def_id
     * camunda 父级子流程nodeId
     */
    @ApiModelProperty(value="父级子流程nodeId")
    private String camundaParentNodeDefId;
}
