package com.netwisd.base.mdm.entity;

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

/**
 * @Description 移动设备表 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-03 11:00:47
 */
@Data
@Table(value = "incloud_base_mdm_user_device",comment = "移动设备表")
@TableName("incloud_base_mdm_user_device")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "移动设备表 Entity")
public class MdmUserDevice extends IModel<MdmUserDevice> {

    /**
     * device_name
     * 设备名称
     */
    @ApiModelProperty(value="设备名称")
    @TableField(value="device_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "设备名称")
    private String deviceName;
    /**
     * device_model
     * 设备型号
     */
    @ApiModelProperty(value="设备型号")
    @TableField(value="device_model")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "设备型号")
    private String deviceModel;
    /**
     * device_type
     * 系统类型 IOS Android
     */
    @ApiModelProperty(value="系统类型 IOS Android")
    @TableField(value="device_type")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "系统类型 IOS Android")
    private String deviceType;
    /**
     * device_flag
     * 设备唯一标识
     */
    @ApiModelProperty(value="设备唯一标识")
    @TableField(value="device_flag")
    @Column(type = DataType.VARCHAR, length = 128,  isNull = true, comment = "设备唯一标识")
    private String deviceFlag;
    /**
     * last_login_time
     * 最后登录时间
     */
    @ApiModelProperty(value="最后登录时间")
    @TableField(value="last_login_time")
    @Column(type = DataType.DATETIME, length = 2,  isNull = true, comment = "最后登录时间")
    private LocalDateTime lastLoginTime;
}
