package com.netwisd.base.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.base.model.vo.ModelingVo;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.anntation.Table;
import com.netwisd.common.db.data.DataType;
import com.netwisd.common.db.data.IModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table(value = "incloud_base_model_modeling", comment = "数据建模")
@TableName("incloud_base_model_modeling")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "数据建模 Entity")
public class Modeling extends IModel<Modeling> {

    /**
     * model_name
     * 模型名称
     */
    @ApiModelProperty(value = "模型名称")
    @TableField(value = "model_name")
    @Column(length = 100, isNull = false, comment = "模型名称")
    private String modelName;

    /**
     * model_name_ch
     * 模型中文名称
     */
    @ApiModelProperty(value = "模型中文名称")
    @TableField(value = "model_name_ch")
    @Column(length = 100, isNull = false, comment = "模型中文名称")
    private String modelNameCh;

    /**
     * model_type_id
     * 模型分类ID
     */
    @ApiModelProperty(value = "模型分类ID")
    @TableField(value = "model_type_id")
    @Column(type = DataType.BIGINT, isNull = false, comment = "模型分类ID")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long modelTypeId;

    /**
     * sysName
     * 模型分类code
     */
    @ApiModelProperty(value = "模型分类code")
    @TableField(value = "model_type_code")
    @Column(length = 100, isNull = false, comment = "模型分类code")
    private String modelTypeCode;

    /**
     * model_type_name
     * 模型分类名称
     */
    @ApiModelProperty(value = "模型分类名称")
    @TableField(value = "model_type_name")
    @Column(length = 100, isNull = false, comment = "模型分类名称")
    private String modelTypeName;

    /**
     * model_property
     * 模型特性
     */
    @ApiModelProperty(value = "模型特性")
    @TableField(value = "model_property")
    @Column(type = DataType.INT, length = 1, isNull = false, comment = "模型特性")
    private Integer modelProperty;

    /**
     * entity_id
     * 主实体ID
     */
    @ApiModelProperty(value = "主实体ID")
    @TableField(value = "entity_id")
    @Column(type = DataType.BIGINT, length = 20, isNull = false, comment = "主实体ID")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long entityId;

    /**
     * entity_name
     * 主实体名称
     */
    @ApiModelProperty(value = "主实体名称")
    @TableField(value = "entity_name")
    @Column(isNull = false, comment = "主实体名称")
    private String entityName;

    /**
     * entity_name_ch
     * 主实体中文名
     */
    @ApiModelProperty(value = "主实体中文名")
    @TableField(value = "entity_name_ch")
    @Column(isNull = false, comment = "主实体中文名")
    private String entityNameCh;

    /**
     * status
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField(value = "status")
    @Column(type = DataType.INT, length = 1, isNull = false, comment = "状态")
    private Integer status;

    public ModelingVo toModelingVo() {
        ModelingVo modelingVo = new ModelingVo();
        modelingVo.setId(this.getId());
        modelingVo.setModelName(this.getModelName());
        modelingVo.setModelNameCh(this.getModelNameCh());
        return modelingVo;
    }
}
