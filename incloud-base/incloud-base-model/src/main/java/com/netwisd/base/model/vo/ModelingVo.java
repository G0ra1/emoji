package com.netwisd.base.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.base.common.model.vo.ModelingFieldVo;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "数据建模 Vo")
public class ModelingVo extends IVo {

    @ApiModelProperty(value = "模型名称")
    private String modelName;

    @ApiModelProperty(value = "模型中文名称")
    private String modelNameCh;

    @ApiModelProperty(value = "模型分类ID")
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long modelTypeId;

    @ApiModelProperty(value = "模型分类code")
    private String modelTypeCode;

    @ApiModelProperty(value = "模型分类名称")
    private String modelTypeName;

    @ApiModelProperty(value = "模型特性")
    private Integer modelProperty;

    @ApiModelProperty(value = "主实体ID")
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long entityId;

    @ApiModelProperty(value = "主实体名称")
    private String entityName;

    @ApiModelProperty(value = "主实体中文名")
    private String entityNameCh;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "模型字段列表")
    private List<ModelingFieldVo> modelingFieldList;
}
