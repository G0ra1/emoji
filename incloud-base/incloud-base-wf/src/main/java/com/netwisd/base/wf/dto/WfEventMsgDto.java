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
 * @Description 流程定义消息提醒 功能描述...
 * @author 云数网讯 lihongyu@netwisd.com
 * @date 2020-07-23 11:15:59
 */
@Data
@ApiModel(value = "流程定义消息提醒 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WfEventMsgDto extends IDto{

    /**
     * procdef_id
     * 流程定义ID
     */
    @ApiModelProperty(value="流程定义ID")
    @Valid(length = 20)
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long procdefId;

    /**
     * event_type
     * 事件提醒类型
     */
    @ApiModelProperty(value="事件提醒类型")
    @Valid(length = 2) 
    private Integer eventType;

    /**
     * event_handle_type
     * 事件处理类型
     */
    @ApiModelProperty(value="事件处理类型")
    @Valid(length = 2) 
    private Integer eventHandleType;

    /**
     * node_id
     * 节点id
     */
    @ApiModelProperty(value="节点id")
    @Valid(length = 64)
    private String nodeId;

    /**
     * node_type
     * 节点类型
     */
    @ApiModelProperty(value="节点类型")
    @Valid(length = 2) 
    private Integer nodeType;

    /**
     * msg
     * 消息体
     */
    @ApiModelProperty(value="消息体")
    @Valid(length = 255) 
    private String msg;

    /**
     * is_read
     * 是否已读
     */
    @ApiModelProperty(value="是否已读")
    @Valid(length = 1) 
    private Integer isRead;

}
