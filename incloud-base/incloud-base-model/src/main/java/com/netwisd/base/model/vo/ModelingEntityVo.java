package com.netwisd.base.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Lists;
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
@ApiModel(value = "数据建模实体 Vo")
public class ModelingEntityVo extends IVo {

    @ApiModelProperty(value = "模型Id")
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long modelId;

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

    @ApiModelProperty(value = "建模实体Id(表实体Id)")
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long entityId;

    @ApiModelProperty(value = "父模型Id(表实体Id)")
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long parentEntityId;

    @ApiModelProperty(value = "级别")
    private Integer level;

    @ApiModelProperty(value = "表名")
    private String tableName;

    @ApiModelProperty(value = "表中文名")
    private String tableNameCh;

    @ApiModelProperty(value = "子表存储集合Key")
    private String childTableSetKey;

    @ApiModelProperty(value = "是否选中")
    private Boolean isSelect = Boolean.FALSE;

    @ApiModelProperty(value = "数据模型实体信息")
    private List<ModelingEntityVo> modelingEntityList = Lists.newArrayList();

    @ApiModelProperty(value = "数据模型实体字段信息")
    private List<ModelingFieldVo> modelingFieldList = Lists.newArrayList();
}
