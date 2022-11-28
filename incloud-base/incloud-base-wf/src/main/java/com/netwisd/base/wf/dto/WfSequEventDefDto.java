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
 * @Description 流程定义-序列流-事件 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-22 16:55:36
 */
@Data
@ApiModel(value = "流程定义-序列流-事件 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WfSequEventDefDto extends IDto{

    /**
     * event_bind_type
     * 事件绑定类型
     */
    @ApiModelProperty(value="事件绑定类型")
    @Valid(length = 32) 
    private String eventBindType;

    /**
     * event_id
     * 事件字典定义id
     */
    @ApiModelProperty(value="事件字典定义id")
    @Valid(length = 20)
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long eventId;

    /**
     * procdef_id
     * 流程定义ID
     */
    @ApiModelProperty(value="流程定义ID")
    @Valid(length = 20)
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long procdefId;

    /**
     * sequ_def_id
     * 节点定义ID
     */
    @ApiModelProperty(value="节点定义ID")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long sequDefId;

    /**
     * camunda_procdef_id
     * camunda流程定义ID
     */
    @ApiModelProperty(value="camunda流程定义ID")
    @Valid(length = 64) 
    private String camundaProcdefId;

    /**
     * camunda_procdef_key
     * camunda流程定义key
     */
    @ApiModelProperty(value="camunda流程定义key")
    @Valid(length = 50) 
    private String camundaProcdefKey;

    /**
     * camunda_sequ_def_id
     * camunda节点ID
     */
    @ApiModelProperty(value="camunda节点ID")
    private String camundaSequDefId;

    /**
     * wfSequEventParamDefDtoList
     *  事件参数列表
     */
    @ApiModelProperty(value="事件参数列表")
    private List<WfSequEventParamDefDto> wfSequEventParamDefDtoList;

}
