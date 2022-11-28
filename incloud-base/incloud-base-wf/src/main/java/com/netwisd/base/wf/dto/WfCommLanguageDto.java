package com.netwisd.base.wf.dto;

import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.anntation.Valid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * @Description 审批时常用语 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-09-17 14:45:42
 */
@Data
@ApiModel(value = "审批时常用语 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WfCommLanguageDto extends IDto {

    /**
     * use_user_id
     * 使用人id
     */
    @ApiModelProperty(value="使用人id")
    
    private String useUserId;

    /**
     * use_user_name
     * 使用人名称
     */
    @ApiModelProperty(value="使用人名称")
    
    private String useUserName;

    /**
     * content
     * 常用语内容
     */
    @ApiModelProperty(value="常用语内容")
    @Valid(length = 255) 
    private String content;

    /**
     * is_general
     * 是否是通用 常用语
     */
    @ApiModelProperty(value="是否是通用 常用语")
    
    private Integer isGeneral;

}
