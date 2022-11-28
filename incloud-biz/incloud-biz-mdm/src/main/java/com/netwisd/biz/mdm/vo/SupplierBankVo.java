package com.netwisd.biz.mdm.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 供应商银行账号 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-27 16:03:18
 */
@Data
@ApiModel(value = "供应商银行账号 Vo")
public class SupplierBankVo extends IVo{

    /**
     * supplier_id
     * 供应商id
     */
    @ApiModelProperty(value="供应商id")
    private Long supplierId;
    /**
     * supplier_code
     * 供应商编号
     */
    @ApiModelProperty(value="供应商编号")
    private String supplierCode;
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
