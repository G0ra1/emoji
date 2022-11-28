package com.netwisd.biz.mdm.dto;

import com.netwisd.common.core.constants.Args;
import com.netwisd.base.common.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import java.util.List;

import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @Description 物资sku行 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-05-25 19:52:49
 */
@Data
@ApiModel(value = "物资sku行 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ItemSkuLineDto extends IDto{

    public ItemSkuLineDto(Args args){
        super(args);
    }

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
     * sku_code
     * sku编码
     */
    @ApiModelProperty(value="sku编码")
    private String skuCode;

    List<ItemSkuColumnDto> skuColumnList;

}
