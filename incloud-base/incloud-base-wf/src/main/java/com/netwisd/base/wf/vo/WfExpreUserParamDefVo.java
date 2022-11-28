package com.netwisd.base.wf.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 人员表达式定义 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-07-14 11:49:34
 */
@Data
@ApiModel(value = "人员表达式定义 Vo")
public class WfExpreUserParamDefVo extends IVo{
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
    /**
     * expre_param_value
     * 表达式或过程变量或表单对应的参数值
     */
    @ApiModelProperty(value="表达式或过程变量或表单对应的参数值")
    private String expreParamValue;
    /**
     * expre_param_source
     * 参数来源
     */
    @ApiModelProperty(value="参数来源")
    private String expreParamSource;

    /**
     * expre_user_def_id
     * 表达式defID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="表达式defID")
    private Long expreUserDefId;

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
     * expre_param_var_type
     * 参数类型
     */
    @ApiModelProperty(value="参数类型")
    private String expreParamVarType;

//    /**
//     * form_id
//     * 业务表单id
//     */
//    @JsonDeserialize(using = IdTypeDeserializer.class)
//    @ApiModelProperty(value="业务表单id")
//    private Long formId;

    /**
     * expre_param_name
     * 参数字段名称
     */
    @ApiModelProperty(value="参数字段名称")
    private String expreParamName;

    /**
     * expre_param_desc
     * 参数字段描述
     */
    @ApiModelProperty(value="参数字段描述")
    private String expreParamDesc;
}
