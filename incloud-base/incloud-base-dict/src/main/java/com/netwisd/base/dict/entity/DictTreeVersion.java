package com.netwisd.base.dict.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.anntation.Table;
import com.netwisd.common.db.data.DataType;
import com.netwisd.common.db.data.IModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Table(value = "incloud_base_dict_tree_version", comment = "字典树形版本")
@ApiModel(value = "字典树形版本 Entity")
@TableName("incloud_base_dict_tree_version")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DictTreeVersion extends IModel<DictTreeVersion> {

    @ApiModelProperty(value = "原字典Id")
    @TableField(value = "old_dict_tree_id")
    @Column(type = DataType.BIGINT, length = 20, comment = "原字典Id")
    private Long oldDictTreeId;

    @ApiModelProperty(value = "版本号")
    @TableField(value = "version")
    @Column(type = DataType.INT, length = 10, comment = "version")
    private Integer version;

    @ApiModelProperty(value = "是否主版本")
    @TableField(value = "is_main_version")
    @Column(type = DataType.INT, length = 1, defaultValue = "1", comment = "是否主版本")
    private Integer isMainVersion;

    @ApiModelProperty(value = "字典名称")
    @TableField(value = "dict_name")
    @Column(length = 100, isNull = false, comment = "字典名称")
    private String dictName;

    @ApiModelProperty(value = "字典编码")
    @TableField(value = "dict_code")
    @Column(length = 100, isNull = false, comment = "字典编码")
    private String dictCode;

    @ApiModelProperty(value = "字典名称别名")
    @TableField(value = "dict_name_alias")
    @Column(length = 100, comment = "字典名称别名")
    private String dictNameAlias;

    @ApiModelProperty(value = "字典编码别名")
    @TableField(value = "dict_code_alias")
    @Column(length = 100, comment = "字典编码别名")
    private String dictCodeAlias;

    @ApiModelProperty(value = "备注")
    @TableField(value = "remark")
    @Column(length = 100, comment = "备注")
    private String remark;

    @ApiModelProperty(value = "父级ID")
    @TableField(value = "parent_id")
    @Column(type = DataType.BIGINT, length = 20, comment = "父级ID")
    private Long parentId;

    @ApiModelProperty(value = "级别")
    @TableField(value = "level")
    @Column(type = DataType.INT, length = 10, comment = "级别")
    private Integer level;

    public DictTreeVersion(Integer version) {
        this.version = version;
    }
}
