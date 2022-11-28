package com.netwisd.base.common.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "数据建模表单 Vo")
public class ModelFormVo extends IVo {

    @ApiModelProperty(value = "表单名称")
    private String formName;

    @ApiModelProperty(value = "表单名称")
    private String formNameCh;

    @ApiModelProperty(value = "表单类型")
    private Integer formType;

    @ApiModelProperty(value = "表单内容")
    private String formContent;

    @ApiModelProperty(value = "模型Id")
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long modelingId;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "模型分类ID")
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long modelTypeId;

    @ApiModelProperty(value = "模型分类code")
    private String modelTypeCode;

    @ApiModelProperty(value = "模型分类名称")
    private String modelTypeName;

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
    private List<ModelFormButtonVo> buttonList = new ArrayList<>();

    @ApiModelProperty(value = "表单字段")
    private List<ModelingFieldVo> fieldList = new ArrayList<>();
}
