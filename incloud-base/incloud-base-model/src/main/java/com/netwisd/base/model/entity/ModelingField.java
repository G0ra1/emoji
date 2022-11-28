package com.netwisd.base.model.entity;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.netwisd.common.code.entity.FieldConfig;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.anntation.Table;
import com.netwisd.common.db.data.DataType;
import com.netwisd.common.db.data.IModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table(value = "incloud_base_model_modeling_field", comment = "数据建模实体字段")
@TableName("incloud_base_model_modeling_field")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "数据建模实体字段 Entity")
public class ModelingField extends IModel<ModelingField> {

    /**
     * model_id
     * 模型Id
     */
    @ApiModelProperty(value = "模型Id")
    @TableField(value = "modeling_id")
    @Column(length = 20, type = DataType.BIGINT, isNull = false, comment = "模型Id")
    private Long modelingId;

    /**
     * modeling_entity_id
     * 模型实体Id
     */
    @ApiModelProperty(value = "模型实体Id")
    @TableField(value = "modeling_entity_id")
    @Column(type = DataType.BIGINT, isNull = false, comment = "模型实体Id")
    private Long modelingEntityId;

    /**
     * modeling_entity_name
     * 模型实体名称
     */
    @ApiModelProperty(value = "模型名称")
    @TableField(value = "modeling_entity_name")
    @Column(length = 100, isNull = false, comment = "模型实体名称")
    private String modelingEntityName;

    /**
     * entity_id
     * 实体Id
     */
    @ApiModelProperty(value = "实体Id")
    @TableField(value = "entity_id")
    @Column(length = 20, type = DataType.BIGINT, defaultValue = "0", comment = "实体Id")
    private Long entityId;

    /**
     * entity_field_id
     * 模型实体字段Id
     */
    @ApiModelProperty(value = "模型实体字段Id")
    @TableField(value = "entity_field_id")
    @Column(length = 20, type = DataType.BIGINT, defaultValue = "0", comment = "模型实体字段Id")
    private Long entityFieldId;

    /**
     * name
     * 字段名称
     */
    @ApiModelProperty(value = "字段名称")
    @TableField(value = "`name`")
    @Column(isNull = false, comment = "字段名称")
    private String name;

    /**
     * java_name
     * 字段Java名称
     */
    @ApiModelProperty(value = "字段Java名称")
    @TableField(value = "`java_name`")
    @Column(isNull = false, comment = "字段Java名称")
    private String javaName;

    /**
     * name_ch
     * 表名
     */
    @ApiModelProperty(value = "字段中文名称")
    @TableField(value = "name_ch")
    @Column(comment = "字段中文名称")
    private String nameCh;

    /**
     * db_type
     * 数据库类型
     */
    @ApiModelProperty(value = "数据库类型")
    @TableField(value = "db_type")
    @Column(length = 50, comment = "数据库类型")
    private String dbType;

    /**
     * 长度
     * 长度
     */
    @ApiModelProperty(value = "长度")
    @TableField(value = "length")
    @Column(type = DataType.BIGINT, length = 10, comment = "长度")
    private Integer length;

    /**
     * 精度
     * 精度
     */
    @ApiModelProperty(value = "精度")
    @TableField(value = "`precision`")
    @Column(type = DataType.BIGINT, length = 10, comment = "精度")
    private Integer precision;

    /**
     * is_not_null
     * 是否不为空
     */
    @ApiModelProperty(value = "是否不为空")
    @TableField(value = "is_not_null")
    @Column(type = DataType.INT, length = 1, comment = "是否不为空")
    private Integer isNotNull;

    /**
     * is_key
     * 是否主键
     */
    @ApiModelProperty(value = "是否主键")
    @TableField(value = "is_key")
    @Column(type = DataType.INT, defaultValue = "0", length = 1, comment = "是否主键")
    private Integer isKey;

    /**
     * is_unique
     * 是否唯一
     */
    @ApiModelProperty(value = "是否唯一")
    @TableField(value = "is_unique")
    @Column(type = DataType.INT, length = 1, comment = "是否唯一")
    private Integer isUnique;

    /**
     * fk_table_name
     * 关联表名称
     */
    @ApiModelProperty(value = "关联表名称")
    @TableField(value = "fk_table_name")
    @Column(comment = "关联表名称")
    private String fkTableName;

    /**
     * fk_field_name
     * 关联表字段名称
     */
    @ApiModelProperty(value = "关联表字段名称")
    @TableField(value = "fk_field_name")
    @Column(comment = "关联表字段名称")
    private String fkFieldName;

    /**
     * sort
     * 排序
     */
    @ApiModelProperty(value = "排序")
    @TableField(value = "sort")
    @Column(type = DataType.INT, length = 10, comment = "排序")
    private Integer sort;

    public FieldConfig toFieldConfigVo() {
        FieldConfig fieldConfig = new FieldConfig();
        fieldConfig.setName(this.getName());
        fieldConfig.setNameCh(this.getNameCh());
        fieldConfig.setDbType(this.getDbType());
        fieldConfig.setLength(ObjectUtil.isEmpty(this.getLength()) ? 11 : this.getLength());
        fieldConfig.setPrecision(ObjectUtil.isEmpty(this.getPrecision()) ? 0 : this.getPrecision());
        fieldConfig.setIsNotNull(this.getIsNotNull());
        fieldConfig.setIsKey(this.getIsKey());
        fieldConfig.setIsUnique(this.getIsUnique());
        fieldConfig.setFkTableName(this.getFkTableName());
        fieldConfig.setFkFieldName(this.getFkFieldName());
        return fieldConfig;
    }
}
