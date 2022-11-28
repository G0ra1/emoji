package com.netwisd.base.common.mdm.dto;

import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.constants.Args;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Description 移动设备表 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-03 11:00:47
 */
@Data
@ApiModel(value = "移动设备表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MdmUserDeviceDto extends IDto{

    public MdmUserDeviceDto(Args args){
        super(args);
    }

    /**
     * device_name
     * 设备名称
     */
    @ApiModelProperty(value="设备名称")
    private String deviceName;
    /**
     * device_model
     * 设备型号
     */
    @ApiModelProperty(value="设备型号")
    private String deviceModel;
    /**
     * device_type
     * 系统类型 IOS Android
     */
    @ApiModelProperty(value="系统类型 IOS Android")
    private String deviceType;
    /**
     * device_flag
     * 设备唯一标识
     */
    @ApiModelProperty(value="设备唯一标识")
    private String deviceFlag;
    /**
     * last_login_time
     * 最后登录时间
     */
    @ApiModelProperty(value="最后登录时间")
    private LocalDateTime lastLoginTime;

    /**
     * user_id
     * 用户id
     */
    @ApiModelProperty(value="用户id")
    private Long userId;
}
