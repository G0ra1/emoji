package com.netwisd.base.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.db.anntation.Column;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "数据建模实体字段 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ModelingFieldDto extends IDto {

    @ApiModelProperty(value = "模型实体Id")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long modelingEntityId;

    @ApiModelProperty(value = "模型名称")
    private String modelingEntityName;

    @ApiModelProperty(value = "实体Id")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long entityId;

    @ApiModelProperty(value = "模型实体字段Id")
    @JsonDeserialize(using = IdTypeDeserializer.class)
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
}
