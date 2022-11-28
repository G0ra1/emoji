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
 * @Description 岗位职等 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-27 10:54:55
 */
@Data
@ApiModel(value = "岗位职等 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MdmPostGradeDictDto extends IDto{

    public MdmPostGradeDictDto(Args args){
        super(args);
    }

    /**
     * post_grade_name
     * 职等
     */
    
    @Valid(length = 255)
    @ApiModelProperty(value="职等")
    private String postGradeName;

    /**
     * post_sequ_id
     * 所属序列id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Valid(length = 20)
    @ApiModelProperty(value="所属序列id")
    private Long postSequId;

    /**
     * post_sequ_name
     * 所属序列名称
     */
    
    @Valid(length = 255)
    @ApiModelProperty(value="所属序列名称")
    private String postSequName;

}
