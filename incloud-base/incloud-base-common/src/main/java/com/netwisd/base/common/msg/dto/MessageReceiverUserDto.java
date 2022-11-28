package com.netwisd.base.common.msg.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "消息接受人 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MessageReceiverUserDto {

    @ApiModelProperty(value = "接收人身份证号")
    private String receiverIdcard;

    @ApiModelProperty(value = "接收人")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long receiverUserId;

    @ApiModelProperty(value = "接收人")
    private String receiverUserName;

}
