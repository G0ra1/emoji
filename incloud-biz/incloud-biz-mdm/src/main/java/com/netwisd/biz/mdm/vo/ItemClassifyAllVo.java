package com.netwisd.biz.mdm.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 物资分类 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-19 15:14:41
 */
@Data
@ApiModel(value = "物资 Vo")
public class ItemClassifyAllVo extends ItemClassifyVo{
    /**
     * subList
     * 子集列表
     */
    @ApiModelProperty(value="子集列表")
    private List<ItemClassifyAllVo> kids = new ArrayList<>();
    

}
