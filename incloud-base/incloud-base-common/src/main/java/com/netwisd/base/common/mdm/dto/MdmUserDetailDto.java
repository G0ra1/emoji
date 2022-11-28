package com.netwisd.base.common.mdm.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.common.core.data.IDto;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Description 用户详情 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-26 09:09:37
 */
@Data
@ApiModel(value = "用户详情 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MdmUserDetailDto extends IDto{

    public MdmUserDetailDto(Args args){
        super(args);
    }

    /**
     * user_id
     * 用户id
     */
    @ApiModelProperty(value="用户id")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    @Valid(length = 20) 
    private Long userId;

    /**
     * contact_name
     * 紧急联系人姓名
     */
    @ApiModelProperty(value="紧急联系人姓名")
    
    private String contactName;

    /**
     * contact_with_oneself
     * 紧急联系人与本人关系
     */
    @ApiModelProperty(value="紧急联系人与本人关系")
    
    private String contactWithOneself;

    /**
     * contact_num
     * 紧急联系人联系电话
     */
    @ApiModelProperty(value="紧急联系人联系电话")
    
    private String contactNum;

    /**
     * contact_addr
     * 紧急联系人联系地址
     */
    @ApiModelProperty(value="紧急联系人联系地址")
    
    private String contactAddr;

    /**
     * contact_email
     * 紧急联系人电子邮箱
     */
    @ApiModelProperty(value="紧急联系人电子邮箱")
    
    private String contactEmail;

    /**
     * contact_zipcode
     * 紧急联系人邮政编码
     */
    @ApiModelProperty(value="紧急联系人邮政编码")
    
    private String contactZipcode;

    /**
     * job_level
     * 职级
     */
    @ApiModelProperty(value="职级")
    
    private Integer jobLevel;

    /**
     * job_level_start_time
     * 职级开始时间
     */
    @ApiModelProperty(value="职级开始时间")
    
    private LocalDateTime jobLevelStartTime;

    /**
     * job_level_end_time
     * 职级结束时间
     */
    @ApiModelProperty(value="职级结束时间")
    
    private LocalDateTime jobLevelEndTime;

    /**
     * job_level_year
     * 任职级年限
     */
    @ApiModelProperty(value="任职级年限")
    
    private Integer jobLevelYear;

    /**
     * duty_level_start_time
     * 职务开始时间
     */
    @ApiModelProperty(value="职务开始时间")
    
    private LocalDateTime dutyLevelStartTime;

    /**
     * duty_level_end_time
     * 职务结束时间
     */
    @ApiModelProperty(value="职务结束时间")
    
    private LocalDateTime dutyLevelEndTime;

    /**
     * duty_id
     * 职务ID
     */
    @ApiModelProperty(value="职务ID")
    
    private String dutyId;

    /**
     * duty_name
     * 职务名称
     */
    @ApiModelProperty(value="职务名称")
    
    private String dutyName;

    /**
     * duty_in_charge
     * 主管或从事工作
     */
    @ApiModelProperty(value="主管或从事工作")
    
    private String dutyInCharge;

    /**
     * duty_is_main
     * 首要职务;0否；1是；
     */
    @ApiModelProperty(value="首要职务;0否；1是；")
    
    private String dutyIsMain;

    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    
    private String remark;

}
