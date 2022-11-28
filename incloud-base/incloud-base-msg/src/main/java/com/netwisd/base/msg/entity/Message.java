package com.netwisd.base.msg.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.msg.dto.MessageDto;
import com.netwisd.base.common.msg.vo.MessageVo;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.anntation.Table;
import com.netwisd.common.db.data.DataType;
import com.netwisd.common.db.data.IModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Table(value = "incloud_base_msg_message", comment = "消息")
@ApiModel(value = "消息 Entity")
@TableName("incloud_base_msg_message")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Message extends IModel<Message> {

    @ApiModelProperty(value = "模板Code")
    @TableField(value = "tmp_code")
    @Column(length = 100, comment = "模板Code")
    private String tmpCode;

    @ApiModelProperty(value = "标题")
    @TableField(value = "msg_title")
    @Column(length = 1024, comment = "消息标题")
    private String msgTitle;

    @ApiModelProperty(value = "消息内容参数")
    @TableField(value = "msg_params")
    @Column(length = 1024, comment = "消息内容参数")
    private String msgParams;

    @ApiModelProperty(value = "消息内容")
    @TableField(value = "msg_content")
    @Column(length = 2048, comment = "消息内容")
    private String msgContent;

    @ApiModelProperty(value = "发送人")
    @TableField(value = "send_user_id")
    @Column(length = 20, type = DataType.BIGINT, comment = "发送人")
    private Long sendUserId;

    @ApiModelProperty(value = "发送人")
    @TableField(value = "send_user_name")
    @Column(length = 128, comment = "发送人")
    private String sendUserName;

    @ApiModelProperty(value = "接收人")
    @TableField(value = "receiver_user_id")
    @Column(length = 20, type = DataType.BIGINT, comment = "接收人")
    private Long receiverUserId;

    @ApiModelProperty(value = "接收人")
    @TableField(value = "receiver_user_name")
    @Column(length = 128, comment = "接收人")
    private String receiverUserName;

    @ApiModelProperty(value = "send_time")
    @TableField(value = "send_time")
    @Column(type = DataType.DATETIME, length = 0, comment = "发送时间")
    public LocalDateTime sendTime;

    @ApiModelProperty(value = "消息PC地址")
    @TableField(value = "msg_pc_url")
    @Column(length = 2048, comment = "消息PC地址")
    private String msgPcUrl;

    @ApiModelProperty(value = "消息H5地址")
    @TableField(value = "msg_h5_url")
    @Column(length = 2048, comment = "消息H5地址")
    private String msgH5Url;

    @ApiModelProperty(value = "消息类型")
    @TableField(value = "msg_type")
    @Column(length = 1, type = DataType.INT, comment = "消息类型")
    private Integer msgType;

    @ApiModelProperty(value = "读取状态")
    @TableField(value = "is_read")
    @Column(length = 1, type = DataType.INT, defaultValue = "0", isNull = false, comment = "读取状态")
    private Integer isRead;

    @ApiModelProperty(value = "read_time")
    @TableField(value = "read_time")
    @Column(type = DataType.DATETIME, length = 0, comment = "读取时间")
    public LocalDateTime readTime;

    @ApiModelProperty(value = "消息来源")
    @TableField(value = "msg_source")
    @Column(length = 127, comment = "消息来源")
    private String msgSource;

    public void toMessage(MessageDto messageDto) {
        this.setTmpCode(messageDto.getTmpCode());
        this.setMsgParams(messageDto.getMsgParams());
        this.setSendTime(LocalDateTime.now());
        this.setMsgPcUrl(messageDto.getMsgPcUrl());
        this.setMsgH5Url(messageDto.getMsgH5Url());
        this.setMsgSource(messageDto.getMsgSource());
        //todo this.setMsgType();
    }

    public MessageVo toMessageVo() {
        MessageVo messageVo = new MessageVo();
        messageVo.setId(this.id);
        messageVo.setMsgTitle(this.msgTitle);
        messageVo.setMsgContent(this.msgContent);
        messageVo.setSendUserName(this.sendUserName);
        messageVo.setReceiverUserName(this.receiverUserName);
        messageVo.setMsgPcUrl(this.msgPcUrl);
        messageVo.setMsgH5Url(this.msgH5Url);
        messageVo.setIsRead(YesNo.NO.getCode());
        messageVo.setMsgSource(this.msgSource);
        messageVo.setSendTime(this.sendTime);
        return messageVo;
    }
}
