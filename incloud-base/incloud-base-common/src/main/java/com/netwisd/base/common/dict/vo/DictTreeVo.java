package com.netwisd.base.common.dict.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(value = "字典树形Vo")
@AllArgsConstructor
@NoArgsConstructor
public class DictTreeVo extends IVo {

    @ApiModelProperty(value = "字典名称")
    private String dictName;

    @ApiModelProperty(value = "字典编码")
    private String dictCode;

    @ApiModelProperty(value = "字典名称别名")
    private String dictNameAlias;

    @ApiModelProperty(value = "字典编码别名")
    private String dictCodeAlias;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "父级ID")
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long parentId;

    @ApiModelProperty(value = "级别")
    private Integer level;

    @ApiModelProperty(value = "是否有子集")
    private Integer hasKids;

    @ApiModelProperty(value = "是否主版本")
    private Integer isMainVersion;

    @ApiModelProperty(value = "版本号")
    private Integer version;

    /*@ApiModelProperty(value = "数据唯一")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long dataUnique;*/

    @ApiModelProperty(value = "原字典Id")
    @JsonSerialize(using = IdTypeSerializer.class)
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long oldDictTreeId;

    @ApiModelProperty(value = "子集")
    private List<DictTreeVo> kids = new ArrayList<>();
}
