package com.netwisd.base.dict.dto;

import com.netwisd.common.core.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "字典Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DictDto extends IDto {

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
}
