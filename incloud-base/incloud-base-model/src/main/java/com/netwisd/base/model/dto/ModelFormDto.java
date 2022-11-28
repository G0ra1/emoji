package com.netwisd.base.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.util.IdTypeDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@ApiModel(value = "数据建模表单 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ModelFormDto extends IDto {

    @ApiModelProperty(value = "表单名称")
    @Valid(nullMsg = "表单名称为空")
    private String formName;

    @ApiModelProperty(value = "表单名称")
    @Valid(nullMsg = "表单名称为空")
    private String formNameCh;

    @ApiModelProperty(value = "表单类型")
    @Valid(nullMsg = "表单类型为空")
    private Integer formType;

    @ApiModelProperty(value = "模型Id")
    @Valid(nullMsg = "模型Id为空")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long modelingId;

    @ApiModelProperty(value = "状态")
    @Valid(nullMsg = "状态为空")
    private Integer status;

    @ApiModelProperty(value = "模型分类ID")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Valid(nullMsg = "模型分类为空")
    private Long modelTypeId;

    @ApiModelProperty(value = "模型分类code")
    @Valid(nullMsg = "模型分类为空")
    private String modelTypeCode;

    @ApiModelProperty(value = "模型分类名称")
    @Valid(nullMsg = "模型分类为空")
    private String modelTypeName;

    @ApiModelProperty(value = "表单内容")
    private String formContent;

    @ApiModelProperty(value = "表单保存URL")
    private String formSaveUrl;

    @ApiModelProperty(value = "表单删除URL")
    private String formDeleteUrl;

    @ApiModelProperty(value = "表单更新URL")
    private String formUpdateUrl;

    @ApiModelProperty(value = "表单获取URL")
    private String formGetUrl;

    @ApiModelProperty(value = "表单保存URL类型")
    private String formSaveMethodType;

    @ApiModelProperty(value = "表单删除URL类型")
    private String formDeleteMethodType;

    @ApiModelProperty(value = "表单更新URL类型")
    private String formUpdateMethodType;

    @ApiModelProperty(value = "表单获取URL类型")
    private String formGetMethodType;

    @ApiModelProperty(value = "外联表单地址")
    private String pageUrl;

    @ApiModelProperty(value = "表单按钮")
    private List<ModelFormButtonDto> buttonList;
}
