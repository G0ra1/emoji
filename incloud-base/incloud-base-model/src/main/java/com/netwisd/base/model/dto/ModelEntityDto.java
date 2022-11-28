package com.netwisd.base.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.util.IdTypeDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "数据建模 Dto")
public class ModelEntityDto extends IDto {

    @ApiModelProperty(value = "数据源Id")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Valid(nullMsg = "数据源不能为空")
    private Long dbSourceId;

    @ApiModelProperty(value = "数据源")
    @Valid(nullMsg = "数据源不能为空")
    private String dbSource;

    @ApiModelProperty(value = "创建类型1、新创建2、选择现有表")
    @Valid(nullMsg = "创建类型不能为空")
    private Integer createType;

    @ApiModelProperty(value = "表名")
    @Valid(nullMsg = "表名不能为空")
    private String tableName;

    @ApiModelProperty(value = "表中文名")
    private String tableNameCh;

    @ApiModelProperty(value = "基本包路径(包名)")
    @Valid(nullMsg = "基本包路径不能为空")
    private String packageName;

    @ApiModelProperty(value = "前缀(表名前缀)")
    @Valid(nullMsg = "前缀不能为空")
    private String tablePrefix;

    @ApiModelProperty(value = "模块名")
    @Valid(nullMsg = "模块名不能为空")
    private String moduleName;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "子系统Code")
    @Valid(nullMsg = "子系统不能为空")
    private String sysCode;

    @ApiModelProperty(value = "子系统名称")
    @Valid(nullMsg = "子系统名称为空")
    private String sysName;

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

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "sql语句")
    private String sql;

    @ApiModelProperty(value = "实体字段")
    private List<ModelFieldDto> filedList;

    @ApiModelProperty(value = "实体字段")
    private List<ModelFieldDto> oldFieldList;

    @ApiModelProperty(value = "表名")
    private String oldTableName;

    @ApiModelProperty(value = "表中文名")
    private String oldTableNameCh;

}
