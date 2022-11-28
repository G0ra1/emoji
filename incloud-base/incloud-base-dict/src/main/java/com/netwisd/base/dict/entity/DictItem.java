package com.netwisd.base.dict.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.base.dict.dto.DictItemDto;
import com.netwisd.base.dict.vo.DictItemVo;
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
@Table(value = "incloud_base_dict_item", comment = "字典项")
@ApiModel(value = "字典项 Entity")
@TableName("incloud_base_dict_item")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DictItem extends IModel<DictItem> {

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

    @ApiModelProperty(value = "版本号")
    @TableField(value = "version")
    @Column(type = DataType.INT, length = 10, defaultValue = "1", comment = "版本号")
    private Integer version;

    public DictItem(Integer version) {
        this.version = version;
    }

    public void toDictItem(DictItemDto dictItemDto) {
        this.setId(dictItemDto.getId());
        this.setDictItemCode(dictItemDto.getDictItemCode());
        this.setDictItemName(dictItemDto.getDictItemName());
        this.setRemark(dictItemDto.getRemark());
        this.setDictId(dictItemDto.getDictId());
        this.setDictCode(dictItemDto.getDictCode());
        this.setSort(dictItemDto.getSort());
        this.setIsEnable(dictItemDto.getIsEnable());
    }

    public DictItemVo toDictItemVo() {
        DictItemVo dictItemVo = new DictItemVo();
        dictItemVo.setId(this.id);
        dictItemVo.setDictItemCode(this.dictItemCode);
        dictItemVo.setDictItemName(this.dictItemName);
        dictItemVo.setRemark(this.remark);
        dictItemVo.setSort(this.sort);
        dictItemVo.setDictId(this.dictId);
        dictItemVo.setDictCode(this.dictCode);
        dictItemVo.setVersion(this.version);
        dictItemVo.setIsEnable(this.isEnable);
        return dictItemVo;
    }

}
