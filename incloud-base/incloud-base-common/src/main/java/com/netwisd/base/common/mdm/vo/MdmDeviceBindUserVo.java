package com.netwisd.base.common.mdm.vo;

import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description 设备绑定人员表 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-03 11:00:47
 */
@Data
@ApiModel(value = "设备绑定人员表 Entity")
public class MdmDeviceBindUserVo extends IVo {

    /**
     * user_id
     * 设备名称
     */
    @ApiModelProperty(value="设备名称")
    private Long userId;
    /**
     * device_id
     * 设备Id
     */
    @ApiModelProperty(value="设备Id")
    private Long deviceId;
    /**
     * bind_time
     * 绑定时间
     */
    @ApiModelProperty(value="绑定时间")
    private LocalDateTime bindTime;
}
