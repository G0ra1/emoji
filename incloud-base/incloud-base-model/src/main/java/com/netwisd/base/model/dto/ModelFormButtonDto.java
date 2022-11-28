package com.netwisd.base.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.util.IdTypeDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "数据建模表单按钮 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ModelFormButtonDto extends IDto {

    @ApiModelProperty(value = "表单Id")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long modelFormId;

    @ApiModelProperty(value = "表单名称")
    private String modelFormName;

    @ApiModelProperty(value = "表单名称")
    private String modelFormNameCh;

    @ApiModelProperty(value = "按钮Code")
    private String buttonCode;

    @ApiModelProperty(value = "按钮名称")
    private String buttonName;

    @ApiModelProperty(value = "按钮类型")
    private Integer buttonType;

    @ApiModelProperty(value = "按钮执行url")
    private String buttonUrl;

    @ApiModelProperty(value = "按钮执行方法类型")
    private String buttonExcMethodType;

}
