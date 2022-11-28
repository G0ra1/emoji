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
    import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description $客户银行账号 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-03 17:06:29
 */
@Data
@Table(value = "incloud_biz_mdm_customer_bank",comment = "客户银行账号")
@TableName("incloud_biz_mdm_customer_bank")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "客户银行账号 Entity")
public class CustomerBank extends IModel<CustomerBank> {

    /**
     * customer_id
     * 供应商id
     */
    @ApiModelProperty(value="供应商id")
    @TableField(value="customer_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "供应商id")
    private Long customerId;
    /**
     * customer_code
     * 供应商编号
     */
    @ApiModelProperty(value="供应商编号")
    @TableField(value="customer_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "供应商编号")
    private String customerCode;
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
