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
 * @Description 岗位标识字典 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-27 10:44:20
 */
@Data
@ApiModel(value = "岗位标识字典 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MdmPostTagDictDto extends IDto{

    public MdmPostTagDictDto(Args args){
        super(args);
    }

    /**
     * post_tag_name
     * 岗位标识
     */
    
    @Valid(length = 255)
    @ApiModelProperty(value="岗位标识")
    private String postTagName;

}
