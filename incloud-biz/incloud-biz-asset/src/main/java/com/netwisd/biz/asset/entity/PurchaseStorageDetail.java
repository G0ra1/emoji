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
 * @Description $物资验收入库明细 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-06-14 10:39:31
 */
@Data
@Table(value = "incloud_biz_asset_purchase_storage_detail",comment = "物资验收入库明细")
@TableName("incloud_biz_asset_purchase_storage_detail")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "物资验收入库明细 Entity")
public class PurchaseStorageDetail extends IModel<PurchaseStorageDetail> {

    /**
     * storage_id
     * 资产入库id
     */
    @ApiModelProperty(value="资产入库id")
    @TableField(value="storage_id")
    @Column(type = DataType.BIGINT, length = 19, fkTableName = "incloud_biz_asset_purchase_storage" ,fkFieldName = "id" , isNull = true, comment = "资产入库id")
    private Long storageId;
    /**
     * assets_id
     * 资产台账id
     */
    @ApiModelProperty(value="资产台账id")
    @TableField(value="assets_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "资产台账id")
    private Long assetsId;
    /**
     * assets_sku_id
     * 资产sku台账id
     */
    @ApiModelProperty(value="资产sku台账id")
    @TableField(value="assets_sku_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "资产sku台账id")
    private Long assetsSkuId;
    /**
     * assets_detail_id
     * 资产明细Id
     */
    @ApiModelProperty(value="资产明细Id")
    @TableField(value="assets_detail_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "资产明细Id")
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
     * item_type
     * 物资类型
     */
    @ApiModelProperty(value="物资类型")
    @TableField(value="item_type")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资类型")
    private String itemType;
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
     * storage_tax_rate
     * 税率
     */
    @ApiModelProperty(value="税率")
    @TableField(value="storage_tax_rate")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "税率")
    private BigDecimal storageTaxRate;
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
     * storage_time
     * 入库时间
     */
    @ApiModelProperty(value="入库时间")
    @TableField(value="storage_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "入库时间")
    private LocalDateTime storageTime;
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
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "仓库地点")
    private Long warehouseId;
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
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "货架号")
    private Long shelfId;
    /**
     * shelf_name
     * 货架号
     */
    @ApiModelProperty(value="货架号")
    @TableField(value="shelf_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "货架号")
    private String shelfName;
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
     * rz_date
     * 入账日期
     */
    @ApiModelProperty(value="入账日期")
    @TableField(value="rz_date")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "入账日期")
    private LocalDateTime rzDate;
    /**
     * real_time
     * 实际到货时间
     */
    @ApiModelProperty(value="实际到货时间")
    @TableField(value="real_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "实际到货时间")
    private LocalDateTime realTime;
    /**
     * accpetance_time
     * 验收时间
     */
    @ApiModelProperty(value="验收时间")
    @TableField(value="accpetance_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "验收时间")
    private LocalDateTime accpetanceTime;
    /**
     * accpetance_code
     * 验收编号
     */
    @ApiModelProperty(value="验收编号")
    @TableField(value="accpetance_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "验收编号")
    private String accpetanceCode;
    /**
     * assets_number
     * 物资数量
     */
    @ApiModelProperty(value="物资数量")
    @TableField(value="assets_number")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "物资数量")
    private BigDecimal assetsNumber;
    /**
     * purchase_type
     * 物资购置类型
     */
    @ApiModelProperty(value="物资购置类型")
    @TableField(value="purchase_type")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "物资购置类型")
    private Integer purchaseType;
    /**
     * factory_sn
     * 设备出厂SN号
     */
    @ApiModelProperty(value="设备出厂SN号")
    @TableField(value="factory_sn")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "设备出厂SN号")
    private String factorySn;
    /**
     * factory_data
     * 设备出厂资料
     */
    @ApiModelProperty(value="设备出厂资料")
    @TableField(value="factory_data")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "设备出厂资料")
    private String factoryData;
    /**
     * accept_photo
     * 设备验收照片
     */
    @ApiModelProperty(value="设备验收照片")
    @TableField(value="accept_photo")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "设备验收照片")
    private String acceptPhoto;
    /**
     * source_id
     * 入库物资来源id
     */
    @ApiModelProperty(value="入库物资来源id")
    @TableField(value="source_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "入库物资来源id")
    private Long sourceId;
    /**
     * source_detail_id
     * 入库物资来源详情id
     */
    @ApiModelProperty(value="入库物资来源详情id")
    @TableField(value="source_detail_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "入库物资来源详情id")
    private Long sourceDetailId;
    /**
     * source_assets_id
     * 入库物资来源资产详情id
     */
    @ApiModelProperty(value="入库物资来源资产详情id")
    @TableField(value="source_assets_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "入库物资来源资产详情id")
    private Long sourceAssetsId;
    /**
     * storage_sourch
     * 入库来源
     */
    @ApiModelProperty(value="入库来源")
    @TableField(value="storage_sourch")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "入库来源")
    private String storageSourch;
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
     * supplier_id
     * 供应商id
     */
    @ApiModelProperty(value="供应商id")
    @TableField(value="supplier_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "供应商id")
    private Long supplierId;
    /**
     * contract_id
     * 合同id
     */
    @ApiModelProperty(value="合同id")
    @TableField(value="contract_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "合同id")
    private Long contractId;
    /**
     * create_user_id
     * 创建人ID
     */
    @ApiModelProperty(value="创建人ID")
    @TableField(value="create_user_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "创建人ID")
    private Long createUserId;
    /**
     * create_user_name
     * 创建人名称
     */
    @ApiModelProperty(value="创建人名称")
    @TableField(value="create_user_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "创建人名称")
    private String createUserName;
    /**
     * create_user_parent_org_id
     * 创建人父级机构ID
     */
    @ApiModelProperty(value="创建人父级机构ID")
    @TableField(value="create_user_parent_org_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "创建人父级机构ID")
    private Long createUserParentOrgId;
    /**
     * create_user_parent_org_name
     * 创建人父级机构名称
     */
    @ApiModelProperty(value="创建人父级机构名称")
    @TableField(value="create_user_parent_org_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "创建人父级机构名称")
    private String createUserParentOrgName;
    /**
     * create_user_parent_dept_id
     * 创建人父级部门ID
     */
    @ApiModelProperty(value="创建人父级部门ID")
    @TableField(value="create_user_parent_dept_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "创建人父级部门ID")
    private Long createUserParentDeptId;
    /**
     * create_user_parent_dept_name
     * 创建人父级部门名称
     */
    @ApiModelProperty(value="创建人父级部门名称")
    @TableField(value="create_user_parent_dept_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "创建人父级部门名称")
    private String createUserParentDeptName;
    /**
     * create_user_org_full_id
     * 创建人父级组织全路径ID
     */
    @ApiModelProperty(value="创建人父级组织全路径ID")
    @TableField(value="create_user_org_full_id")
    @Column(type = DataType.VARCHAR, length = 2000,  isNull = true, comment = "创建人父级组织全路径ID")
    private String createUserOrgFullId;
    /**
     * item_id
     * 物资Id
     */
    @ApiModelProperty(value="物资Id")
    @TableField(value="item_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "物资Id")
    private Long itemId;
    /**
     * storage_number
     * 入库数量
     */
    @ApiModelProperty(value="入库数量")
    @TableField(value="storage_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "入库数量")
    private BigDecimal storageNumber;
    /**
     * storage_amount
     * 物资原值单价
     */
    @ApiModelProperty(value="物资原值单价")
    @TableField(value="storage_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "物资原值单价")
    private BigDecimal storageAmount;
    /**
     * storage_untaxed_amount
     * 物资原值单价-未税
     */
    @ApiModelProperty(value="物资原值单价-未税")
    @TableField(value="storage_untaxed_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "物资原值单价-未税")
    private BigDecimal storageUntaxedAmount;
    /**
     * storage_tax_amount
     * 入库原值税额
     */
    @ApiModelProperty(value="入库原值税额")
    @TableField(value="storage_tax_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "入库原值税额")
    private BigDecimal storageTaxAmount;
    /**
     * storage_sum_amount
     * 入库原值总价
     */
    @ApiModelProperty(value="入库原值总价")
    @TableField(value="storage_sum_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "入库原值总价")
    private BigDecimal storageSumAmount;
    /**
     * storage_sum_untaxed_amount
     * 入库原值总价-未税
     */
    @ApiModelProperty(value="入库原值总价-未税")
    @TableField(value="storage_sum_untaxed_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "入库原值总价-未税")
    private BigDecimal storageSumUntaxedAmount;
    /**
     * storage_sum_tax_amount
     * 入库原值总税额
     */
    @ApiModelProperty(value="入库原值总税额")
    @TableField(value="storage_sum_tax_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "入库原值总税额")
    private BigDecimal storageSumTaxAmount;

    /**
     * asset_source
     * 物项来源
     */
    @ApiModelProperty(value="物项来源")
    @TableField(value="asset_source" )
    @Column(type = DataType.INT,length = 10, isNull = true,comment = "物项来源")
    private Integer assetSource;
    /**
     * asset_self_code
     * 资产自编码
     */
    @ApiModelProperty(value="资产自编码")
    @TableField(value="asset_self_code" )
    @Column(type = DataType.VARCHAR,length = 255, isNull = true,comment = "资产自编码")
    private String assetSelfCode;

    /**
     * bar_code_file
     * 条件码文件id
     */
    @ApiModelProperty(value="条件码文件id")
    @TableField(value="bar_code_file" )
    @Column(type = DataType.VARCHAR,length = 255, isNull = true,comment = "条件码文件id")
    private String barCodeFile;
    /**
     * bar_code
     * 条件码
     */
    @ApiModelProperty(value="条件码")
    @TableField(value="bar_code" )
    @Column(type = DataType.VARCHAR,length = 255, isNull = true,comment = "条件码")
    private String barCode;
    /**
     * classify_type_code
     * 资产分类编码
     */
    @ApiModelProperty(value="资产分类编码")
    @TableField(value="classify_type_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "资产分类编码")
    private String classifyTypeCode;
    /**
     * classify_type_name
     * 资产分类名称
     */
    @ApiModelProperty(value="资产分类名称")
    @TableField(value="classify_type_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "资产分类名称")
    private String classifyTypeName;
}
