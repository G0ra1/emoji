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
 * @Description $客户-客户类别关系表 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-10-22 10:24:07
 */
@Data
@Table(value = "incloud_biz_mdm_customer_category_relation",comment = "客户-客户类别关系表")
@TableName("incloud_biz_mdm_customer_category_relation")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "客户-客户类别关系表 Entity")
public class CustomerCategoryRelation extends IModel<CustomerCategoryRelation> {

    /**
     * customer_id
     * 
     */
    @ApiModelProperty(value="customer_id")
    @TableField(value="customer_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "")
    private Long customerId;
    /**
     * customer_type_id
     * 
     */
    @ApiModelProperty(value="customer_type_id")
    @TableField(value="customer_type_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "")
    private Long customerTypeId;
    /**
     * customer_type_code
     *
     */
    @ApiModelProperty(value="customer_type_code")
    @TableField(value="customer_type_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "" )
    private String customerTypeCode;
    /**
     * customer_type_name
     *
     */
    @ApiModelProperty(value="customer_type_name")
    @TableField(value="customer_type_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "")
    private String customerTypeName;
}
