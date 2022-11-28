package com.netwisd.base.common.mdm.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 子系统 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-03 11:00:47
 */
@Data
@ApiModel(value = "子系统 Vo")
public class MdmSysVo extends IVo{

    /**
     * sys_code
     * 系统code
     */
    
    @ApiModelProperty(value="系统code")
    private String sysCode;
    /**
     * sys_name
     * 系统名称
     */
    
    @ApiModelProperty(value="系统名称")
    private String sysName;
    /**
     * sys_icon
     * 图标
     */
    
    @ApiModelProperty(value="图标")
    private String sysIcon;
    /**
     * sort
     * 排序字段
     */
    
    @ApiModelProperty(value="排序字段")
    private Integer sort;
    /**
     * status
     * 状态标识
     */
    
    @ApiModelProperty(value="状态标识")
    private Integer status;
}
