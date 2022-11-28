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
 * @Description 登录页-子表（企业文化轮播图） 功能描述...
 * @date 2021-12-27 17:22:50
 */
@Data
@ApiModel(value = "登录页-子表（企业文化轮播图） Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PortalContentLoginCorporateSonDto extends IDto {

    public PortalContentLoginCorporateSonDto(Args args) {
        super(args);
    }

    /**
     * login_id
     * 登录页主表id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value = "登录页主表id")
    private Long loginId;

    /**
     * corporate_culture_pictures_id
     * 企业文化图片id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value = "企业文化图片id")
    private Long corporateCulturePicturesId;

    /**
     * corporate_culture_pictures_url
     * 企业文化图片url
     */
    @ApiModelProperty(value = "企业文化图片url")
    private String corporateCulturePicturesUrl;

}
