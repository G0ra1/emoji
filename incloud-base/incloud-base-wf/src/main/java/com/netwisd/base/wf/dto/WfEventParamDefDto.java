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
 * @Description 事件定义参数维护 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-13 17:00:37
 */
@Data
@ApiModel(value = "事件定义参数维护 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WfEventParamDefDto extends IDto{

    /**
     * event_def_id
     * 事件def id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="事件def id")
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
     * 字典参数id
     */
    @ApiModelProperty(value="字典参数id")
    @Valid(length = 100) 
    private String paramId;

    /**
     * var_defalut_value
     * 默认值
     */
    @ApiModelProperty(value="默认值")
    private String paramValue;

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
     * node_def_id
     * 节点定义ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="节点定义ID")
    private Long nodeDefId;

}
