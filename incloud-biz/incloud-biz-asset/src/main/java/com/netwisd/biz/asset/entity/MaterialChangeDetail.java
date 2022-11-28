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
 * @Description $资产变更详情 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-08-03 09:56:31
 */
@Data
@Table(value = "incloud_biz_asset_material_change_detail",comment = "资产变更详情")
@TableName("incloud_biz_asset_material_change_detail")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "资产变更详情 Entity")
public class MaterialChangeDetail extends IModel<MaterialChangeDetail> {

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
     * tax_rate
     * 税率
     */
    @ApiModelProperty(value="税率")
    @TableField(value="tax_rate")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "税率")
    private BigDecimal taxRate;
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
     * assets_number
     * 总数量/领用数量
     */
    @ApiModelProperty(value="总数量/领用数量")
    @TableField(value="assets_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "总数量/领用数量")
    private BigDecimal assetsNumber;
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
     * net_rate
     * 净值率
     */
    @ApiModelProperty(value="净值率")
    @TableField(value="net_rate")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "净值率")
    private BigDecimal netRate;
    /**
     * estlimate_net_salvage
     * 预计净残值
     */
    @ApiModelProperty(value="预计净残值")
    @TableField(value="estlimate_net_salvage")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "预计净残值")
    private BigDecimal estlimateNetSalvage;
    /**
     * secret_related
     * 是否涉密
     */
    @ApiModelProperty(value="是否涉密")
    @TableField(value="secret_related")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "是否涉密")
    private Integer secretRelated;
    /**
     * asset_dept_id
     * 资产所属部门
     */
    @ApiModelProperty(value="资产所属部门")
    @TableField(value="asset_dept_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "资产所属部门")
    private Long assetDeptId;
    /**
     * asset_dept_name
     * 资产所属部门名称
     */
    @ApiModelProperty(value="资产所属部门名称")
    @TableField(value="asset_dept_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "资产所属部门名称")
    private String assetDeptName;
    /**
     * asset_org_id
     * 资产所属机构id
     */
    @ApiModelProperty(value="资产所属机构id")
    @TableField(value="asset_org_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "资产所属机构id")
    private Long assetOrgId;
    /**
     * asset_org_name
     * 资产所属机构名称
     */
    @ApiModelProperty(value="资产所属机构名称")
    @TableField(value="asset_org_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "资产所属机构名称")
    private String assetOrgName;
    /**
     * asset_org_full_id
     * 组织全路径ID
     */
    @ApiModelProperty(value="组织全路径ID")
    @TableField(value="asset_org_full_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "组织全路径ID")
    private Long assetOrgFullId;
    /**
     * item_type
     * 物资类型
     */
    @ApiModelProperty(value="物资类型")
    @TableField(value="item_type")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资类型")
    private String itemType;
    /**
     * asset_source
     * 物资来源
     */
    @ApiModelProperty(value="物资来源")
    @TableField(value="asset_source")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "物资来源")
    private Integer assetSource;
    /**
     * series_number
     * 物资序列号;出厂时的唯一序号
     */
    @ApiModelProperty(value="物资序列号;出厂时的唯一序号")
    @TableField(value="series_number")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资序列号;出厂时的唯一序号")
    private String seriesNumber;
    /**
     * type
     * 类型;0-历史数据，1-表单数据
     */
    @ApiModelProperty(value="类型;0-历史数据，1-表单数据")
    @TableField(value="type")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "类型;0-历史数据，1-表单数据")
    private Integer type;
    /**
     * change_id
     * 资产变更id
     */
    @ApiModelProperty(value="资产变更id")
    @TableField(value="change_id")
    @Column(type = DataType.BIGINT, length = 19, fkTableName = "incloud_biz_asset_material_change" ,fkFieldName = "id" , isNull = true, comment = "资产变更id")
    private Long changeId;
    /**
     * assets_detail_id
     * 资产明细id
     */
    @ApiModelProperty(value="资产明细id")
    @TableField(value="assets_detail_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "资产明细id")
    private Long assetsDetailId;
    /**
     * item_id
     * 物资id
     */
    @ApiModelProperty(value="物资id")
    @TableField(value="item_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "物资id")
    private Long itemId;
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
     * apply_time
     * 申请时间
     */
    @ApiModelProperty(value="申请时间")
    @TableField(value="apply_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "申请时间")
    private LocalDateTime applyTime;
    /**
     * apply_user_id
     * 申请人ID
     */
    @ApiModelProperty(value="申请人ID")
    @TableField(value="apply_user_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "申请人ID")
    private Long applyUserId;
    /**
     * apply_user_name
     * 申请人名称
     */
    @ApiModelProperty(value="申请人名称")
    @TableField(value="apply_user_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "申请人名称")
    private String applyUserName;
    /**
     * apply_user_org_id
     * 申请人机构ID
     */
    @ApiModelProperty(value="申请人机构ID")
    @TableField(value="apply_user_org_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "申请人机构ID")
    private Long applyUserOrgId;
    /**
     * apply_user_org_name
     * 申请人机构名称
     */
    @ApiModelProperty(value="申请人机构名称")
    @TableField(value="apply_user_org_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "申请人机构名称")
    private String applyUserOrgName;
    /**
     * apply_user_dept_id
     * 申请人部门ID
     */
    @ApiModelProperty(value="申请人部门ID")
    @TableField(value="apply_user_dept_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "申请人部门ID")
    private Long applyUserDeptId;
    /**
     * apply_user_dept_name
     * 申请人部门名称
     */
    @ApiModelProperty(value="申请人部门名称")
    @TableField(value="apply_user_dept_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "申请人部门名称")
    private String applyUserDeptName;
    /**
     * change_number
     * 变更数量
     */
    @ApiModelProperty(value="变更数量")
    @TableField(value="change_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "变更数量")
    private BigDecimal changeNumber;
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
     * status
     * 物资状态
     */
    @ApiModelProperty(value="物资状态")
    @TableField(value="status")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资状态")
    private String status;
    /**
     * asset_user_id
     * 资产所属人
     */
    @ApiModelProperty(value="资产所属人")
    @TableField(value="asset_user_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "资产所属人")
    private Long assetUserId;
    /**
     * asset_user_name
     * 资产所属人名称
     */
    @ApiModelProperty(value="资产所属人名称")
    @TableField(value="asset_user_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "资产所属人名称")
    private String assetUserName;

}
