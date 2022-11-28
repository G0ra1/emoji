package com.netwisd.base.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.netwisd.base.model.vo.ModelingEntityVo;
import com.netwisd.common.code.entity.EntityConfig;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.anntation.Table;
import com.netwisd.common.db.data.DataType;
import com.netwisd.common.db.data.IModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table(value = "incloud_base_model_entity", comment = "建模实体")
@TableName("incloud_base_model_entity")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "建模实体 Entity")
public class ModelEntity extends IModel<ModelEntity> {

    /**
     * db_source_id
     * 数据源
     */
    @ApiModelProperty(value = "数据源Id")
    @TableField(value = "db_source_id")
    @Column(type = DataType.BIGINT, length = 20, isNull = false, comment = "数据源Id")
    private Long dbSourceId;

    /**
     * db_source
     * 数据源
     */
    @ApiModelProperty(value = "数据源")
    @TableField(value = "db_source")
    @Column(length = 50, isNull = false, comment = "数据源")
    private String dbSource;

    /**
     * create_type
     * 创建类型1、新创建2、选择现有表
     */
    @ApiModelProperty(value = "创建类型1、新创建2、选择现有表")
    @TableField(value = "create_type")
    @Column(type = DataType.INT, length = 1, isNull = false, comment = "创建类型1、新创建2、选择现有表")
    private Integer createType;

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

    /**
     * package_name
     * 基本包路径
     */
    @ApiModelProperty(value = "基本包路径(包名)")
    @TableField(value = "package_name")
    @Column(isNull = false, comment = "基本包路径(包名)")
    private String packageName;

    /**
     * table_prefix
     * 实体前缀
     */
    @ApiModelProperty(value = "前缀(表名前缀)")
    @TableField(value = "table_prefix")
    @Column(isNull = false, comment = "前缀(表名前缀)")
    private String tablePrefix;

    /**
     * module_name
     * 模块名
     */
    @ApiModelProperty(value = "模块名")
    @TableField(value = "module_name")
    @Column(length = 50, comment = "模块名")
    private String moduleName;

    /**
     * author
     * 作者
     */
    @ApiModelProperty(value = "作者")
    @TableField(value = "author")
    @Column(length = 50, comment = "作者")
    private String author;

    /**
     * sysCode
     * 子系统Code
     */
    @ApiModelProperty(value = "子系统Code")
    @TableField(value = "sys_code")
    @Column(length = 100, isNull = false, comment = "子系统Code")
    private String sysCode;

    /**
     * sysName
     * 子系统名称
     */
    @ApiModelProperty(value = "子系统名称")
    @TableField(value = "sys_name")
    @Column(length = 100, isNull = false, comment = "子系统名称")
    private String sysName;

    /**
     * model_type_id
     * 模型分类ID
     */
    @ApiModelProperty(value = "模型分类ID")
    @TableField(value = "model_type_id")
    @Column(type = DataType.BIGINT, isNull = false, comment = "模型分类ID")
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
     * status
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField(value = "status")
    @Column(type = DataType.INT, length = 1, isNull = false, comment = "状态")
    private Integer status;

    public ModelingEntityVo toModelingEntityVo(Long parentEntityId, int level) {
        ModelingEntityVo modelingEntityVo = new ModelingEntityVo();
        modelingEntityVo.setParentEntityId(parentEntityId);
        modelingEntityVo.setLevel(level);
        modelingEntityVo.setTableName(this.getTableName());
        modelingEntityVo.setTableNameCh(this.getTableNameCh());
        modelingEntityVo.setEntityId(this.getId());
        return modelingEntityVo;
    }

    public EntityConfig toEntityConfig() {
        EntityConfig entityConfig = new EntityConfig();
        entityConfig.setAuthor(this.getAuthor());
        entityConfig.setModuleName(this.getModuleName());
        entityConfig.setPackageName(this.getPackageName());
        entityConfig.setTablePrefix(this.getTablePrefix());
        entityConfig.setTableName(this.getTableName());
        entityConfig.setTableNameCh(this.getTableNameCh());
        return entityConfig;
    }
}
