package com.netwisd.base.msg.dto;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.netwisd.base.common.data.IDto;
import com.netwisd.base.msg.mq.ProducerConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "生产者 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProducerDto extends IDto {

    @ApiModelProperty(value = "mq类型")
    private String mq;

    @ApiModelProperty(value = "host")
    private String host;

    @ApiModelProperty(value = "port")
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

    public ProducerConfig toProducerConifg() {
        ProducerConfig producerConfig = new ProducerConfig();
        producerConfig.setProducerGroup(StrUtil.isNotEmpty(this.getProducerGroup()) ? this.getProducerGroup() : "");
        producerConfig.setSendMsgTimeout(ObjectUtil.isNotEmpty(this.getSendMsgTimeout()) ? this.getSendMsgTimeout() : 3000);
        producerConfig.setHost(StrUtil.isNotEmpty(this.getHost()) ? this.getHost() : "");
        producerConfig.setPort(ObjectUtil.isNotEmpty(this.getPort()) ? this.getPort() : 0);
        producerConfig.setTag(StrUtil.isNotEmpty(this.getTag()) ? this.getTag() : "");
        producerConfig.setTopic(StrUtil.isNotEmpty(this.getTopic()) ? this.getTopic() : "");
        producerConfig.setVirtualHost(StrUtil.isNotEmpty(this.getVirtualHost()) ? this.getVirtualHost() : "");
        producerConfig.setUsername(StrUtil.isNotEmpty(this.getUsername()) ? this.getUsername() : "");
        producerConfig.setPassword(StrUtil.isNotEmpty(this.getPassword()) ? this.getPassword() : "");
        producerConfig.setQueueName(StrUtil.isNotEmpty(this.getQueueName()) ? this.getQueueName() : "");
        return producerConfig;
    }
}
