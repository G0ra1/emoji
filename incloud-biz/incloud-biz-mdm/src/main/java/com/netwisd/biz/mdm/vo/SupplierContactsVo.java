package com.netwisd.biz.mdm.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 供应商联系人 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-27 16:05:59
 */
@Data
@ApiModel(value = "供应商联系人 Vo")
public class SupplierContactsVo extends IVo{

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
