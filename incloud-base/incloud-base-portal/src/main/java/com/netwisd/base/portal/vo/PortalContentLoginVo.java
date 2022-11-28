package com.netwisd.base.portal.vo;

import com.netwisd.base.portal.dto.PortalContentLoginBackgroundSonDto;
import com.netwisd.base.portal.dto.PortalContentLoginCorporateSonDto;
import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author 云数网讯 cuiran@netwisd.com
 * @Description 登录页设置表 功能描述...
 * @date 2021-12-27 16:36:19
 */
@Data
@ApiModel(value = "登录页设置表 Vo")
public class PortalContentLoginVo extends IVo {

    /**
     * login_page_logo_id
     * 登录页logoId
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value = "登录页logoId")
    private Long loginPageLogoId;

    /**
     * login_page_logo_url
     * 登录页logo
     */

    @ApiModelProperty(value = "登录页logo")
    private String loginPageLogoUrl;

    /**
     * system_logo_id
     * 系统logoid
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value = "系统logoId")
    private Long systemLogoId;

    /**
     * system_logo_url
     * 系统logo
     */

    @ApiModelProperty(value = "系统logo")
    private String systemLogoUrl;
    /**
     * copyright
     * 版权所有
     */

    @ApiModelProperty(value = "版权所有")
    private String copyright;


    /**
     * isDefault
     * 是否默认（0：否；1：是）
     */
    @ApiModelProperty(value = "是否默认（0：否；1：是）")
    private Integer isDefault;


    /**
     * isEnable
     * 是否启用（0：否；1：是）
     */
    @ApiModelProperty(value = "是否启用（0：否；1：是）")
    private Integer isEnable;

    /**
     * subjectName
     * 登录页主题名称
     */
    @ApiModelProperty(value = "登录页主题名称")
    private String subjectName;

    /**
     * portalContentLoginCorporateSonDtos
     * 企业文化轮播图集合
     */
    @ApiModelProperty(value = "企业文化轮播图集合")
    private List<PortalContentLoginCorporateSonVo> portalContentLoginCorporateSons;

    /**
     * portalContentLoginBackgroundSonDtos
     * 登录页背景轮播图集合
     */
    @ApiModelProperty(value = "登录页背景轮播图集合")
    private List<PortalContentLoginBackgroundSonVo> portalContentLoginBackgroundSons;
}
