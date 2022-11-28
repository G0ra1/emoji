package com.netwisd.base.common.msg.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "消息 Vo")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MessageVo extends IVo {

    @ApiModelProperty(value = "模板Code")
    private String tmpCode;

    @ApiModelProperty(value = "标题")
    private String msgTitle;

    @ApiModelProperty(value = "消息内容参数")
    private String msgParams;

    @ApiModelProperty(value = "消息内容")
    private String msgContent;

    @ApiModelProperty(value = "发送人")
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long sendUserId;

    @ApiModelProperty(value = "发送人")
    private String sendUserName;

    @ApiModelProperty(value = "接收人")
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long receiverUserId;

    @ApiModelProperty(value = "接收人")
    private String receiverUserName;

    @ApiModelProperty(value = "send_time")
    public LocalDateTime createTime;

    @ApiModelProperty(value = "消息地址")
    private String msgPcUrl;

    @ApiModelProperty(value = "消息地址")
    private String msgH5Url;

    @ApiModelProperty(value = "消息类型")
    private Integer msgType;

    @ApiModelProperty(value = "读取状态")
    private Integer isRead;

    @ApiModelProperty(value = "读取时间")
    public LocalDateTime readTime;

    @ApiModelProperty(value = "发送时间")
    public LocalDateTime sendTime;

    @ApiModelProperty(value = "消息来源")
    private String msgSource;
}
