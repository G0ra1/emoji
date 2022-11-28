package com.netwisd.biz.asset.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.anntation.Table;
import com.netwisd.common.db.data.DataType;
import com.netwisd.common.db.data.IModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
    import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description $资产出库明细 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-09-08 14:33:15
 */
@Data
@Table(value = "incloud_biz_asset_material_delivery_detail",comment = "资产出库明细")
@TableName("incloud_biz_asset_material_delivery_detail")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "资产出库明细 Entity")
public class MaterialDeliveryDetail extends IModel<MaterialDeliveryDetail> {

    /**
     * delivery_id
     * 出库id
     */
    @ApiModelProperty(value="出库id")
    @TableField(value="delivery_id")
    @Column(type = DataType.BIGINT, length = 19, fkTableName = "incloud_biz_asset_material_delivery" ,fkFieldName = "id" , isNull = true, comment = "出库id")
    private Long deliveryId;
    /**
     * assets_id
     * 资产Id
     */
    @ApiModelProperty(value="资产Id")
    @TableField(value="assets_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "资产Id")
    private Long assetsId;
    /**
     * assets_detail_id
     * 资产明细id
     */
    @ApiModelProperty(value="资产明细id")
    @TableField(value="assets_detail_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "资产明细id")
    private Long assetsDetailId;
    /**
     * classify_id
     * 分类id
     */
    @ApiModelProperty(value="分类id")
    @TableField(value="classify_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "分类id")
    private Long classifyId;
    /**
     * classify_code
     * 分类编码
     */
    @ApiModelProperty(value="分类编码")
    @TableField(value="classify_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "分类编码")
    private String classifyCode;
    /**
     * classify_name
     * 分类名称
     */
    @ApiModelProperty(value="分类名称")
    @TableField(value="classify_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "分类名称")
    private String classifyName;
    /**
     * route
     * 物资分类全路径
     */
    @ApiModelProperty(value="物资分类全路径")
    @TableField(value="route")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资分类全路径")
    private String route;
    /**
     * route_name
     * 物资分类全路径名称
     */
    @ApiModelProperty(value="物资分类全路径名称")
    @TableField(value="route_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资分类全路径名称")
    private String routeName;
    /**
     * item_id
     * 物资id
     */
    @ApiModelProperty(value="物资id")
    @TableField(value="item_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "物资id")
    private Long itemId;
    /**
     * item_code
     * 物资编码
     */
    @ApiModelProperty(value="物资编码")
    @TableField(value="item_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资编码")
    private String itemCode;
    /**
     * item_name
     * 物资名称
     */
    @ApiModelProperty(value="物资名称")
    @TableField(value="item_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资名称")
    private String itemName;
    /**
     * desclong
     * 物资长描述
     */
    @ApiModelProperty(value="物资长描述")
    @TableField(value="desclong")
    @Column(type = DataType.VARCHAR, length = 500,  isNull = true, comment = "物资长描述")
    private String desclong;
    /**
     * descshort
     * 物资短描述
     */
    @ApiModelProperty(value="物资短描述")
    @TableField(value="descshort")
    @Column(type = DataType.VARCHAR, length = 500,  isNull = true, comment = "物资短描述")
    private String descshort;
    /**
     * unit_code
     * 物资单位编码
     */
    @ApiModelProperty(value="物资单位编码")
    @TableField(value="unit_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资单位编码")
    private String unitCode;
    /**
     * unit_name
     * 物资单位名称
     */
    @ApiModelProperty(value="物资单位名称")
    @TableField(value="unit_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资单位名称")
    private String unitName;
    /**
     * material_quality
     * 物资材质
     */
    @ApiModelProperty(value="物资材质")
    @TableField(value="material_quality")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资材质")
    private String materialQuality;
    /**
     * standard
     * 物资标准
     */
    @ApiModelProperty(value="物资标准")
    @TableField(value="standard")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资标准")
    private String standard;
    /**
     * specs
     * 物资规格
     */
    @ApiModelProperty(value="物资规格")
    @TableField(value="specs")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资规格")
    private String specs;
    /**
     * sku_code
     * sku属性
     */
    @ApiModelProperty(value="sku属性")
    @TableField(value="sku_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "sku属性")
    private String skuCode;
    /**
     * sku_full_id
     * sku全路径id
     */
    @ApiModelProperty(value="sku全路径id")
    @TableField(value="sku_full_id")
    @Column(type = DataType.VARCHAR, length = 2000,  isNull = true, comment = "sku全路径id")
    private String skuFullId;
    /**
     * sku_full_name
     * sku全路径名称
     */
    @ApiModelProperty(value="sku全路径名称")
    @TableField(value="sku_full_name")
    @Column(type = DataType.VARCHAR, length = 2000,  isNull = true, comment = "sku全路径名称")
    private String skuFullName;
    /**
     * delivery_number
     * 出库数量
     */
    @ApiModelProperty(value="出库数量")
    @TableField(value="delivery_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "出库数量")
    private BigDecimal deliveryNumber;
    /**
     * asset_self_code
     * 资产自编码
     */
    @ApiModelProperty(value="资产自编码")
    @TableField(value="asset_self_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "资产自编码")
    private String assetSelfCode;
    /**
     * assets_code
     * 资产编码
     */
    @ApiModelProperty(value="资产编码")
    @TableField(value="assets_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "资产编码")
    private String assetsCode;
    /**
     * delivery_assets_code
     * 出库后资产编码
     */
    @ApiModelProperty(value="出库后资产编码")
    @TableField(value="delivery_assets_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "出库后资产编码")
    private String deliveryAssetsCode;
    /**
     * assets_number
     * 当前库存数量
     */
    @ApiModelProperty(value="当前库存数量")
    @TableField(value="assets_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "当前库存数量")
    private BigDecimal assetsNumber;
    /**
     * tax_rate
     * 税率
     */
    @ApiModelProperty(value="税率")
    @TableField(value="tax_rate")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "税率")
    private BigDecimal taxRate;
    /**
     * assets_amount
     * 物资原值单价
     */
    @ApiModelProperty(value="物资原值单价")
    @TableField(value="assets_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "物资原值单价")
    private BigDecimal assetsAmount;
    /**
     * assets_untaxed_amount
     * 物资原值单价-未税
     */
    @ApiModelProperty(value="物资原值单价-未税")
    @TableField(value="assets_untaxed_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "物资原值单价-未税")
    private BigDecimal assetsUntaxedAmount;
    /**
     * assets_tax_amount
     * 物资原值税额
     */
    @ApiModelProperty(value="物资原值税额")
    @TableField(value="assets_tax_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "物资原值税额")
    private BigDecimal assetsTaxAmount;
    /**
     * assets_sum_amount
     * 物资原值总价
     */
    @ApiModelProperty(value="物资原值总价")
    @TableField(value="assets_sum_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "物资原值总价")
    private BigDecimal assetsSumAmount;
    /**
     * assets_sum_untaxed_amount
     * 物资原值总价-未税
     */
    @ApiModelProperty(value="物资原值总价-未税")
    @TableField(value="assets_sum_untaxed_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "物资原值总价-未税")
    private BigDecimal assetsSumUntaxedAmount;
    /**
     * assets_sum_tax_amount
     * 物资原值总税额
     */
    @ApiModelProperty(value="物资原值总税额")
    @TableField(value="assets_sum_tax_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "物资原值总税额")
    private BigDecimal assetsSumTaxAmount;
    /**
     * delivery_amount
     * 出库单价
     */
    @ApiModelProperty(value="出库单价")
    @TableField(value="delivery_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "出库单价")
    private BigDecimal deliveryAmount;
    /**
     * delivery_untaxed_amount
     * 出库单价-未税
     */
    @ApiModelProperty(value="出库单价-未税")
    @TableField(value="delivery_untaxed_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "出库单价-未税")
    private BigDecimal deliveryUntaxedAmount;
    /**
     * delivery_tax_amount
     * 出库税额
     */
    @ApiModelProperty(value="出库税额")
    @TableField(value="delivery_tax_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "出库税额")
    private BigDecimal deliveryTaxAmount;
    /**
     * delivery_sum_amount
     * 出库总价
     */
    @ApiModelProperty(value="出库总价")
    @TableField(value="delivery_sum_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "出库总价")
    private BigDecimal deliverySumAmount;
    /**
     * delivery_sum_untaxed_amount
     * 出库总价-未税
     */
    @ApiModelProperty(value="出库总价-未税")
    @TableField(value="delivery_sum_untaxed_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "出库总价-未税")
    private BigDecimal deliverySumUntaxedAmount;
    /**
     * delivery_sum_tax_amount
     * 出库总税额
     */
    @ApiModelProperty(value="出库总税额")
    @TableField(value="delivery_sum_tax_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "出库总税额")
    private BigDecimal deliverySumTaxAmount;
    /**
     * explanation
     * 说明
     */
    @ApiModelProperty(value="说明")
    @TableField(value="explanation")
    @Column(type = DataType.VARCHAR, length = 500,  isNull = true, comment = "说明")
    private String explanation;

}
