package com.netwisd.base.common.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "数据建模表单按钮 Vo")
public class ModelFormButtonVo extends IVo {

    @ApiModelProperty(value = "表单Id")
    @JsonSerialize(using = IdTypeSerializer.class)
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
