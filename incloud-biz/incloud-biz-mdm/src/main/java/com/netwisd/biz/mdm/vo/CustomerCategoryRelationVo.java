package com.netwisd.biz.mdm.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 客户-客户类别关系表 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-10-22 10:24:07
 */
@Data
@ApiModel(value = "客户-客户类别关系表 Vo")
public class CustomerCategoryRelationVo extends IVo{

    /**
     * customer_id
     * 
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="customer_id")
    private Long customerId;
    /**
     * customer_type_id
     * 
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="customer_type_id")
    private Long customerTypeId;

    /**
     * customer_type_code
     *
     */
    @ApiModelProperty(value="customer_type_code")
    private String customerTypeCode;

    /**
     * customer_type_name
     *
     */
    @ApiModelProperty(value="customer_type_name")
    private String customerTypeName;
}
