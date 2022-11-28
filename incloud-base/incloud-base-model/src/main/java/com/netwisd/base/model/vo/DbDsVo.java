package com.netwisd.base.model.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "数据源 Vo")
public class DbDsVo extends IVo {

    @ApiModelProperty(value = "子系统Code")
    private String sysCode;

    @ApiModelProperty(value = "子系统名称")
    private String sysName;

    @ApiModelProperty(value = "数据源")
    private String poolName;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "url")
    private String url;

    @ApiModelProperty(value = "是否启用")
    private Integer isEnable;

    @ApiModelProperty(value = "描述")
    private String description;

}
