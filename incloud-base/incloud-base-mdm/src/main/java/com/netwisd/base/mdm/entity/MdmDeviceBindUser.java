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
 * @Description 设备绑定人员表 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-03 11:00:47
 */
@Data
@Table(value = "incloud_base_mdm_device_bind_user",comment = "设备绑定人员表")
@TableName("incloud_base_mdm_device_bind_user")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "设备绑定人员表 Entity")
public class MdmDeviceBindUser extends IModel<MdmDeviceBindUser> {

    /**
     * user_id
     * 设备名称
     */
    @ApiModelProperty(value="设备名称")
    @TableField(value="user_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "人员Id")
    private Long userId;
    /**
     * device_id
     * 设备Id
     */
    @ApiModelProperty(value="设备Id")
    @TableField(value="device_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "设备Id")
    private Long deviceId;
    /**
     * bind_time
     * 绑定时间
     */
    @ApiModelProperty(value="绑定时间")
    @TableField(value="bind_time")
    @Column(type = DataType.DATETIME, length = 2,  isNull = true, comment = "绑定时间")
    private LocalDateTime bindTime;
}
