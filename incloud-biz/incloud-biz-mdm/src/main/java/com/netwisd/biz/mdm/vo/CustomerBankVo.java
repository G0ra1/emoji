package com.netwisd.biz.mdm.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 客户银行账号 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-03 17:06:29
 */
@Data
@ApiModel(value = "客户银行账号 Vo")
public class CustomerBankVo extends IVo{

    /**
     * customer_id
     * 供应商id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
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
