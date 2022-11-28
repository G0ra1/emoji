package com.netwisd.biz.mdm.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.base.common.data.IDto;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.data.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @Description 客户-客户类别关系表 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-10-22 10:24:07
 */
@Data
@ApiModel(value = "客户-客户类别关系表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CustomerCategoryRelationDto extends IDto{

    public CustomerCategoryRelationDto(Args args){
        super(args);
    }

    /**
     * customer_id
     * 
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    
    @ApiModelProperty(value="customer_id")
    private Long customerId;

    /**
     * customer_type_id
     * 
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    
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
