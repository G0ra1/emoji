package com.netwisd.base.common.mdm.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.base.common.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description 组织级别类型字典 功能描述...
 * @author 云数网讯 zouliming@netwisd.com@netwisd.com
 * @date 2021-08-26 09:56:26
 */
@Data
@ApiModel(value = "组织级别类型字典 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MdmOrgLevelDictDto extends IDto{

    public MdmOrgLevelDictDto(Args args){
        super(args);
    }

    /**
     * org_level
     * 组织级别类型
     */
    @ApiModelProperty(value="组织级别类型")
    @Valid(length = 255) 
    private String orgLevel;

}
