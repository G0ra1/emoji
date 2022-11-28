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
 * @Description 登录页-子表（背景轮播图） 功能描述...
 * @date 2021-12-27 17:03:27
 */
@Data
@ApiModel(value = "登录页-子表（背景轮播图） Vo")
public class PortalContentLoginBackgroundSonVo extends IVo {

    /**
     * login_id
     * 主表id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value = "主表id")
    private Long loginId;

    /**
     * background_rotation_picture_id
     * 背景轮播图url
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value = "背景轮播图id")
    private Long backgroundRotationPictureId;

    /**
     * background_rotation_picture_url
     * 背景轮播图url
     */

    @ApiModelProperty(value = "背景轮播图url")
    private String backgroundRotationPictureUrl;
}
