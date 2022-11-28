package com.netwisd.base.model.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
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
@Table(value = "incloud_base_model_field", comment = "建模实体字段")
@TableName("incloud_base_model_field")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "建模实体字段 Entity")
public class ModelField extends IModel<ModelField> {

    /**
     * entity_id
     * 实体ID
     */
    @ApiModelProperty(value = "实体Id")
    @TableField(value = "entity_id")
    @Column(type = DataType.BIGINT, length = 20, isNull = false, comment = "实体Id")
    private Long entityId;

    /**
     * entity_name
     * 实体名称
     */
    @ApiModelProperty(value = "实体名称")
    @TableField(value = "entity_name")
    @Column(isNull = false, comment = "实体名称")
    private String entityName;

    /**
     * name
     * 字段名称
     */
    @ApiModelProperty(value = "字段名称")
    @TableField(value = "`name`")
    @Column(isNull = false, comment = "字段名称")
    private String name;

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
     * length
     */
    @ApiModelProperty(value = "长度")
    @TableField(value = "length")
    @Column(type = DataType.INT, length = 10, comment = "长度")
    private Integer length;

    /**
     * 精度
     * precision
     */
    @ApiModelProperty(value = "精度")
    @TableField(value = "`precision`")
    @Column(type = DataType.INT, length = 10, comment = "精度")
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
    @Column(type = DataType.INT, length = 1, comment = "是否主键")
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
     * default_value
     * 默认值
     */
    @ApiModelProperty(value = "默认值")
    @TableField(value = "default_value", updateStrategy = FieldStrategy.IGNORED)
    @Column(length = 500, comment = "默认值")
    private String defaultValue;

    /**
     * fk_table_id
     * 关联表ID
     */
    @ApiModelProperty(value = "关联表ID")
    @TableField(value = "fk_table_id", updateStrategy = FieldStrategy.IGNORED)
    @Column(type = DataType.BIGINT, length = 20, comment = "关联表ID")
    private Long fkTableId;

    /**
     * fk_table_name
     * 关联表名称
     */
    @ApiModelProperty(value = "关联表名称")
    @TableField(value = "fk_table_name", updateStrategy = FieldStrategy.IGNORED)
    @Column(comment = "关联表名称")
    private String fkTableName;

    /**
     * fk_field_id
     * 关联表字段ID
     */
    @ApiModelProperty(value = "关联表字段ID")
    @TableField(value = "fk_field_id", updateStrategy = FieldStrategy.IGNORED)
    @Column(type = DataType.BIGINT, length = 20, comment = "关联表字段ID")
    private Long fkFieldId;

    /**
     * fk_field_name
     * 关联表字段名称
     */
    @ApiModelProperty(value = "关联表字段名称")
    @TableField(value = "fk_field_name", updateStrategy = FieldStrategy.IGNORED)
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
}
