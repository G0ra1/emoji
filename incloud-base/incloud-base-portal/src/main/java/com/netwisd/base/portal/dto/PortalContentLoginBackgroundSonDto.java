package com.netwisd.base.portal.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.base.common.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Date;

import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author 云数网讯 cuiran@netwisd.com
 * @Description 登录页-子表（背景轮播图） 功能描述...
 * @date 2021-12-27 17:03:27
 */
@Data
@ApiModel(value = "登录页-子表（背景轮播图） Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PortalContentLoginBackgroundSonDto extends IDto {

    public PortalContentLoginBackgroundSonDto(Args args) {
        super(args);
    }

    /**
     * login_id
     * 主表id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value = "主表id")
    private Long loginId;

    /**
     * background_rotation_picture_id
     * 背景轮播图id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value = "背景轮播图Id")
    private Long backgroundRotationPictId;

    /**
     * background_rotation_picture_url
     * 背景轮播图url
     */
    @ApiModelProperty(value = "背景轮播图url")
    private String backgroundRotationPictureUrl;

}
