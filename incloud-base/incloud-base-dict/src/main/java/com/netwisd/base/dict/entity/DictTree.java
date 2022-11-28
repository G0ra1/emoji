package com.netwisd.base.dict.entity;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.netwisd.base.common.dict.dto.DictTreeDto;
import com.netwisd.base.common.dict.vo.DictTreeVo;
import com.netwisd.base.dict.dto.DictReceiveDto;
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
@Table(value = "incloud_base_dict_tree", comment = "字典树形")
@ApiModel(value = "字典树形 Entity")
@TableName("incloud_base_dict_tree")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DictTree extends IModel<DictTree> {

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

    @ApiModelProperty(value = "版本号")
    @TableField(value = "version")
    @Column(type = DataType.INT, length = 10, comment = "版本号")
    private Integer version;

    @ApiModelProperty(value = "扩展Id")
    @TableField(value = "ext_id")
    @Column(length = 100, comment = "扩展Id")
    private String extId;

    @ApiModelProperty(value = "扩展父级Id")
    @TableField(value = "ext_pid")
    @Column(length = 100, comment = "扩展父级Id")
    private String extPid;

    @ApiModelProperty(value = "扩展父级Id")
    @TableField(value = "ext_full_code")
    @Column(length = 1024, comment = "扩展父级Id")
    private String extFullCode;

    public void toDictTree(DictTreeDto dictTreeDto) {
        this.setId(dictTreeDto.getId());
        this.setParentId(ObjectUtil.isNull(dictTreeDto.getParentId()) ? 0L : dictTreeDto.getParentId());
        this.setDictName(dictTreeDto.getDictName());
        this.setDictCode(dictTreeDto.getDictCode());
        this.setDictNameAlias(dictTreeDto.getDictNameAlias());
        this.setDictCodeAlias(dictTreeDto.getDictCodeAlias());
    }

    public void toDictTree(DictReceiveDto dictReceiveDto) {
        this.setExtId(dictReceiveDto.getId());
        this.setExtPid(dictReceiveDto.getPid());
        this.setParentId(0L);
        this.setDictName(dictReceiveDto.getDictName());
        this.setDictCode(dictReceiveDto.getDictCode());
        this.setRemark(dictReceiveDto.getRemark());
        this.setLevel(dictReceiveDto.getLevel());
        this.setExtFullCode(dictReceiveDto.getFullCode());
    }

    public DictTreeVo toDictTreeVo() {
        DictTreeVo dictTreeVo = new DictTreeVo();
        dictTreeVo.setId(this.id);
        dictTreeVo.setDictName(this.dictName);
        dictTreeVo.setDictCode(this.dictCode);
        dictTreeVo.setRemark(this.remark);
        dictTreeVo.setParentId(this.parentId);
        dictTreeVo.setLevel(this.level);
        dictTreeVo.setDictNameAlias(this.dictNameAlias);
        dictTreeVo.setDictCodeAlias(this.dictCodeAlias);
        dictTreeVo.setVersion(this.version);
        return dictTreeVo;
    }
}
