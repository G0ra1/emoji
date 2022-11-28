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
 * @Description 资产出库明细 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-09-08 14:33:15
 */
@Data
@ApiModel(value = "资产出库明细 Vo")
public class MaterialDeliveryDetailVo extends IVo{

    /**
     * delivery_id
     * 出库id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="出库id")
    private Long deliveryId;
    /**
     * assets_id
     * 资产Id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="资产Id")
    private Long assetsId;
    /**
     * assets_detail_id
     * 资产明细id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="资产明细id")
    private Long assetsDetailId;
    /**
     * classify_id
     * 分类id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="分类id")
    private Long classifyId;
    /**
     * classify_code
     * 分类编码
     */
    @ApiModelProperty(value="分类编码")
    private String classifyCode;
    /**
     * classify_name
     * 分类名称
     */
    @ApiModelProperty(value="分类名称")
    private String classifyName;
    /**
     * route
     * 物资分类全路径
     */
    @ApiModelProperty(value="物资分类全路径")
    private String route;
    /**
     * route_name
     * 物资分类全路径名称
     */
    @ApiModelProperty(value="物资分类全路径名称")
    private String routeName;
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
     * desclong
     * 物资长描述
     */
    @ApiModelProperty(value="物资长描述")
    private String desclong;
    /**
     * descshort
     * 物资短描述
     */
    @ApiModelProperty(value="物资短描述")
    private String descshort;
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
     * sku属性
     */
    @ApiModelProperty(value="sku属性")
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
     * delivery_number
     * 出库数量
     */
    @ApiModelProperty(value="出库数量")
    private BigDecimal deliveryNumber;
    /**
     * asset_self_code
     * 资产自编码
     */
    @ApiModelProperty(value="资产自编码")
    private String assetSelfCode;
    /**
     * assets_code
     * 资产编码
     */
    @ApiModelProperty(value="资产编码")
    private String assetsCode;
    /**
     * delivery_assets_code
     * 出库后资产编码
     */
    @ApiModelProperty(value="出库后资产编码")
    private String deliveryAssetsCode;
    /**
     * assets_number
     * 当前库存数量
     */
    @ApiModelProperty(value="当前库存数量")
    private BigDecimal assetsNumber;
    /**
     * tax_rate
     * 税率
     */
    @ApiModelProperty(value="税率")
    private BigDecimal taxRate;
    /**
     * assets_amount
     * 物资原值单价
     */
    @ApiModelProperty(value="物资原值单价")
    private BigDecimal assetsAmount;
    /**
     * assets_untaxed_amount
     * 物资原值单价-未税
     */
    @ApiModelProperty(value="物资原值单价-未税")
    private BigDecimal assetsUntaxedAmount;
    /**
     * assets_tax_amount
     * 物资原值税额
     */
    @ApiModelProperty(value="物资原值税额")
    private BigDecimal assetsTaxAmount;
    /**
     * assets_sum_amount
     * 物资原值总价
     */
    @ApiModelProperty(value="物资原值总价")
    private BigDecimal assetsSumAmount;
    /**
     * assets_sum_untaxed_amount
     * 物资原值总价-未税
     */
    @ApiModelProperty(value="物资原值总价-未税")
    private BigDecimal assetsSumUntaxedAmount;
    /**
     * assets_sum_tax_amount
     * 物资原值总税额
     */
    @ApiModelProperty(value="物资原值总税额")
    private BigDecimal assetsSumTaxAmount;
    /**
     * delivery_amount
     * 出库单价
     */
    @ApiModelProperty(value="出库单价")
    private BigDecimal deliveryAmount;
    /**
     * delivery_untaxed_amount
     * 出库单价-未税
     */
    @ApiModelProperty(value="出库单价-未税")
    private BigDecimal deliveryUntaxedAmount;
    /**
     * delivery_tax_amount
     * 出库税额
     */
    @ApiModelProperty(value="出库税额")
    private BigDecimal deliveryTaxAmount;
    /**
     * delivery_sum_amount
     * 出库总价
     */
    @ApiModelProperty(value="出库总价")
    private BigDecimal deliverySumAmount;
    /**
     * delivery_sum_untaxed_amount
     * 出库总价-未税
     */
    @ApiModelProperty(value="出库总价-未税")
    private BigDecimal deliverySumUntaxedAmount;
    /**
     * delivery_sum_tax_amount
     * 出库总税额
     */
    @ApiModelProperty(value="出库总税额")
    private BigDecimal deliverySumTaxAmount;
    /**
     * explanation
     * 说明
     */
    @ApiModelProperty(value="说明")
    private String explanation;


}
