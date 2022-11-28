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
 * @Description $登录页设置表 功能描述...
 * @date 2021-12-27 16:36:19
 */
@Data
@Table(value = "incloud_base_portal_content_login", comment = "登录页设置表")
@TableName("incloud_base_portal_content_login")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "登录页设置表 Entity")
public class PortalContentLogin extends IModel<PortalContentLogin> {

    /**
     * login_page_logo_id
     * 登录页logoId
     */
    @ApiModelProperty(value = "登录页logoId")
    @TableField(value = "login_page_logo_id")
    @Column(type = DataType.BIGINT, length = 20, isNull = true, comment = "登录页logoId")
    private String loginPageLogoId;

    /**
     * login_page_logo_url
     * 登录页logo
     */
    @ApiModelProperty(value = "登录页logo")
    @TableField(value = "login_page_logo_url")
    @Column(type = DataType.VARCHAR, length = 255, isNull = true, comment = "登录页logo")
    private String loginPageLogoUrl;

    /**
     * system_logo_id
     * 系统logoId
     */
    @ApiModelProperty(value = "系统logoId")
    @TableField(value = "system_logo_id")
    @Column(type = DataType.BIGINT, length = 20, isNull = true, comment = "系统logoId")
    private Long systemLogoId;

    /**
     * system_logo_url
     * 系统logo
     */
    @ApiModelProperty(value = "系统logo")
    @TableField(value = "system_logo_url")
    @Column(type = DataType.VARCHAR, length = 255, isNull = true, comment = "系统logo")
    private String systemLogoUrl;

    /**
     * copyright
     * 版权所有
     */
    @ApiModelProperty(value = "版权所有")
    @TableField(value = "copyright")
    @Column(type = DataType.VARCHAR, length = 255, isNull = true, comment = "版权所有")
    private String copyright;

    /**
     * isDefault
     * 是否默认（0：否；1：是）
     */
    @ApiModelProperty(value = "是否默认（0：否；1：是）")
    @TableField(value = "is_default")
    @Column(type = DataType.INT, length = 255, isNull = true, comment = "是否默认（0：否；1：是）")
    private Integer isDefault;


    /**
     * isEnable
     * 是否启用（0：否；1：是）
     */
    @ApiModelProperty(value = "是否启用（0：否；1：是）")
    @TableField(value = "is_enable")
    @Column(type = DataType.INT, length = 255, isNull = true, comment = "是否启用（0：否；1：是）")
    private Integer isEnable;

    /**
     * subjectName
     * 登录页主题名称
     */
    @ApiModelProperty(value = "登录页主题名称")
    @TableField(value = "subject_name")
    @Column(type = DataType.VARCHAR, length = 255, isNull = true, comment = "登录页主题名称")
    private String subjectName;
}
