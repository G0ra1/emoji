package com.netwisd.base.dict.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
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
@Table(value = "incloud_base_dict_item_version", comment = "字典项版本")
@ApiModel(value = "字典项版本 Entity")
@TableName("incloud_base_dict_item_version")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DictItemVersion extends IModel<DictItemVersion> {

    @ApiModelProperty(value = "原字典项Id")
    @TableField(value = "old_dict_item_id")
    @Column(type = DataType.BIGINT, length = 20, comment = "原字典项Id")
    private Long oldDictItemId;

    @ApiModelProperty(value = "版本号")
    @TableField(value = "version")
    @Column(type = DataType.INT, length = 10, comment = "version")
    private Integer version;

    @ApiModelProperty(value = "是否主版本")
    @TableField(value = "is_main_version")
    @Column(type = DataType.INT, length = 1, defaultValue = "1", comment = "是否主版本")
    private Integer isMainVersion;

    @ApiModelProperty(value = "所属字典类id")
    @TableField(value = "dict_id")
    @Column(type = DataType.BIGINT, length = 20, isNull = false, comment = "所属字典类id")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long dictId;

    @ApiModelProperty(value = "字典编码")
    @TableField(value = "dict_code")
    @Column(length = 100, isNull = false, comment = "字典编码")
    private String dictCode;

    @ApiModelProperty(value = "字典项名称")
    @TableField(value = "dict_item_name")
    @Column(length = 100, isNull = false, comment = "字典项名称")
    private String dictItemName;

    @ApiModelProperty(value = "字典项编码")
    @TableField(value = "dict_item_code")
    @Column(length = 100, isNull = false, comment = "字典项编码")
    private String dictItemCode;

    @ApiModelProperty(value = "备注")
    @TableField(value = "remark")
    @Column(length = 100, comment = "备注")
    private String remark;

    @ApiModelProperty(value = "排序值，默认升序")
    @TableField(value = "sort")
    @Column(type = DataType.INT, length = 10, comment = "排序值，默认升序")
    private Integer sort;

    @ApiModelProperty(value = "是否启用")
    @TableField(value = "is_enable")
    @Column(type = DataType.INT, length = 1, defaultValue = "0", comment = "是否启用")
    private Integer isEnable;

    public DictItemVersion(Integer version) {
        this.version = version;
    }
}
