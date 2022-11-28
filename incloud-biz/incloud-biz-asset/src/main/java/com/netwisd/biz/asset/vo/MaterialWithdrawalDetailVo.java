package com.netwisd.biz.asset.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
/**
 * @Description 资产退库详情 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-08-01 10:45:42
 */
@Data
@ApiModel(value = "资产退库详情 Vo")
public class MaterialWithdrawalDetailVo extends IVo{

    /**
     * withdrawal_id
     * 退库id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="退库id")
    private Long withdrawalId;
    /**
     * assets_id
     * 资产id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="资产id")
    private Long assetsId;
    /**
     * assets_detail_id
     * 资产明细id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="资产明细id")
    private Long assetsDetailId;
    /**
     * assets_delivery_id
     * 资产出库id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="资产出库id")
    private Long assetsDeliveryId;
    /**
     * item_id
     * 物资id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="物资id")
    private Long itemId;
    /**
     * item_code
     * 物资编码
     */
    @ApiModelProperty(value="物资编码")
    private String itemCode;
    /**
     * item_name
     * 物资名称
     */
    @ApiModelProperty(value="物资名称")
    private String itemName;
    /**
     * item_type
     * 物资类型
     */
    @ApiModelProperty(value="物资类型")
    private String itemType;
    /**
     * item_type_name
     * 物资类型名称
     */
    @ApiModelProperty(value="物资类型名称")
    private String itemTypeName;
    /**
     * unit_code
     * 物资单位编码
     */
    @ApiModelProperty(value="物资单位编码")
    private String unitCode;
    /**
     * unit_name
     * 物资单位名称
     */
    @ApiModelProperty(value="物资单位名称")
    private String unitName;
    /**
     * material_quality
     * 物资材质
     */
    @ApiModelProperty(value="物资材质")
    private String materialQuality;
    /**
     * standard
     * 物资标准
     */
    @ApiModelProperty(value="物资标准")
    private String standard;
    /**
     * specs
     * 物资规格
     */
    @ApiModelProperty(value="物资规格")
    private String specs;
    /**
     * sku_code
     * sku编码
     */
    @ApiModelProperty(value="sku编码")
    private String skuCode;
    /**
     * sku_full_id
     * sku全路径id
     */
    @ApiModelProperty(value="sku全路径id")
    private String skuFullId;
    /**
     * sku_full_name
     * sku全路径名称
     */
    @ApiModelProperty(value="sku全路径名称")
    private String skuFullName;
    /**
     * assets_code
     * 资产编号
     */
    @ApiModelProperty(value="资产编号")
    private String assetsCode;
    /**
     * assets_label
     * 资产标签
     */
    @ApiModelProperty(value="资产标签")
    private String assetsLabel;
    /**
     * warehouse_id
     * 仓库地点
     */
    @ApiModelProperty(value="仓库地点")
    private String warehouseId;
    /**
     * warehouse_name
     * 仓库地点
     */
    @ApiModelProperty(value="仓库地点")
    private String warehouseName;
    /**
     * shelf_id
     * 货架号
     */
    @ApiModelProperty(value="货架号")
    private String shelfId;
    /**
     * shelf_name
     * 货架号
     */
    @ApiModelProperty(value="货架号")
    private String shelfName;
    /**
     * tax_rate
     * 税率
     */
    @ApiModelProperty(value="税率")
    private BigDecimal taxRate;
    /**
     * accept_number
     * 领用数量
     */
    @ApiModelProperty(value="领用数量")
    private BigDecimal acceptNumber;
    /**
     * withdrawal_number
     * 退库数量
     */
    @ApiModelProperty(value="退库数量")
    private BigDecimal withdrawalNumber;
    /**
     * withdrawal_amount
     * 物资退库单价
     */
    @ApiModelProperty(value="物资退库单价")
    private BigDecimal withdrawalAmount;
    /**
     * withdrawal_untaxed_amount
     * 物资退库单价-未税
     */
    @ApiModelProperty(value="物资退库单价-未税")
    private BigDecimal withdrawalUntaxedAmount;
    /**
     * withdrawal_tax_amount
     * 物资退库税额
     */
    @ApiModelProperty(value="物资退库税额")
    private BigDecimal withdrawalTaxAmount;
    /**
     * accept_result_id
     * 领用结果id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="领用结果id")
    private Long acceptResultId;
    /**
     * explanation
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String explanation;


}
