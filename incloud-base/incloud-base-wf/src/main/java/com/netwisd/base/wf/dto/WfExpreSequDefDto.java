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
import java.util.Date;
import java.util.List;

/**
 * @Description 流程定义-序列流-表达式 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-21 18:59:28
 */
@Data
@ApiModel(value = "流程定义-序列流-表达式 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WfExpreSequDefDto extends IDto{

    /**
     * expre_id
     * 表达式id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="表达式id")
    @Valid(length = 20) 
    private Long expreId;

    /**
     * expression
     * 表达式的值
     */
    @ApiModelProperty(value="表达式的值")
    
    private String expression;

    /**
     * sequ_def_id
     * def序列流ID
     */
    @ApiModelProperty(value="def序列流ID")
    @Valid(length = 20) 
    private Long sequDefId;

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

    /**
     * procdef_id
     * 流程定义id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="流程定义id")
    private Long procdefId;

    /**
     * wfExpreSequParamDefDtoList
     * 序列流的参数列表
     */
    @ApiModelProperty(value="序列流的参数列表")
    private List<WfExpreSequParamDefDto> wfExpreSequParamDefDtoList;

}
