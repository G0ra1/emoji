package com.netwisd.base.common.mdm.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.base.common.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @Description $职务 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-09-27 10:45:35
 */
@Data
@ApiModel(value = "职务与用户关系 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MdmDutyUserDto extends IDto{

    public MdmDutyUserDto(Args args){
        super(args);
    }

    /**
     * org_full_duty_id
     * 组织全路径职务ID
     */
    
    @Valid(length = 4000)
    @ApiModelProperty(value="组织全路径职务ID")
    private String orgFullDutyId;

    /**
     * org_full_duty_name
     * 组织全路径职务名称
     */
    
    @Valid(length = 4000)
    @ApiModelProperty(value="组织全路径职务名称")
    private String orgFullDutyName;

    /**
     * duty_id
     * 职务ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Valid(length = 20)
    @ApiModelProperty(value="职务ID")
    private Long dutyId;

    /**
     * duty_code
     * 职务code
     */
    
    @Valid(length = 255)
    @ApiModelProperty(value="职务code")
    private String dutyCode;

    /**
     * duty_name
     * 职务名称
     */
    
    @Valid(length = 255)
    @ApiModelProperty(value="职务名称")
    private String dutyName;

    /**
     * user_id
     * 用户ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Valid(length = 20)
    @ApiModelProperty(value="用户ID")
    private Long userId;

    /**
     * user_name
     * 用户名称
     */
    
    @Valid(length = 255)
    @ApiModelProperty(value="用户名称")
    private String userName;

    /**
     * user_name_ch
     * 用户中文名称
     */
    
    @Valid(length = 255)
    @ApiModelProperty(value="用户中文名称")
    private String userNameCh;

    /**
     * is_master
     * 是否主岗
     */
    
    @Valid(length = 1)
    @ApiModelProperty(value="是否主岗")
    private Integer isMaster;

}
