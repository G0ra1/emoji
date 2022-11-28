package com.netwisd.biz.mdm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.anntation.Table;
import com.netwisd.common.db.data.DataType;
import com.netwisd.common.db.data.IModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description $供应商银行账号 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-27 16:03:18
 */
@Data
@Table(value = "incloud_biz_mdm_supplier_bank",comment = "供应商银行账号")
@TableName("incloud_biz_mdm_supplier_bank")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "供应商银行账号 Entity")
public class SupplierBank extends IModel<SupplierBank> {

    /**
     * supplier_id
     * 供应商id
     */
    @ApiModelProperty(value="供应商id")
    @TableField(value="supplier_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "供应商id")
    private Long supplierId;
    /**
     * supplier_code
     * 供应商编号
     */
    @ApiModelProperty(value="供应商编号")
    @TableField(value="supplier_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "供应商编号")
    private String supplierCode;
    /**
     * bank_name
     * 开户行
     */
    @ApiModelProperty(value="开户行")
    @TableField(value="bank_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "开户行")
    private String bankName;
    /**
     * bank_account
     * 开户行账号
     */
    @ApiModelProperty(value="开户行账号")
    @TableField(value="bank_account")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "开户行账号")
    private String bankAccount;
}
