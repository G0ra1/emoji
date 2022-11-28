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
 * @Description 流程定义-按钮 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-15 22:26:53
 */
@Data
@ApiModel(value = "流程定义-按钮 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WfButtonDefDto extends IDto{

    @ApiModelProperty(value="按钮code")
    private String buttonCode;
    /**
     * button_name
     * 按钮name
     */
    @ApiModelProperty(value="按钮name")
    @Valid(length = 50) 
    private String buttonName;

    /**
     * node_def_id
     * 节点定义Id
     */
    @ApiModelProperty(value="节点定义Id")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long nodeDefId;

    /**
     * procdef_id
     * 流程定义id
     */
    @ApiModelProperty(value="流程定义id")
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
}
