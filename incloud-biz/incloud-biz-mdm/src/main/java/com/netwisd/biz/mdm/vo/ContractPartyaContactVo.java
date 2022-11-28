package com.netwisd.biz.mdm.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 甲方联系人 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-11-16 14:31:37
 */
@Data
@ApiModel(value = "甲方联系人 Vo")
public class ContractPartyaContactVo extends IVo{

    /**
     * partya_id
     * 
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="partya_id")
    private Long partyaId;
    /**
     * partya_code
     * 
     */
    
    @ApiModelProperty(value="partya_code")
    private String partyaCode;
    /**
     * contacts_id
     * 联系人ID
     */
    
    @ApiModelProperty(value="联系人ID")
    private Integer contactsId;
    /**
     * contacts_name
     * 联系人姓名
     */
    
    @ApiModelProperty(value="联系人姓名")
    private String contactsName;
    /**
     * contacts_phone
     * 联系人电话
     */
    
    @ApiModelProperty(value="联系人电话")
    private String contactsPhone;
}
