package com.netwisd.base.mdm.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.base.common.data.IDto;
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
 * @Description $职务 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-09-27 10:45:35
 */
@Data
@ApiModel(value = "职务职等字典 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MdmDutyGradeDictDto extends IDto{

    public MdmDutyGradeDictDto(Args args){
        super(args);
    }

    /**
     * duty_grade_name
     * 职等
     */
    
    @Valid(length = 255)
    @ApiModelProperty(value="职等")
    private String dutyGradeName;

    /**
     * duty_sequ_id
     * 所属序列id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Valid(length = 20)
    @ApiModelProperty(value="所属序列id")
    private Long dutySequId;

    /**
     * duty_sequ_name
     * 所属序列名称
     */
    
    @Valid(length = 255)
    @ApiModelProperty(value="所属序列名称")
    private String dutySequName;

}
