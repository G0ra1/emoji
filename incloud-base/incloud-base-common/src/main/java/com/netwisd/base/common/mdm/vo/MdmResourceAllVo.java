package com.netwisd.base.common.mdm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: limengzheng@netwisd.com
 * @Date: 2021/9/10 11:31
 */
@Data
@ApiModel(value = "资源管理 Vo")
public class MdmResourceAllVo extends MdmResourceVo{
    /**
     * subList
     * 子集列表
     */
    @ApiModelProperty(value="子集列表")
    private List<MdmResourceAllVo> kids = new ArrayList<>();
}
