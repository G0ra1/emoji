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
 * @Description 流程定义-序列流 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-21 16:21:33
 */
@Data
@ApiModel(value = "流程定义-序列流 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WfSequDefDto extends IDto {

    /**
     * camundaSequId
     * camunda序列流ID
     */
    @ApiModelProperty(value = "camunda序列流ID")
    @Valid(length = 50)
    private String camundaSequId;

    /**
     * sequ_name
     * camunda节点名称
     */
    @ApiModelProperty(value = "camunda节点名称")
    @Valid(length = 50)
    private String sequName;

    /**
     * procdef_id
     * 流程定义ID
     */
    @ApiModelProperty(value = "流程定义ID")
    @Valid(length = 20)
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long procdefId;

    /**
     * camunda_procdef_id
     * camunda流程定义ID
     */
    @ApiModelProperty(value = "camunda流程定义ID")
    @Valid(length = 64)
    private String camundaProcdefId;

    /**
     * camunda_procdef_key
     * camunda流程定义key
     */
    @ApiModelProperty(value = "camunda流程定义key")
    @Valid(length = 50)
    private String camundaProcdefKey;

    /**
     * expression
     * 网关表达式
     */
    @ApiModelProperty(value = "网关表达式")

    private String expression;

    /**
     * expression_name
     * 网关表达式名称
     */
    @ApiModelProperty(value = "网关表达式名称")
    private String expressionName;

    /**
     * wfExpreSequDefDtoList
     * 序列流的表达式列表
     */
    @ApiModelProperty(value = "序列流的表达式列表")
    private List<WfExpreSequDefDto> wfExpreSequDefDtoList;

    /**
     * wfExpreSequDefDtoList
     * 序列流的变量列表
     */
    @ApiModelProperty(value = "序列流的变量列表")
    private List<WfVarDefDto> wfVarDefDtoList;
    /**
     * wfExpreSequDefDtoList
     * 序列流的事件列表
     */
    @ApiModelProperty(value = "序列流的事件列表")
    private List<WfSequEventDefDto> wfSequEventDefDtoList;

    /**
     * parent_id
     * camunda 父级子流程nodeId
     */
    @ApiModelProperty(value="父级子流程nodeId")
    private String camundaParentNodeDefId;

    /**
     * is_change
     * 是否变化
     */
    @ApiModelProperty(value="是否变化")
    private Integer isChange;
}