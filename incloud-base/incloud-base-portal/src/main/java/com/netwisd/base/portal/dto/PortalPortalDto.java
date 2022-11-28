package com.netwisd.base.portal.dto;

import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * @Description 门户维护 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-11 09:50:22
 */
@Data
@ApiModel(value = "门户维护 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PortalPortalDto extends IDto {

    public PortalPortalDto(Args args){
        super(args);
    }

    /**
     * portal_name
     * 门户名称
     */
    @ApiModelProperty(value="门户名称")
    @Valid
    private String portalName;

    /**
     * is_default
     * 默认首页 0否1是;
     */
    @ApiModelProperty(value="默认首页 0否1是;")
    @Valid
    private Integer isDefault;

    /**
     * is_login
     * 是否登录 0否1是;
     */
    @ApiModelProperty(value="是否登录 0否1是;")
    @Valid
    private Integer isLogin;

    /**
     * is_enable
     * 是否启用 0否1是;
     */
    @ApiModelProperty(value="是否启用 0否1是;")
    @Valid
    private Integer isEnable;

    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    
    private String remark;

    /**
     * hits
     * 点击量
     */
    @ApiModelProperty(value = "点击量")
    private Long hits;
}
