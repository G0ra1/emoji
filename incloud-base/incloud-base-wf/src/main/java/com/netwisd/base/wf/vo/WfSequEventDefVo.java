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
 * @Description 流程定义-序列流-事件 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-22 16:55:36
 */
@Data
@ApiModel(value = "流程定义-序列流-事件 Vo")
public class WfSequEventDefVo extends IVo{
    /**
     * event_bind_type
     * 事件绑定类型
     */
    @ApiModelProperty(value="事件绑定类型")
    private String eventBindType;
    /**
     * event_id
     * 事件字典定义id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="事件字典定义id")
    private Long eventId;
    /**
     * procdef_id
     * 流程定义ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="流程定义ID")
    private Long procdefId;
    /**
     * sequ_def_id
     * 节点定义ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="节点定义ID")
    private Long sequDefId;
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
