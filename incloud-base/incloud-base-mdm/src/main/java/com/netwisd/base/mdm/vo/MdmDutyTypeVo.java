package com.netwisd.base.mdm.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 职务分类 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-27 16:00:30
 */
@Data
@ApiModel(value = "职务分类 Vo")
public class MdmDutyTypeVo extends IVo{

    /**
     * duty_type_name
     * 职务分类名称
     */
    
    @ApiModelProperty(value="职务分类名称")
    private String dutyTypeName;
    /**
     * duty_type_code
     * 职务分类Code
     */
    
    @ApiModelProperty(value="职务分类Code")
    private String dutyTypeCode;
    /**
     * sort
     * 排序
     */
    
    @ApiModelProperty(value="排序")
    private Integer sort;
}
