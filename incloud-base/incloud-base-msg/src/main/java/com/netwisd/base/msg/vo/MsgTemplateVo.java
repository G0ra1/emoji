package com.netwisd.base.msg.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "消息模板 Vo")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MsgTemplateVo extends IVo {

    @ApiModelProperty(value = "模板Code")
    private String tmpCode;

    @ApiModelProperty(value = "模板名称")
    private String tmpName;

    @ApiModelProperty(value = "模板内容")
    private String tmpContent;

    @ApiModelProperty(value = "模板标题")
    private String tmpTitle;
}
