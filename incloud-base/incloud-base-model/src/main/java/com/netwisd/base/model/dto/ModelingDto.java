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
@ApiModel(value = "数据建模 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ModelingDto extends IDto {

    @ApiModelProperty(value = "模型名称")
    @Valid(nullMsg = "模型名称为空")
    private String modelName;

    @ApiModelProperty(value = "模型中文名称")
    @Valid(nullMsg = "模型中文名称为空")
    private String modelNameCh;

    @ApiModelProperty(value = "模型分类ID")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Valid(nullMsg = "请选择模型分类")
    private Long modelTypeId;

    @ApiModelProperty(value = "模型分类code")
    @Valid(nullMsg = "请选择模型分类")
    private String modelTypeCode;

    @ApiModelProperty(value = "模型分类名称")
    @Valid(nullMsg = "请选择模型分类")
    private String modelTypeName;

    @ApiModelProperty(value = "模型特性")
    private Integer modelProperty;

    @ApiModelProperty(value = "主实体ID")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Valid(nullMsg = "请选择实体")
    private Long entityId;

    @ApiModelProperty(value = "主实体名称")
    @Valid(nullMsg = "请选择实体")
    private String entityName;

    @ApiModelProperty(value = "主实体中文名")
    @Valid(nullMsg = "请选择实体")
    private String entityNameCh;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "数据建模实体信息")
    @Valid(nullMsg = "请选择数据建模实体信息")
    private List<ModelingEntityDto> modelingEntityList;
}
