package com.netwisd.base.msg.dto;

import com.netwisd.common.core.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "消息模板Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MsgTemplateDto extends IDto {

    @ApiModelProperty(value = "模板Code")
    private String tmpCode;

    @ApiModelProperty(value = "模板名称")
    private String tmpName;

    @ApiModelProperty(value = "模板内容")
    private String tmpContent;

    @ApiModelProperty(value = "模板标题")
    private String tmpTitle;
}
