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
 * @Description 丙方联系人 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-11-16 14:40:00
 */
@Data
@ApiModel(value = "丙方联系人 Vo")
public class ContractPartycContactVo extends IVo{

    /**
     * partyc_id
     * 
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="partyc_id")
    private Long partycId;
    /**
     * partyc_code
     * 
     */
    
    @ApiModelProperty(value="partyc_code")
    private String partycCode;
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
