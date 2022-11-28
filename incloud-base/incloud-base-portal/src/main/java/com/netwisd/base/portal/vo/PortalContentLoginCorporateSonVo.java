package com.netwisd.base.portal.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author 云数网讯 cuiran@netwisd.com
 * @Description 登录页-子表（企业文化轮播图） 功能描述...
 * @date 2021-12-27 17:22:50
 */
@Data
@ApiModel(value = "登录页-子表（企业文化轮播图） Vo")
public class PortalContentLoginCorporateSonVo extends IVo {

    /**
     * login_id
     * 登录页主表id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value = "登录页主表id")
    private Long loginId;

    /**
     * corporate_culture_pictures_id
     * 企业文化图片id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="企业文化图片id")
    private Long corporateCulturePicturesId;

    /**
     * corporate_culture_pictures_url
     * 企业文化图片url
     */

    @ApiModelProperty(value = "企业文化图片url")
    private String corporateCulturePicturesUrl;
}
