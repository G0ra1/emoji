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
 * @Description $资产退库详情 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-08-01 10:45:42
 */
@Data
@Table(value = "incloud_biz_asset_material_withdrawal_detail",comment = "资产退库详情")
@TableName("incloud_biz_asset_material_withdrawal_detail")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "资产退库详情 Entity")
public class MaterialWithdrawalDetail extends IModel<MaterialWithdrawalDetail> {

    /**
     * withdrawal_id
     * 退库id
     */
    @ApiModelProperty(value="退库id")
    @TableField(value="withdrawal_id")
    @Column(type = DataType.BIGINT, length = 19, fkTableName = "incloud_biz_asset_material_withdrawal" ,fkFieldName = "id" , isNull = true, comment = "退库id")
    private Long withdrawalId;
    /**
     * assets_id
     * 资产id
     */
    @ApiModelProperty(value="资产id")
    @TableField(value="assets_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "资产id")
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
     * assets_delivery_id
     * 资产出库id
     */
    @ApiModelProperty(value="资产出库id")
    @TableField(value="assets_delivery_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "资产出库id")
    private Long assetsDeliveryId;
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
     * item_type
     * 物资类型
     */
    @ApiModelProperty(value="物资类型")
    @TableField(value="item_type")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资类型")
    private String itemType;
    /**
     * item_type_name
     * 物资类型名称
     */
    @ApiModelProperty(value="物资类型名称")
    @TableField(value="item_type_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资类型名称")
    private String itemTypeName;

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
     * sku编码
     */
    @ApiModelProperty(value="sku编码")
    @TableField(value="sku_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "sku编码")
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
     * assets_code
     * 资产编号
     */
    @ApiModelProperty(value="资产编号")
    @TableField(value="assets_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "资产编号")
    private String assetsCode;
    /**
     * assets_label
     * 资产标签
     */
    @ApiModelProperty(value="资产标签")
    @TableField(value="assets_label")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "资产标签")
    private String assetsLabel;
    /**
     * warehouse_id
     * 仓库地点
     */
    @ApiModelProperty(value="仓库地点")
    @TableField(value="warehouse_id")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "仓库地点")
    private String warehouseId;
    /**
     * warehouse_name
     * 仓库地点
     */
    @ApiModelProperty(value="仓库地点")
    @TableField(value="warehouse_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "仓库地点")
    private String warehouseName;
    /**
     * shelf_id
     * 货架号
     */
    @ApiModelProperty(value="货架号")
    @TableField(value="shelf_id")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "货架号")
    private String shelfId;
    /**
     * shelf_name
     * 货架号
     */
    @ApiModelProperty(value="货架号")
    @TableField(value="shelf_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "货架号")
    private String shelfName;
    /**
     * tax_rate
     * 税率
     */
    @ApiModelProperty(value="税率")
    @TableField(value="tax_rate")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "税率")
    private BigDecimal taxRate;
    /**
     * accept_number
     * 领用数量
     */
    @ApiModelProperty(value="领用数量")
    @TableField(value="accept_number")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "领用数量")
    private BigDecimal acceptNumber;
    /**
     * withdrawal_number
     * 退库数量
     */
    @ApiModelProperty(value="退库数量")
    @TableField(value="withdrawal_number")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "退库数量")
    private BigDecimal withdrawalNumber;
    /**
     * withdrawal_amount
     * 物资退库单价
     */
    @ApiModelProperty(value="物资退库单价")
    @TableField(value="withdrawal_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "物资退库单价")
    private BigDecimal withdrawalAmount;
    /**
     * withdrawal_untaxed_amount
     * 物资退库单价-未税
     */
    @ApiModelProperty(value="物资退库单价-未税")
    @TableField(value="withdrawal_untaxed_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "物资退库单价-未税")
    private BigDecimal withdrawalUntaxedAmount;
    /**
     * withdrawal_tax_amount
     * 物资退库税额
     */
    @ApiModelProperty(value="物资退库税额")
    @TableField(value="withdrawal_tax_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "物资退库税额")
    private BigDecimal withdrawalTaxAmount;

    /**
     * accept_result_id
     * 领用结果id
     */
    @ApiModelProperty(value="领用结果id")
    @TableField(value="accept_result_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "领用结果id")
    private Long acceptResultId;

    /**
     * explanation
     * 备注
     */
    @ApiModelProperty(value="备注")
    @TableField(value="explanation")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "备注")
    private String explanation;
}
