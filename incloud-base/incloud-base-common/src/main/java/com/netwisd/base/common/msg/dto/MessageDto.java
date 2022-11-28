package com.netwisd.base.common.msg.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.data.IDto;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@ApiModel(value = "消息 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MessageDto extends IDto {

    @ApiModelProperty(value = "模板Code")
    private String tmpCode;

    @ApiModelProperty(value = "标题")
    private String msgTitle;

    @ApiModelProperty(value = "消息内容参数")
    private String msgParams;

    @ApiModelProperty(value = "消息内容")
    private String msgContent;

    @ApiModelProperty(value = "发送人")
    private String sendUserIdcard;

    @ApiModelProperty(value = "发送人")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long sendUserId;

    @ApiModelProperty(value = "发送人")
    private String sendUserName;

    @ApiModelProperty(value = "消息地址")
    private String msgPcUrl;

    @ApiModelProperty(value = "消息地址")
    private String msgH5Url;

    @ApiModelProperty(value = "消息类型")
    private Integer msgType;

    @ApiModelProperty(value = "读取状态")
    private Integer isRead;

    @ApiModelProperty(value = "消息来源")
    private String msgSource;

    @ApiModelProperty(value = "是否系统发送")
    private Integer isSystemSend;

    @ApiModelProperty(value = "消息接收人")
    private List<MessageReceiverUserDto> receiverUserList;
}
