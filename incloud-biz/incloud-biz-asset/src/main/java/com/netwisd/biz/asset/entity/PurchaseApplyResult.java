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
 * @Description $购置申请结果表 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-04-21 11:18:24
 */
@Data
@Table(value = "incloud_biz_asset_purchase_apply_result",comment = "购置申请结果表")
@TableName("incloud_biz_asset_purchase_apply_result")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "购置申请结果表 Entity")
public class PurchaseApplyResult extends IModel<PurchaseApplyResult> {

    /**
    * apply_id
    * 购置申请id;购置申请id
    */
    @ApiModelProperty(value="购置申请id;购置申请id")
    @TableField(value="apply_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "购置申请id;购置申请id")
    private Long applyId;

    /**
    * apply_code
    * 购置申请编号
    */
    @ApiModelProperty(value="购置申请编号")
    @TableField(value="apply_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "购置申请编号")
    private String applyCode;

    /**
    * apply_detail_id
    * 购置申请详情id
    */
    @ApiModelProperty(value="购置申请详情id")
    @TableField(value="apply_detail_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "购置申请详情id")
    private Long applyDetailId;

    /**
    * route
    * 全路径
    */
    @ApiModelProperty(value="全路径")
    @TableField(value="route")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "全路径")
    private String route;

    /**
    * route_name
    * 全路径名称
    */
    @ApiModelProperty(value="全路径名称")
    @TableField(value="route_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "全路径名称")
    private String routeName;

    /**
    * classify_code
    * 物资分类编码;物资分类编码
    */
    @ApiModelProperty(value="物资分类编码;物资分类编码")
    @TableField(value="classify_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资分类编码;物资分类编码")
    private String classifyCode;

    /**
    * classify_name
    * 物资分类名称;物资分类名称
    */
    @ApiModelProperty(value="物资分类名称;物资分类名称")
    @TableField(value="classify_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资分类名称;物资分类名称")
    private String classifyName;

    /**
    * classify_type
    * 物资类型;物资类型
    */
    @ApiModelProperty(value="物资类型;物资类型")
    @TableField(value="classify_type")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资类型;物资类型")
    private String classifyType;
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
    * 物资编码;物资编码
    */
    @ApiModelProperty(value="物资编码;物资编码")
    @TableField(value="item_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资编码;物资编码")
    private String itemCode;

    /**
    * item_name
    * 物资名称;物资名称
    */
    @ApiModelProperty(value="物资名称;物资名称")
    @TableField(value="item_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资名称;物资名称")
    private String itemName;

    /**
    * desclong
    * 物资长描述;物资长描述
    */
    @ApiModelProperty(value="物资长描述;物资长描述")
    @TableField(value="desclong")
    @Column(type = DataType.VARCHAR, length = 500,  isNull = true, comment = "物资长描述;物资长描述")
    private String desclong;

    /**
    * descshort
    * 物资短描述;物资短描述
    */
    @ApiModelProperty(value="物资短描述;物资短描述")
    @TableField(value="descshort")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资短描述;物资短描述")
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
    * specs
    * 物资规格
    */
    @ApiModelProperty(value="物资规格")
    @TableField(value="specs")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资规格")
    private String specs;

    /**
    * standard
    * 物资标准
    */
    @ApiModelProperty(value="物资标准")
    @TableField(value="standard")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资标准")
    private String standard;

    /**
    * tax_rate
    * 税率
    */
    @ApiModelProperty(value="税率")
    @TableField(value="tax_rate")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "税率")
    private String taxRate;

    /**
    * tax_amount
    * 税额
    */
    @ApiModelProperty(value="税额")
    @TableField(value="tax_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "税额")
    private BigDecimal taxAmount;

    /**
    * apply_number
    * 申请数量
    */
    @ApiModelProperty(value="申请数量")
    @TableField(value="apply_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "申请数量")
    private BigDecimal applyNumber;

    /**
    * apply_amount
    * 申请含税单价
    */
    @ApiModelProperty(value="申请含税单价")
    @TableField(value="apply_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "申请含税单价")
    private BigDecimal applyAmount;

    /**
    * apply_untaxed_amount
    * 申请不含税单价
    */
    @ApiModelProperty(value="申请不含税单价")
    @TableField(value="apply_untaxed_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "申请不含税单价")
    private BigDecimal applyUntaxedAmount;

    /**
    * apply_sum_amount
    * 申请含税总价
    */
    @ApiModelProperty(value="申请含税总价")
    @TableField(value="apply_sum_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "申请含税总价")
    private BigDecimal applySumAmount;

    /**
    * apply_sum_untaxed_amount
    * 申请不含税总价
    */
    @ApiModelProperty(value="申请不含税总价")
    @TableField(value="apply_sum_untaxed_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "申请不含税总价")
    private BigDecimal applySumUntaxedAmount;

    /**
    * purpose
    * 用途
    */
    @ApiModelProperty(value="用途")
    @TableField(value="purpose")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "用途")
    private String purpose;

    /**
    * explanation
    * 说明
    */
    @ApiModelProperty(value="说明")
    @TableField(value="explanation")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "说明")
    private String explanation;

    /**
    * material_quality
    * 物资材质
    */
    @ApiModelProperty(value="物资材质")
    @TableField(value="material_quality")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资材质")
    private String materialQuality;

}
