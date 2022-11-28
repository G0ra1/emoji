package com.netwisd.base.common.dict.dto;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.data.IDto;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "字典树形Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DictTreeDto extends IDto {

    @ApiModelProperty(value = "父级ID")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long parentId;

    @ApiModelProperty(value = "父级Code")
    private String parentCode;

    @ApiModelProperty(value = "扩展Id")
    private String extId;

    @ApiModelProperty(value = "扩展Ids")
    private String extIds = StrUtil.EMPTY;

    @ApiModelProperty(value = "扩展PId")
    private String extPid;

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

    /*@ApiModelProperty(value = "数据唯一")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long dataUnique;*/

    @ApiModelProperty(value = "原字典Id")
    @JsonSerialize(using = IdTypeSerializer.class)
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long oldDictTreeId;

}
