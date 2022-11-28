package com.netwisd.base.common.portal.vo;


import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @Description 门户维护 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-11 15:01:07
 */
@Data
@ApiModel(value = "门户维护 Vo")
public class PortalPortalVo extends IVo{

    /**
     * portal_name
     * 门户名称
     */
    @ApiModelProperty(value="门户名称")
    private String portalName;
    /**
     * is_default
     * 默认首页 0否1是;
     */
    @ApiModelProperty(value="默认首页 0否1是;")
    private Integer isDefault;
    /**
     * is_login
     * 是否登录 0否1是;
     */
    @ApiModelProperty(value="是否登录 0否1是;")
    private Integer isLogin;
    /**
     * is_enable
     * 是否启用 0否1是;
     */
    @ApiModelProperty(value="是否启用 0否1是;")
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
