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
 * @Description $处置计划明细 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-07 17:50:50
 */
@Data
@Table(value = "incloud_biz_asset_dispose_plan_detail",comment = "处置计划明细")
@TableName("incloud_biz_asset_dispose_plan_detail")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "处置计划明细 Entity")
public class DisposePlanDetail extends IModel<DisposePlanDetail> {

    /**
     * dispose_plan_id
     * 处置计划id
     */
    @ApiModelProperty(value="处置计划id")
    @TableField(value="dispose_plan_id")
    @Column(type = DataType.BIGINT, length = 19, fkTableName = "incloud_biz_asset_dispose_plan" ,fkFieldName = "id" , isNull = true, comment = "处置计划id")
    private Long disposePlanId;
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
     * category_id
     * 分类类别Id
     */
    @ApiModelProperty(value="分类类别Id")
    @TableField(value="category_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "分类类别Id")
    private Long categoryId;
    /**
     * category_code
     * 分类类别编码
     */
    @ApiModelProperty(value="分类类别编码")
    @TableField(value="category_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "分类类别编码")
    private String categoryCode;
    /**
     * category_name
     * 分类类别名称
     */
    @ApiModelProperty(value="分类类别名称")
    @TableField(value="category_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "分类类别名称")
    private String categoryName;
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
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资短描述")
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
     * assets_classification
     * 资产分类
     */
    @ApiModelProperty(value="资产分类")
    @TableField(value="assets_classification")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "资产分类")
    private String assetsClassification;
    /**
     * assets_code
     * 资产编号
     */
    @ApiModelProperty(value="资产编号")
    @TableField(value="assets_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "资产编号")
    private String assetsCode;
    /**
     * tax_rate
     * 税率
     */
    @ApiModelProperty(value="税率")
    @TableField(value="tax_rate")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "税率")
    private BigDecimal taxRate;
    /**
     * stock_number
     * 库存数量
     */
    @ApiModelProperty(value="库存数量")
    @TableField(value="stock_number")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "库存数量")
    private BigDecimal stockNumber;
    /**
     * dispose_plan_number
     * 计划数量
     */
    @ApiModelProperty(value="计划数量")
    @TableField(value="dispose_plan_number")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "计划数量")
    private BigDecimal disposePlanNumber;
    /**
     * assets_amount
     * 资产原值单价
     */
    @ApiModelProperty(value="资产原值单价")
    @TableField(value="assets_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "资产原值单价")
    private BigDecimal assetsAmount;
    /**
     * assets_untaxed_amount
     * 资产原值单价-未税
     */
    @ApiModelProperty(value="资产原值单价-未税")
    @TableField(value="assets_untaxed_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "资产原值单价-未税")
    private BigDecimal assetsUntaxedAmount;
    /**
     * assets_tax_amount
     * 资产原值税额
     */
    @ApiModelProperty(value="资产原值税额")
    @TableField(value="assets_tax_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "资产原值税额")
    private BigDecimal assetsTaxAmount;
    /**
     * assets_sum_amount
     * 资产原值总价
     */
    @ApiModelProperty(value="资产原值总价")
    @TableField(value="assets_sum_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "资产原值总价")
    private BigDecimal assetsSumAmount;
    /**
     * assets_sum_untaxed_amount
     * 资产原值总价-未税
     */
    @ApiModelProperty(value="资产原值总价-未税")
    @TableField(value="assets_sum_untaxed_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "资产原值总价-未税")
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
     * use_data
     * 开始使用日期
     */
    @ApiModelProperty(value="开始使用日期")
    @TableField(value="use_data")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "开始使用日期")
    private LocalDateTime useData;
    /**
     * withdrawal_month
     * 已计提月份
     */
    @ApiModelProperty(value="已计提月份")
    @TableField(value="withdrawal_month")
    @Column(type = DataType.VARCHAR, length = 30,  isNull = true, comment = "已计提月份")
    private String withdrawalMonth;
    /**
     * depreciation
     * 累计折旧
     */
    @ApiModelProperty(value="累计折旧")
    @TableField(value="depreciation")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "累计折旧")
    private BigDecimal depreciation;
    /**
     * valid_period
     * 规定使用年限
     */
    @ApiModelProperty(value="规定使用年限")
    @TableField(value="valid_period")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "规定使用年限")
    private String validPeriod;
    /**
     * jing_zhi
     * 净值
     */
    @ApiModelProperty(value="净值")
    @TableField(value="jing_zhi")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "净值")
    private BigDecimal jingZhi;
    /**
     * jing_can_zhi
     * 净残值
     */
    @ApiModelProperty(value="净残值")
    @TableField(value="jing_can_zhi")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "净残值")
    private BigDecimal jingCanZhi;
    /**
     * jinge
     * 净额
     */
    @ApiModelProperty(value="净额")
    @TableField(value="jinge")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "净额")
    private BigDecimal jinge;
    /**
     * dispose_reason
     * 处置原因
     */
    @ApiModelProperty(value="处置原因")
    @TableField(value="dispose_reason")
    @Column(type = DataType.VARCHAR, length = 500,  isNull = true, comment = "处置原因")
    private String disposeReason;
    /**
     * dispose_plan_date
     * 预计处置时间
     */
    @ApiModelProperty(value="预计处置时间")
    @TableField(value="dispose_plan_date")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "预计处置时间")
    private LocalDateTime disposePlanDate;

}
