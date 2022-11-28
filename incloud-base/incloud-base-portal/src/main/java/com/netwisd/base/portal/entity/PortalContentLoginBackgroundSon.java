package com.netwisd.base.portal.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.anntation.Table;
import com.netwisd.common.db.data.DataType;
import com.netwisd.common.db.data.IModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author 云数网讯 cuiran@netwisd.com
 * @Description $登录页-子表（背景轮播图） 功能描述...
 * @date 2021-12-27 17:03:27
 */
@Data
@Table(value = "incloud_base_portal_content_login_background_son", comment = "登录页-子表（背景轮播图）")
@TableName("incloud_base_portal_content_login_background_son")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "登录页-子表（背景轮播图） Entity")
public class PortalContentLoginBackgroundSon extends IModel<PortalContentLoginBackgroundSon> {

    /**
     * login_id
     * 主表id
     */
    @ApiModelProperty(value = "主表id")
    @TableField(value = "login_id")
    @Column(type = DataType.BIGINT, length = 20, isNull = true, comment = "主表id")
    private Long loginId;

    /**
     * background_rotation_picture_id
     * 背景轮播图id
     */
    @ApiModelProperty(value = "背景轮播图id")
    @TableField(value = "background_rotation_picture_id")
    @Column(type = DataType.BIGINT, length = 20, isNull = true, comment = "背景轮播图Id")
    private Long backgroundRotationPictureId;

    /**
     * background_rotation_picture_url
     * 背景轮播图url
     */
    @ApiModelProperty(value = "背景轮播图url")
    @TableField(value = "background_rotation_picture_url")
    @Column(type = DataType.VARCHAR, length = 255, isNull = true, comment = "背景轮播图url")
    private String backgroundRotationPictureUrl;

}
