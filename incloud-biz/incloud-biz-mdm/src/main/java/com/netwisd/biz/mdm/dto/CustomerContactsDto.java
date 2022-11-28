package com.netwisd.biz.mdm.dto;

import com.netwisd.common.core.constants.Args;
import com.netwisd.base.common.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @Description 客户联系人 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-03 17:07:37
 */
@Data
@ApiModel(value = "客户联系人 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CustomerContactsDto extends IDto{

    public CustomerContactsDto(Args args){
        super(args);
    }

    /**
     * customer_id
     * 供应商id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    
    @ApiModelProperty(value="供应商id")
    private Long customerId;

    /**
     * customer_code
     * 供应商编号
     */
    
    
    @ApiModelProperty(value="供应商编号")
    private String customerCode;

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
