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
 * @Description mdm通用字典  功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-16 15:58:36
 */
@Data
@ApiModel(value = "mdm通用字典  Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MdmCommDictDto extends IDto{

    public MdmCommDictDto(Args args){
        super(args);
    }

    /**
     * dict_type_id
     * 字典分类id
     */
    
    @Valid(length = 32)
    @ApiModelProperty(value="字典分类id")
    private String dictTypeId;

    /**
     * dict_code
     * 字典code
     */
    
    @Valid(length = 32)
    @ApiModelProperty(value="字典code")
    private String dictCode;

    /**
     * dict_name
     * 字典名称
     */
    
    @Valid(length = 11)
    @ApiModelProperty(value="字典名称")
    private String dictName;

    /**
     * create_user_id
     * 创建人ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    
    @ApiModelProperty(value="创建人ID")
    private Long createUserId;

    /**
     * create_user_name
     * 创建人名称
     */
    
    
    @ApiModelProperty(value="创建人名称")
    private String createUserName;

}
