package com.netwisd.base.dict.dto;

import com.netwisd.common.core.anntation.Valid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "字典接受Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DictReceiveDto {

    @Valid(nullMsg = "Id不能为空")
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "父级ID")
    private String pid;

    @ApiModelProperty(value = "级别")
    private Integer level;

    @ApiModelProperty(value = "字典名称")
    private String dictName;

    @ApiModelProperty(value = "完整代码")
    private String fullCode;

    @ApiModelProperty(value = "字典编码")
    private String dictCode;

    @ApiModelProperty(value = "备注")
    private String remark;
}
