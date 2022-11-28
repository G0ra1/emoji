package com.netwisd.base.model.dto;

import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.anntation.Valid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "数据建模字段 Dto")
public class ModelFieldDto extends IDto {

    @ApiModelProperty(value = "实体Id")
    private Long entityId;

    @ApiModelProperty(value = "实体名称")
    private String entityName;

    @ApiModelProperty(value = "字段名称")
    @Valid(nullMsg = "字段名称为空")
    private String name;

    @ApiModelProperty(value = "字段中文名称")
    @Valid(nullMsg = "字段名称为空")
    private String nameCh;

    @ApiModelProperty(value = "数据库类型")
    @Valid(nullMsg = "数据库类型为空")
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

    @ApiModelProperty(value = "默认值")
    private String defaultValue;

    @ApiModelProperty(value = "关联表ID")
    private Long fkTableId;

    @ApiModelProperty(value = "关联表名称")
    private String fkTableName;

    @ApiModelProperty(value = "关联表字段ID")
    private Long fkFieldId;

    @ApiModelProperty(value = "关联表字段名称")
    private String fkFieldName;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "表格操作类型")
    private String operateType;//add del update

    @ApiModelProperty(value = "列类型")
    private String columnType;
}
