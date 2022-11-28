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
@ApiModel(value = "数据建模实体 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ModelingEntityDto extends IDto {

    @ApiModelProperty(value = "模型Id")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long modelId;

    @ApiModelProperty(value = "模型名称")
    private String modelName;

    @ApiModelProperty(value = "模型中文名称")
    private String modelNameCh;

    @ApiModelProperty(value = "模型分类ID")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long modelTypeId;

    @ApiModelProperty(value = "模型分类code")
    private String modelTypeCode;

    @ApiModelProperty(value = "模型分类名称")
    private String modelTypeName;

    @ApiModelProperty(value = "建模实体Id(表实体Id)")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Valid(nullMsg = "实体为空")
    private Long entityId;

    @ApiModelProperty(value = "父模型Id(表实体Id)")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Valid(nullMsg = "父实体为空")
    private Long parentEntityId;

    @ApiModelProperty(value = "表名")
    @Valid(nullMsg = "表名为空")
    private String tableName;

    @ApiModelProperty(value = "表中文名")
    @Valid(nullMsg = "表中文名为空")
    private String tableNameCh;

    @ApiModelProperty(value = "子表存储集合Key")
    private String childTableSetKey;

    @ApiModelProperty(value = "建模实体字段信息")
    @Valid(nullMsg = "请选择建模实体字段信息")
    private List<ModelingFieldDto> modelingFieldList;
}
