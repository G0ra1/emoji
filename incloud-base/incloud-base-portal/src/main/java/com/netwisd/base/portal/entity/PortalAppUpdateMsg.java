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
 * @Description $app安装包更新记录表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-01-06 10:16:32
 */
@Data
@Table(value = "incloud_base_portal_app_update_msg",comment = "app安装包更新记录表")
@TableName("incloud_base_portal_app_update_msg")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "app安装包更新记录表 Entity")
public class PortalAppUpdateMsg extends IModel<PortalAppUpdateMsg> {

    /**
    * app_id
    * appid
    */
    @ApiModelProperty(value="appid")
    @TableField(value="app_id")
    @Column(type = DataType.VARCHAR, length = 10,  isNull = false, comment = "appid")
    private String appId;

    /**
    * app_name
    * app名称
    */
    @ApiModelProperty(value="app名称")
    @TableField(value="app_name")
    @Column(type = DataType.VARCHAR, length = 20,  isNull = false, comment = "app名称")
    private String appName;

    /**
    * app_type
    * app渠道 
    */
    @ApiModelProperty(value="app渠道 ")
    @TableField(value="app_type")
    @Column(type = DataType.VARCHAR, length = 20,  isNull = false, comment = "app渠道 ")
    private String appType;

    /**
    * app_version
    * 版本号
    */
    @ApiModelProperty(value="版本号")
    @TableField(value="app_version")
    @Column(type = DataType.VARCHAR, length = 20,  isNull = false, comment = "版本号")
    private String appVersion;

    /**
    * download_url
    * 下载地址
    */
    @ApiModelProperty(value="下载地址")
    @TableField(value="download_url")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "下载地址")
    private String downloadUrl;

    /**
    * update_log
    * 版本变更说明
    */
    @ApiModelProperty(value="版本变更说明")
    @TableField(value="update_log")
    @Column(type = DataType.VARCHAR, length = 500,  isNull = true, comment = "版本变更说明")
    private String updateLog;

    /**
    * update_install
    * 是否强制升级 0 否 1 是
    */
    @ApiModelProperty(value="是否强制升级 0 否 1 是")
    @TableField(value="update_install")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "是否强制升级 0 否 1 是")
    private Integer updateInstall;

}
