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
 * @Description 流程定义消息提醒 功能描述...
 * @author 云数网讯 lihongyu@netwisd.com
 * @date 2020-07-23 11:15:59
 */
@Data
@ApiModel(value = "流程定义消息提醒 Vo")
public class WfEventMsgVo extends IVo{
    /**
     * procdef_id
     * 流程定义ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="流程定义ID")
    private Long procdefId;
    /**
     * event_type
     * 事件提醒类型
     */
    @ApiModelProperty(value="事件提醒类型")
    private Integer eventType;
    /**
     * event_handle_type
     * 事件处理类型
     */
    @ApiModelProperty(value="事件处理类型")
    private Integer eventHandleType;
    /**
     * node_id
     * 节点id
     */
    @ApiModelProperty(value="节点id")
    private String nodeId;
    /**
     * node_type
     * 节点类型
     */
    @ApiModelProperty(value="节点类型")
    private Integer nodeType;
    /**
     * msg
     * 消息体
     */
    @ApiModelProperty(value="消息体")
    private String msg;
    /**
     * is_read
     * 是否已读
     */
    @ApiModelProperty(value="是否已读")
    private Integer isRead;
}
