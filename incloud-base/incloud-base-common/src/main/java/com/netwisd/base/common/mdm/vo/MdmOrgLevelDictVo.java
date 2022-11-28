package com.netwisd.base.common.mdm.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 组织级别类型字典 功能描述...
 * @author 云数网讯 zouliming@netwisd.com@netwisd.com
 * @date 2021-08-26 09:56:26
 */
@Data
@ApiModel(value = "组织级别类型字典 Vo")
public class MdmOrgLevelDictVo extends IVo{

    /**
     * org_level
     * 组织级别类型
     */
    @ApiModelProperty(value="组织级别类型")
    private String orgLevel;
}
