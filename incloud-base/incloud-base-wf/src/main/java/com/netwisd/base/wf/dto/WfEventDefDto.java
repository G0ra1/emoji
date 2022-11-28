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
 * @Description 事件定义 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-15 16:45:46
 */
@Data
@ApiModel(value = "事件定义 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WfEventDefDto extends IDto{

    /**
     * event_type
     * 事件分类
     */
    @ApiModelProperty(value="事件分类")
    private Integer eventType;


    /**
     * event_bind_type
     * 事件绑定类型
     */
    @ApiModelProperty(value="事件绑定类型")
    @Valid(length = 2) 
    private String eventBindType;

    /**
     * event_id
     * 事件定义id
     */
    @ApiModelProperty(value="事件字典定义id")
    @Valid(length = 20)
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long eventId;

    /**
     * event_submit_sign
     * 完成前事件影响提交标识
     */
    @ApiModelProperty(value="完成前事件影响提交标识")
    @Valid(length = 1) 
    private Integer eventSubmitSign;

    /**
     * procdef_id
     * 流程定义ID
     */
    @ApiModelProperty(value="流程定义ID")
    @Valid(length = 20)
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long procdefId;

    /**
     * node_def_id
     * 节点定义ID
     */
    @ApiModelProperty(value="节点定义ID")
    @Valid(length = 20)
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long nodeDefId;

    /**
     * event_type_sign
     * 事件所属节点类型(流程定义事件、网关事件、节点事件)
     */
    @ApiModelProperty(value="事件所属节点类型(流程定义事件、网关事件、节点事件)")
    private Integer eventTypeSign;

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
     * 事件参数列表
     */
    @ApiModelProperty(value="事件参数列表")
    List<WfEventParamDefDto> wfEventParamDefDto;

    /**
     * netwisd_order
     * 事件顺序
     */
    @ApiModelProperty(value="事件顺序")
    private Integer netwisdOrder;

}
