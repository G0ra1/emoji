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
 * @Description mdm通用字典  功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-16 15:58:36
 */
@Data
@ApiModel(value = "mdm通用字典  Vo")
public class MdmCommDictVo extends IVo{

    /**
     * dict_type_id
     * 字典分类id
     */
    
    @ApiModelProperty(value="字典分类id")
    private String dictTypeId;
    /**
     * dict_code
     * 字典code
     */
    
    @ApiModelProperty(value="字典code")
    private String dictCode;
    /**
     * dict_name
     * 字典名称
     */
    
    @ApiModelProperty(value="字典名称")
    private String dictName;
    /**
     * create_user_id
     * 创建人ID
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="创建人ID")
    private Long createUserId;
    /**
     * create_user_name
     * 创建人名称
     */
    
    @ApiModelProperty(value="创建人名称")
    private String createUserName;
}
