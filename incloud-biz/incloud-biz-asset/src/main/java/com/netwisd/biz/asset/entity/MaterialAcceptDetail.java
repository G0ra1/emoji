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
 * @Description $资产领用明细详情 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:45:59
 */
@Data
@Table(value = "incloud_biz_asset_material_accept_detail",comment = "资产领用明细详情")
@TableName("incloud_biz_asset_material_accept_detail")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "资产领用明细详情 Entity")
public class MaterialAcceptDetail extends IModel<MaterialAcceptDetail> {

    /**
     * accept_id
     * 领用id
     */
    @ApiModelProperty(value="领用id")
    @TableField(value="accept_id")
    @Column(type = DataType.BIGINT, length = 19, fkTableName = "incloud_biz_asset_maint_accept" ,fkFieldName = "id" , isNull = true, comment = "领用id")
    private Long acceptId;
    /**
     * accept_assets_id
     * 资产领用id
     */
    @ApiModelProperty(value="资产领用id")
    @TableField(value="accept_assets_id")
    @Column(type = DataType.BIGINT, length = 19, fkTableName = "incloud_biz_asset_maint_accept_assets" ,fkFieldName = "id" , isNull = true, comment = "资产领用id")
    private Long acceptAssetsId;
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
     * supplier_name
     * 供应商
     */
    @ApiModelProperty(value="供应商")
    @TableField(value="supplier_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "供应商")
    private String supplierName;
    /**
     * contract_code
     * 合同号
     */
    @ApiModelProperty(value="合同号")
    @TableField(value="contract_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "合同号")
    private String contractCode;
    /**
     * assets_classification
     * 资产分类
     */
    @ApiModelProperty(value="资产分类")
    @TableField(value="assets_classification")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "资产分类")
    private String assetsClassification;
    /**
     * bill_codes
     * 发票号
     */
    @ApiModelProperty(value="发票号")
    @TableField(value="bill_codes")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "发票号")
    private String billCodes;
    /**
     * bill_code_files_ids
     * 发票附件
     */
    @ApiModelProperty(value="发票附件")
    @TableField(value="bill_code_files_ids")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "发票附件")
    private String billCodeFilesIds;
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
     * quality_assurance_level
     * 质保等级
     */
    @ApiModelProperty(value="质保等级")
    @TableField(value="quality_assurance_level")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "质保等级")
    private String qualityAssuranceLevel;
    /**
     * manufacturer
     * 生产商
     */
    @ApiModelProperty(value="生产商")
    @TableField(value="manufacturer")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "生产商")
    private String manufacturer;
    /**
     * production_date
     * 生产日期
     */
    @ApiModelProperty(value="生产日期")
    @TableField(value="production_date")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "生产日期")
    private LocalDateTime productionDate;
    /**
     * service_life
     * 使用年限
     */
    @ApiModelProperty(value="使用年限")
    @TableField(value="service_life")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "使用年限")
    private BigDecimal serviceLife;
    /**
     * valid_period
     * 有效期
     */
    @ApiModelProperty(value="有效期")
    @TableField(value="valid_period")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "有效期")
    private String validPeriod;
    /**
     * batch_number
     * 批次/炉号
     */
    @ApiModelProperty(value="批次/炉号")
    @TableField(value="batch_number")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "批次/炉号")
    private String batchNumber;
    /**
     * acceptance_date
     * 验收日期
     */
    @ApiModelProperty(value="验收日期")
    @TableField(value="acceptance_date")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "验收日期")
    private LocalDateTime acceptanceDate;
    /**
     * factory_date
     * 出厂日期
     */
    @ApiModelProperty(value="出厂日期")
    @TableField(value="factory_date")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "出厂日期")
    private LocalDateTime factoryDate;
    /**
     * factory_code
     * 出厂编号
     */
    @ApiModelProperty(value="出厂编号")
    @TableField(value="factory_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "出厂编号")
    private String factoryCode;
    /**
     * approach_date
     * 进场日期
     */
    @ApiModelProperty(value="进场日期")
    @TableField(value="approach_date")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "进场日期")
    private LocalDateTime approachDate;
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
     * rz_date
     * 入账日期
     */
    @ApiModelProperty(value="入账日期")
    @TableField(value="rz_date")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "入账日期")
    private LocalDateTime rzDate;
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
     * 物资数量
     */
    @ApiModelProperty(value="物资数量")
    @TableField(value="stock_number")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "物资数量")
    private BigDecimal stockNumber;
    /**
     * accept_number
     * 出库数量
     */
    @ApiModelProperty(value="出库数量")
    @TableField(value="accept_number")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "出库数量")
    private BigDecimal acceptNumber;
    /**
     * accept_amount
     * 物资领用单价
     */
    @ApiModelProperty(value="物资领用单价")
    @TableField(value="accept_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "物资领用单价")
    private BigDecimal acceptAmount;
    /**
     * accept_untaxed_amount
     * 物资领用单价-未税
     */
    @ApiModelProperty(value="物资领用单价-未税")
    @TableField(value="accept_untaxed_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "物资领用单价-未税")
    private BigDecimal acceptUntaxedAmount;
    /**
     * accept_tax_amount
     * 物资领用税额
     */
    @ApiModelProperty(value="物资领用税额")
    @TableField(value="accept_tax_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "物资领用税额")
    private BigDecimal acceptTaxAmount;
    /**
     * accept_sum_amount
     * 物资领用总价
     */
    @ApiModelProperty(value="物资领用总价")
    @TableField(value="accept_sum_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "物资领用总价")
    private BigDecimal acceptSumAmount;
    /**
     * accept_sum_untaxed_amount
     * 物资领用总价-未税
     */
    @ApiModelProperty(value="物资领用总价-未税")
    @TableField(value="accept_sum_untaxed_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "物资领用总价-未税")
    private BigDecimal acceptSumUntaxedAmount;
    /**
     * accept_sum_tax_amount
     * 物资领用总税额
     */
    @ApiModelProperty(value="物资领用总税额")
    @TableField(value="accept_sum_tax_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "物资领用总税额")
    private BigDecimal acceptSumTaxAmount;
    /**
     * item_type
     * 物资类型
     */
    @ApiModelProperty(value="物资类型")
    @TableField(value="item_type")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资类型")
    private String itemType;
    /**
     * distribute_number
     * 派发数量
     */
    @ApiModelProperty(value="派发数量")
    @TableField(value="distribute_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "派发数量")
    private BigDecimal distributeNumber;
    /**
     * not_distribute_number
     * 未派发数量
     */
    @ApiModelProperty(value="未派发数量")
    @TableField(value="not_distribute_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "未派发数量")
    private BigDecimal notDistributeNumber;
    /**
     * sign_number
     * 签收数量
     */
    @ApiModelProperty(value="签收数量")
    @TableField(value="sign_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "签收数量")
    private BigDecimal signNumber;
    /**
     * not_sign_number
     * 未签收数量
     */
    @ApiModelProperty(value="未签收数量")
    @TableField(value="not_sign_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "未签收数量")
    private BigDecimal notSignNumber;

}
