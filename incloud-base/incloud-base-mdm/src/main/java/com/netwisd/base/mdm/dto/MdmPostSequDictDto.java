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
 * @Description 岗位序列 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-27 11:01:12
 */
@Data
@ApiModel(value = "岗位序列 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MdmPostSequDictDto extends IDto{

    public MdmPostSequDictDto(Args args){
        super(args);
    }

    /**
     * post_sequ_name
     * 岗位序列
     */
    
    @Valid(length = 255)
    @ApiModelProperty(value="岗位序列")
    private String postSequName;

}
