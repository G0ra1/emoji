package com.netwisd.base.msg.entity;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.netwisd.base.msg.mq.ProducerConfig;
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

@Data
@Table(value = "incloud_base_msg_producer", comment = "生产者")
@ApiModel(value = "生产者 Entity")
@TableName("incloud_base_msg_producer")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Producer extends IModel<Producer> {

    @ApiModelProperty(value = "mq类型")
    @TableField(value = "mq")
    @Column(length = 100, comment = "mq类型")
    private String mq;

    @ApiModelProperty(value = "host")
    @TableField(value = "host")
    @Column(length = 256, comment = "host")
    private String host;

    @ApiModelProperty(value = "port")
    @TableField(value = "port")
    @Column(type = DataType.INT, length = 11, comment = "port")
    private int port;

    @ApiModelProperty(value = "username")
    @TableField(value = "username")
    @Column(length = 128, comment = "username")
    private String username;

    @ApiModelProperty(value = "password")
    @TableField(value = "password")
    @Column(length = 128, comment = "password")
    private String password;

    @ApiModelProperty(value = "virtual_host")
    @TableField(value = "virtual_host")
    @Column(length = 128, comment = "virtual_host")
    private String virtualHost;

    @ApiModelProperty(value = "queue_name")
    @TableField(value = "queue_name")
    @Column(length = 128, comment = "queue_name")
    private String queueName;

    @ApiModelProperty(value = "生产者组名")
    @TableField(value = "producer_group")
    @Column(length = 128, comment = "生产者组名")
    private String producerGroup;

    @ApiModelProperty(value = "topic")
    @TableField(value = "topic")
    @Column(length = 128, comment = "topic")
    private String topic;

    @ApiModelProperty(value = "tag")
    @TableField(value = "tag")
    @Column(length = 128, comment = "tag")
    private String tag;

    @ApiModelProperty(value = "发送消息超时时间")
    @TableField(value = "send_msg_timeout")
    @Column(type = DataType.INT, length = 1, defaultValue = "3000", comment = "发送消息超时时间")
    private Integer sendMsgTimeout;

    @ApiModelProperty(value = "状态")
    @TableField(value = "status")
    @Column(type = DataType.INT, length = 1, defaultValue = "0", comment = "状态")
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
