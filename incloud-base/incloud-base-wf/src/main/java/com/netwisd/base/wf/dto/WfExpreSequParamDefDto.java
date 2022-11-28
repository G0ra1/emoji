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

/**
 * @Description 流程定义-序列流-表达式-参数 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-21 19:00:23
 */
@Data
@ApiModel(value = "流程定义-序列流-表达式-参数 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WfExpreSequParamDefDto extends IDto{

    /**
     * expre_param_id
     * 表达式参数ID
     */
    @ApiModelProperty(value="表达式参数ID")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long expreParamId;

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
    
    private Integer expreParamSource;

    /**
     * expre_sequ_def_id
     * 序列流 表达式def id
     */
    @ApiModelProperty(value="序列流 表达式def id")
    @Valid(length = 20)
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long expreSequDefId;

    /**
     * camunda_sequ_id
     * camunda序列流ID
     */
    @ApiModelProperty(value="camunda序列流ID")
    private String camundaSequId;
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

}
