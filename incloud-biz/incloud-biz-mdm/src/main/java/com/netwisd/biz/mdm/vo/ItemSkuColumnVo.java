package com.netwisd.biz.mdm.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 物资sku列 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-05-25 19:54:39
 */
@Data
@ApiModel(value = "物资sku列 Vo")
public class ItemSkuColumnVo extends IVo{

    /**
     * line_id
     * 行id
     */
    @ApiModelProperty(value="行id")
    private Long lineId;

    /**
     * item_id
     * 物资id
     */
    @ApiModelProperty(value="物资id")
    private Long itemId;

    /**
     * item_code
     * 物资code
     */
    @ApiModelProperty(value="物资code")
    private String itemCode;

    /**
     * item_name
     * 物资名称
     */
    @ApiModelProperty(value="物资名称")
    private String itemName;

    /**
     * sku_id
     * 属性id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="属性id")
    private Long skuId;

    /**
     * sku_name
     * 属性名称
     */
    
    @ApiModelProperty(value="属性名称")
    private String skuName;
    /**
     * sku_sort
     * 属性排序
     */
    
    @ApiModelProperty(value="属性排序")
    private Integer skuSort;
    /**
     * sku_value
     * 属性值
     */
    
    @ApiModelProperty(value="属性值")
    private String skuValue;
}
