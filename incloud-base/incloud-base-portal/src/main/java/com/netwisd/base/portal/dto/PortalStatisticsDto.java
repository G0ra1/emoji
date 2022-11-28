package com.netwisd.base.portal.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "统计管理 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PortalStatisticsDto {
    //名称
    @ApiModelProperty(value = "名称")
    private String name;
   //数值
   @ApiModelProperty(value = "数值")
    private Long number;

}
