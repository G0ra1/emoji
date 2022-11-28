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
@ApiModel(value = "数据建模实体字段 Vo")
public class ModelingFieldVo extends IVo {

    @ApiModelProperty(value = "模型实体Id")
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long modelingEntityId;

    @ApiModelProperty(value = "模型名称")
    private String modelingEntityName;

    @ApiModelProperty(value = "实体Id")
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long entityId;

    @ApiModelProperty(value = "模型实体字段Id")
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long entityFieldId;

    @ApiModelProperty(value = "字段名称")
    private String name;

    @ApiModelProperty(value = "字段Java名称")
    private String javaName;

    @ApiModelProperty(value = "字段中文名称")
    private String nameCh;

    @ApiModelProperty(value = "数据库类型")
    private String dbType;

    @ApiModelProperty(value = "长度")
    private Integer length;

    @ApiModelProperty(value = "精度")
    private Integer precision;

    @ApiModelProperty(value = "是否不为空")
    private Integer isNotNull;

    @ApiModelProperty(value = "是否主键")
    private Integer isKey;

    @ApiModelProperty(value = "是否唯一")
    private Integer isUnique;

    @ApiModelProperty(value = "关联表名称")
    private String fkTableName;

    @ApiModelProperty(value = "关联表字段名称")
    private String fkFieldName;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "是否选中")
    private Boolean isSelect = Boolean.FALSE;

    @ApiModelProperty(value = "子表存储集合Key")
    private String childTableSetKey;
}
