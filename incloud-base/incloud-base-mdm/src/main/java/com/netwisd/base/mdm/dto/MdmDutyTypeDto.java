package com.netwisd.base.mdm.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.common.core.data.IDto;
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
 * @Description 职务分类 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-27 16:00:30
 */
@Data
@ApiModel(value = "职务分类 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MdmDutyTypeDto extends IDto{

    public MdmDutyTypeDto(Args args){
        super(args);
    }

    /**
     * duty_type_name
     * 职务分类名称
     */
    
    @Valid(length = 32)
    @ApiModelProperty(value="职务分类名称")
    private String dutyTypeName;

    /**
     * duty_type_code
     * 职务分类Code
     */
    
    @Valid(length = 32)
    @ApiModelProperty(value="职务分类Code")
    private String dutyTypeCode;

    /**
     * sort
     * 排序
     */
    
    @Valid(length = 11)
    @ApiModelProperty(value="排序")
    private Integer sort;

}
