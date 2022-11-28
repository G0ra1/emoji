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
 * @Description 客户银行账号 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-03 17:06:29
 */
@Data
@ApiModel(value = "客户银行账号 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CustomerBankDto extends IDto{

    public CustomerBankDto(Args args){
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
     * bank_name
     * 开户行
     */
    
    
    @ApiModelProperty(value="开户行")
    private String bankName;

    /**
     * bank_account
     * 开户行账号
     */
    
    
    @ApiModelProperty(value="开户行账号")
    private String bankAccount;

}
