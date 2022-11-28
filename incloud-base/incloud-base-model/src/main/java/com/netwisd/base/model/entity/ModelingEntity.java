package com.netwisd.base.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.anntation.Table;
import com.netwisd.common.db.data.DataType;
import com.netwisd.common.db.data.IModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table(value = "incloud_base_model_modeling_entity", comment = "数据建模实体")
@TableName("incloud_base_model_modeling_entity")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "数据建模实体 Entity")
public class ModelingEntity extends IModel<ModelingEntity> {

    /**
     * model_id
     * 模型Id
     */
    @ApiModelProperty(value = "模型Id")
    @TableField(value = "model_id")
    @Column(length = 20, type = DataType.BIGINT, isNull = false, comment = "模型Id")
    private Long modelId;

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
    private Long modelTypeId;

    /**
     * model_type_code
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
     * entity_id
     * 建模实体Id(表实体Id)
     */
    @ApiModelProperty(value = "建模实体Id(表实体Id)")
    @TableField(value = "entity_id")
    @Column(length = 20, type = DataType.BIGINT, isNull = false, comment = "建模实体Id(表实体Id)")
    private Long entityId;

    /**
     * parent_entity_id
     * 父模型Id(表实体Id)
     */
    @ApiModelProperty(value = "父模型Id(表实体Id)")
    @TableField(value = "parent_entity_id")
    @Column(length = 20, type = DataType.BIGINT, isNull = false, comment = "父模型Id(表实体Id)")
    private Long parentEntityId;

    /**
     * table_name
     * 表名
     */
    @ApiModelProperty(value = "表名")
    @TableField(value = "table_name")
    @Column(length = 50, isNull = false, comment = "表名")
    private String tableName;

    /**
     * table_name_ch
     * 表中文名
     */
    @ApiModelProperty(value = "表中文名")
    @TableField(value = "table_name_ch")
    @Column(length = 50, comment = "表中文名")
    private String tableNameCh;

    @ApiModelProperty(value = "子表存储集合Key")
    @TableField(value = "child_table_set_key")
    @Column(length = 50, comment = "子表存储集合Key")
    private String childTableSetKey;
}
