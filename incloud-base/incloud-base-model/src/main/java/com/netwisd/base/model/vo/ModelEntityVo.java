package com.netwisd.base.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
public class ModelEntityVo extends IVo {

    @ApiModelProperty(value = "数据源Id")
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long dbSourceId;

    @ApiModelProperty(value = "数据源")
    private String dbSource;

    @ApiModelProperty(value = "引擎")
    private String engine;

    @ApiModelProperty(value = "编码")
    private String tableCollation;

    @ApiModelProperty(value = "创建类型1、新创建2、选择现有表")
    private Integer createType;

    @ApiModelProperty(value = "表名")
    private String tableName;

    @ApiModelProperty(value = "表中文名")
    private String tableNameCh;

    @ApiModelProperty(value = "基本包路径(包名)")
    private String packageName;

    @ApiModelProperty(value = "前缀(表名前缀)")
    private String tablePrefix;

    @ApiModelProperty(value = "模块名")
    private String moduleName;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "子系统Code")
    private String sysCode;

    @ApiModelProperty(value = "子系统名称")
    private String sysName;

    @ApiModelProperty(value = "模型分类ID")
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long modelTypeId;

    @ApiModelProperty(value = "模型分类code")
    private String modelTypeCode;

    @ApiModelProperty(value = "模型分类名称")
    private String modelTypeName;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "实体字段")
    private List<ModelFieldVo> filedList;
}
