package com.netwisd.base.common.mdm.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description $职务 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-09-27 10:45:35
 */
@Data
@ApiModel(value = "职务与用户关系 Vo")
public class MdmDutyUserVo extends IVo{

    /**
     * org_full_duty_id
     * 组织全路径职务ID
     */
    
    @ApiModelProperty(value="组织全路径职务ID")
    private String orgFullDutyId;
    /**
     * org_full_duty_name
     * 组织全路径职务名称
     */
    
    @ApiModelProperty(value="组织全路径职务名称")
    private String orgFullDutyName;
    /**
     * duty_id
     * 职务ID
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="职务ID")
    private Long dutyId;
    /**
     * duty_code
     * 职务code
     */
    
    @ApiModelProperty(value="职务code")
    private String dutyCode;
    /**
     * duty_name
     * 职务名称
     */
    
    @ApiModelProperty(value="职务名称")
    private String dutyName;
    /**
     * user_id
     * 用户ID
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="用户ID")
    private Long userId;
    /**
     * user_name
     * 用户名称
     */
    
    @ApiModelProperty(value="用户名称")
    private String userName;
    /**
     * user_name_ch
     * 用户中文名称
     */
    
    @ApiModelProperty(value="用户中文名称")
    private String userNameCh;
    /**
     * is_master
     * 是否主岗
     */
    
    @ApiModelProperty(value="是否主岗")
    private Integer isMaster;
}
