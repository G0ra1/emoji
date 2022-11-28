package com.netwisd.biz.mdm.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.netwisd.biz.mdm.dto.ItemSkuColumnDto;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.data.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 物资sku行 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-05-25 19:52:49
 */
@Data
@ApiModel(value = "物资sku行 Vo")
public class ItemSkuLineVo extends IVo{

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

    List<ItemSkuColumnVo> skuColumnList;
}
