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
 * @Description 流程定义-序列流-事件-参数 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-22 16:56:16
 */
@Data
@ApiModel(value = "流程定义-序列流-事件-参数 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WfSequEventParamDefDto extends IDto{

    /**
     * event_def_id
     * 事事件定义ID件ID
     */
    @ApiModelProperty(value="事件定义ID")
    @Valid(length = 20)
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long eventDefId;

    /**
     * param_type
     * 参数类型
     */
    @ApiModelProperty(value="参数类型")
    @Valid(length = 50) 
    private String paramType;

    /**
     * param_id
     * 变量
     */
    @ApiModelProperty(value="变量")
    @Valid(length = 100) 
    private String paramId;

    /**
     * param_name
     * 变量名称
     */
    @ApiModelProperty(value="变量名称")
    @Valid(length = 100) 
    private String paramName;

    /**
     * param_defalut_value
     * 默认值
     */
    @ApiModelProperty(value="默认值")
    private String paramDefalutValue;

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
     * camunda_sequ_def_id
     * camunda节点ID
     */
    @ApiModelProperty(value="camunda节点ID")
    private String camundaSequDefId;

}
