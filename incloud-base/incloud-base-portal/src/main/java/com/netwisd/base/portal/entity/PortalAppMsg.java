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
 * @Description $应用市场app信息表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-12-23 15:07:50
 */
@Data
@Table(value = "incloud_base_portal_app_msg",comment = "应用市场app信息表")
@TableName("incloud_base_portal_app_msg")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "应用市场app信息表 Entity")
public class PortalAppMsg extends IModel<PortalAppMsg> {

    /**
    * bundle_id
    * 
    */
    @ApiModelProperty(value="bundle_id")
    @TableField(value="bundle_id")
    @Column(type = DataType.VARCHAR, length = 25,  isNull = true, comment = "")
    private String bundleId;

    /**
    * version
    * 
    */
    @ApiModelProperty(value="version")
    @TableField(value="version")
    @Column(type = DataType.VARCHAR, length = 25,  isNull = true, comment = "")
    private String version;

    /**
    * platform
    * 
    */
    @ApiModelProperty(value="platform")
    @TableField(value="platform")
    @Column(type = DataType.VARCHAR, length = 25,  isNull = true, comment = "")
    private String platform;

    /**
    * market_name
    * 
    */
    @ApiModelProperty(value="market_name")
    @TableField(value="market_name")
    @Column(type = DataType.VARCHAR, length = 25,  isNull = true, comment = "")
    private String marketName;

    /**
    * state
    * 
    */
    @ApiModelProperty(value="state")
    @TableField(value="state")
    @Column(type = DataType.VARCHAR, length = 25,  isNull = true, comment = "")
    private String state;

    /**
     * url
     *
     */
    @ApiModelProperty(value="url")
    @TableField(value="url")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "")
    private String url;

}
