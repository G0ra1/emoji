package com.netwisd.base.model.vo;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.base.model.dto.ModelFieldDto;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "数据建模字段 Vo")
public class ModelFieldVo extends IVo {

    @ApiModelProperty(value = "实体Id")
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long entityId;

    @ApiModelProperty(value = "实体名称")
    private String entityName;

    @ApiModelProperty(value = "字段名称")
    private String name;

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

    @ApiModelProperty(value = "默认值")
    private String defaultValue;

    @ApiModelProperty(value = "关联表ID")
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long fkTableId;

    @ApiModelProperty(value = "关联表名称")
    private String fkTableName;

    @ApiModelProperty(value = "关联表字段ID")
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long fkFieldId;

    @ApiModelProperty(value = "关联表字段名称")
    private String fkFieldName;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "列类型")
    private String columnType;

    public ModelFieldDto toModelField() {
        ModelFieldDto modelFieldDto = new ModelFieldDto();
        modelFieldDto.setId(this.getId());
        modelFieldDto.setName(this.getName());
        modelFieldDto.setNameCh(this.getNameCh());
        modelFieldDto.setDbType(this.getDbType());
        modelFieldDto.setLength(ObjectUtil.isNotNull(this.getLength()) ? this.getLength() : 0);
        modelFieldDto.setPrecision(ObjectUtil.isNotNull(this.getPrecision()) ? this.getPrecision() : 0);
        modelFieldDto.setIsNotNull(this.getIsNotNull());
        modelFieldDto.setIsKey(this.getIsKey());
        modelFieldDto.setIsUnique(this.getIsUnique());
        modelFieldDto.setDefaultValue(this.getDefaultValue());
        modelFieldDto.setColumnType(this.getColumnType());
        return modelFieldDto;
    }
}
