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
 * @Description $登录页-子表（企业文化轮播图） 功能描述...
 * @author 云数网讯 cuiran@netwisd.com
 * @date 2021-12-27 17:22:50
 */
@Data
@Table(value = "incloud_base_portal_content_login_corporate_son",comment = "登录页-子表（企业文化轮播图）")
@TableName("incloud_base_portal_content_login_corporate_son")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "登录页-子表（企业文化轮播图） Entity")
public class PortalContentLoginCorporateSon extends IModel<PortalContentLoginCorporateSon> {

    /**
    * login_id
    * 登录页主表id
    */
    @ApiModelProperty(value="登录页主表id")
    @TableField(value="login_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "登录页主表id")
    private Long loginId;

    /**
     * corporate_culture_pictures_id
     * 企业文化图片id
     */
    @ApiModelProperty(value="企业文化图片id")
    @TableField(value="corporate_culture_pictures_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "企业文化图片id")
    private Long corporateCulturePicturesId;

    /**
    * corporate_culture_pictures_url
    * 企业文化图片url
    */
    @ApiModelProperty(value="企业文化图片url")
    @TableField(value="corporate_culture_pictures_url")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "企业文化图片url")
    private String corporateCulturePicturesUrl;

}
