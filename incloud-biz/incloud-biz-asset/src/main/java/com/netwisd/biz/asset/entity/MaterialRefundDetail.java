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
 * @Description $资产退还详情 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:46:00
 */
@Data
@Table(value = "incloud_biz_asset_material_refund_detail",comment = "资产退还详情")
@TableName("incloud_biz_asset_material_refund_detail")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "资产退还详情 Entity")
public class MaterialRefundDetail extends IModel<MaterialRefundDetail> {

    /**
     * refund_id
     * 退库id
     */
    @ApiModelProperty(value="退库id")
    @TableField(value="refund_id")
    @Column(type = DataType.BIGINT, length = 19, fkTableName = "incloud_biz_asset_maint_refund" ,fkFieldName = "id" , isNull = true, comment = "退库id")
    private Long refundId;
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
     * assets_accept_id
     * 资产出库id
     */
    @ApiModelProperty(value="资产出库id")
    @TableField(value="assets_accept_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "资产出库id")
    private Long assetsAcceptId;
    /**
     * item_id
     * 物资Id
     */
    @ApiModelProperty(value="物资Id")
    @TableField(value="item_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "物资Id")
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
     * 出库数量
     */
    @ApiModelProperty(value="出库数量")
    @TableField(value="accept_number")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "出库数量")
    private BigDecimal acceptNumber;
    /**
     * refund_number
     * 退库数量
     */
    @ApiModelProperty(value="退库数量")
    @TableField(value="refund_number")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "退库数量")
    private BigDecimal refundNumber;
    /**
     * refund_amount
     * 物资退库单价
     */
    @ApiModelProperty(value="物资退库单价")
    @TableField(value="refund_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "物资退库单价")
    private BigDecimal refundAmount;
    /**
     * refund_untaxed_amount
     * 物资退库单价-未税
     */
    @ApiModelProperty(value="物资退库单价-未税")
    @TableField(value="refund_untaxed_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "物资退库单价-未税")
    private BigDecimal refundUntaxedAmount;
    /**
     * refund_tax_amount
     * 物资退库税额
     */
    @ApiModelProperty(value="物资退库税额")
    @TableField(value="refund_tax_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "物资退库税额")
    private BigDecimal refundTaxAmount;

}
