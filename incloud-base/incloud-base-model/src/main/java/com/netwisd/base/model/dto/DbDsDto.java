package com.netwisd.base.model.dto;

import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.anntation.Valid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "数据源 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DbDsDto extends IDto {

    @ApiModelProperty(value = "子系统Code")
    private String sysCode;

    @ApiModelProperty(value = "子系统名称")
    private String sysName;

    @ApiModelProperty(value = "数据源")
    @Valid(nullMsg = "数据源不能为空")
    private String poolName;

    @ApiModelProperty(value = "类型")
    @Valid(nullMsg = "数据源类型不能为空")
    private String type;

    @ApiModelProperty(value = "用户名")
    @Valid(nullMsg = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码")
    @Valid(nullMsg = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "url")
    @Valid(nullMsg = "url不能为空")
    private String url;

    @ApiModelProperty(value = "是否启用")
    @Valid(nullMsg = "启用状态不能为空")
    private Integer isEnable;

    @ApiModelProperty(value = "描述")
    private String description;
}
