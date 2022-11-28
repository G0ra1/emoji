package com.netwisd.biz.mdm.dto;

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
 * @Description 丙方联系人 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-11-16 14:40:00
 */
@Data
@ApiModel(value = "丙方联系人 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ContractPartycContactDto extends IDto{

    public ContractPartycContactDto(Args args){
        super(args);
    }

    /**
     * partyc_id
     * 
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    
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
