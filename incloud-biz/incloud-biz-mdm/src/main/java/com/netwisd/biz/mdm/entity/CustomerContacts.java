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
import java.util.Date;

/**
 * @Description $客户联系人 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-03 17:07:37
 */
@Data
@Table(value = "incloud_biz_mdm_customer_contacts",comment = "客户联系人")
@TableName("incloud_biz_mdm_customer_contacts")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "客户联系人 Entity")
public class CustomerContacts extends IModel<CustomerContacts> {

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
     * contacts_id
     * 联系人ID
     */
    @ApiModelProperty(value="联系人ID")
    @TableField(value="contacts_id")
    @Column(type = DataType.INT, length = 11,  isNull = true, comment = "联系人ID")
    private Integer contactsId;
    /**
     * contacts_name
     * 联系人姓名
     */
    @ApiModelProperty(value="联系人姓名")
    @TableField(value="contacts_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "联系人姓名")
    private String contactsName;
    /**
     * contacts_phone
     * 联系人电话
     */
    @ApiModelProperty(value="联系人电话")
    @TableField(value="contacts_phone")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "联系人电话")
    private String contactsPhone;
}
