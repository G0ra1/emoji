package com.netwisd.base.msg.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "生产者 Vo")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProducerVo extends IVo {

    @ApiModelProperty(value = "mq类型")
    private String mq;

    @ApiModelProperty(value = "mqIp")
    private String host;

    @ApiModelProperty(value = "mqPort")
    private int port;

    @ApiModelProperty(value = "username")
    private String username;

    @ApiModelProperty(value = "password")
    private String password;

    @ApiModelProperty(value = "virtual_host")
    private String virtualHost;

    @ApiModelProperty(value = "queue_name")
    private String queueName;

    @ApiModelProperty(value = "生产者组名")
    private String producerGroup;

    @ApiModelProperty(value = "topic")
    private String topic;

    @ApiModelProperty(value = "tag")
    private String tag;

    @ApiModelProperty(value = "发送消息超时时间")
    private Integer sendMsgTimeout;

    @ApiModelProperty(value = "状态")
    private Integer status;
}
