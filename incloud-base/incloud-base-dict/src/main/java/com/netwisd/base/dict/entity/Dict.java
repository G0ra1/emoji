package com.netwisd.base.dict.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.netwisd.base.dict.dto.DictDto;
import com.netwisd.base.dict.vo.DictVo;
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
@Table(value = "incloud_base_dict", comment = "字典")
@ApiModel(value = "字典 Entity")
@TableName("incloud_base_dict")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Dict extends IModel<Dict> {

    @ApiModelProperty(value = "字典类型")
    @TableField(value = "dict_type")
    @Column(length = 1, type = DataType.INT, comment = "字典类型")
    private Integer dictType;

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

    public void toDict(DictDto dictDto) {
        this.setDictName(dictDto.getDictName());
        this.setDictCode(dictDto.getDictCode());
        this.setRemark(dictDto.getRemark());
        this.setDictCodeAlias(dictDto.getDictCodeAlias());
        this.setDictNameAlias(dictDto.getDictNameAlias());
    }

    public DictVo toDictVo() {
        DictVo dictVo = new DictVo();
        dictVo.setId(this.id);
        dictVo.setDictName(this.dictName);
        dictVo.setDictCode(this.dictCode);
        dictVo.setRemark(this.remark);
        dictVo.setDictCodeAlias(this.dictCodeAlias);
        dictVo.setDictNameAlias(this.dictNameAlias);
        return dictVo;
    }

}
