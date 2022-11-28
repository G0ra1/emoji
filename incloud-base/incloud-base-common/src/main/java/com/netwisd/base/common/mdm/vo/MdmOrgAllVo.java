package com.netwisd.base.common.mdm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 组织 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2021-08-14 10:08:12
 */
@Data
@ApiModel(value = "组织 Vo")
public class MdmOrgAllVo extends MdmOrgVo{
    /**
     * subList
     * 子集列表
     */
    @ApiModelProperty(value="子集列表")
    private List<MdmOrgAllVo> kids = new ArrayList<>();
}
