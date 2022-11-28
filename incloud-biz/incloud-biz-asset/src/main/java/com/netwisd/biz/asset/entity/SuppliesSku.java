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

/**
 * @Description $耗材库存sku信息 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-06-01 18:21:00
 */
@Data
@Table(value = "incloud_biz_asset_supplies_sku",comment = "耗材库存sku信息")
@TableName("incloud_biz_asset_supplies_sku")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "耗材库存sku信息 Entity")
public class SuppliesSku extends IModel<SuppliesSku> {

    /**
     * assets_id
     * 资产台账id
     */
    @ApiModelProperty(value="资产台账id")
    @TableField(value="assets_id")
    @Column(type = DataType.BIGINT, length = 19, fkTableName = "incloud_biz_asset_supplies" ,fkFieldName = "id" , isNull = true, comment = "资产台账id")
    private Long assetsId;
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
     * asset_org_full_id
     * 组织全路径ID
     */
    @ApiModelProperty(value="组织全路径ID")
    @TableField(value="asset_org_full_id")
    @Column(type = DataType.VARCHAR, length = 2000,  isNull = true, comment = "组织全路径ID")
    private String assetOrgFullId;
    /**
     * reason
     * 事由
     */
    @ApiModelProperty(value="事由")
    @TableField(value="reason")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "事由")
    private String reason;
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
     * assets_number
     * 总数量
     */
    @ApiModelProperty(value="总数量")
    @TableField(value="assets_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "总数量")
    private BigDecimal assetsNumber;
    /**
     * acceptance_number
     * 验收数量
     */
    @ApiModelProperty(value="验收数量")
    @TableField(value="acceptance_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "验收数量")
    private BigDecimal acceptanceNumber;
    /**
     * storage_number
     * 入库数量
     */
    @ApiModelProperty(value="入库数量")
    @TableField(value="storage_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "入库数量")
    private BigDecimal storageNumber;
    /**
     * stock_number
     * 库存数量
     */
    @ApiModelProperty(value="库存数量")
    @TableField(value="stock_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "库存数量")
    private BigDecimal stockNumber;
    /**
     * usable_number
     * 可用数量
     */
    @ApiModelProperty(value="可用数量")
    @TableField(value="usable_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "可用数量")
    private BigDecimal usableNumber;
    /**
     * entry_number
     * 入账数量
     */
    @ApiModelProperty(value="入账数量")
    @TableField(value="entry_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "入账数量")
    private BigDecimal entryNumber;
    /**
     * delivery_number
     * 出库数量
     */
    @ApiModelProperty(value="出库数量")
    @TableField(value="delivery_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "出库数量")
    private BigDecimal deliveryNumber;
    /**
     * accept_number
     * 领用数量
     */
    @ApiModelProperty(value="领用数量")
    @TableField(value="accept_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "领用数量")
    private BigDecimal acceptNumber;
    /**
     * borrow_number
     * 借入数量
     */
    @ApiModelProperty(value="借入数量")
    @TableField(value="borrow_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "借入数量")
    private BigDecimal borrowNumber;
    /**
     * lend_number
     * 借出数量
     */
    @ApiModelProperty(value="借出数量")
    @TableField(value="lend_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "借出数量")
    private BigDecimal lendNumber;
    /**
     * transfer_number
     * 调拨数量
     */
    @ApiModelProperty(value="调拨数量")
    @TableField(value="transfer_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "调拨数量")
    private BigDecimal transferNumber;
    /**
     * repair_number
     * 维修数量
     */
    @ApiModelProperty(value="维修数量")
    @TableField(value="repair_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "维修数量")
    private BigDecimal repairNumber;
    /**
     * scrapped_number
     * 报废数量
     */
    @ApiModelProperty(value="报废数量")
    @TableField(value="scrapped_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "报废数量")
    private BigDecimal scrappedNumber;
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
     * item_type
     * 物资类型
     */
    @ApiModelProperty(value="物资类型")
    @TableField(value="item_type")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "物资类型")
    private String itemType;
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
     * source_id
     * 登记/验收id
     */
    @ApiModelProperty(value="登记/验收id")
    @TableField(value="source_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "登记/验收id")
    private Long sourceId;
    /**
     * asset_source
     * 物资来源
     */
    @ApiModelProperty(value="物资来源")
    @TableField(value="asset_source")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "物资来源")
    private Integer assetSource;
    /**
     * sku_codes
     * 子集编码拼接
     */
    @ApiModelProperty(value="子集编码拼接")
    @TableField(value="sku_codes")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "子集编码拼接")
    private String skuCodes;
    /**
     * register_user_id
     * 采购登记人
     */
    @ApiModelProperty(value="验收人")
    @TableField(value="register_user_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "采购登记人")
    private Long registerUserId;
    /**
     * register_user_name
     * 采购登记人
     */
    @ApiModelProperty(value="采购登记人")
    @TableField(value="register_user_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "采购登记人")
    private String registerUserName;
    /**
     * accept_user_id
     * 验收人
     */
    @ApiModelProperty(value="验收人")
    @TableField(value="accept_user_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "验收人")
    private Long acceptUserId;
    /**
     * accept_user_name
     * 验收人
     */
    @ApiModelProperty(value="验收人")
    @TableField(value="accept_user_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "验收人")
    private String acceptUserName;

    /**
     * use_user_org_id
     * 使用机构ID(资产所在机构id)
     */
    @ApiModelProperty(value="使用机构/物资所在机构 ID")
    @TableField(value="use_user_org_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "使用/物资所在机构ID")
    private Long useUserOrgId;
    /**
     * use_user_org_name
     * 申请人机构名称
     */
    @ApiModelProperty(value="使用机构名称")
    @TableField(value="use_user_org_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "使用机构名称")
    private String useUserOrgName;
    /**
     * use_user_dept_id
     * 申请人部门ID
     */
    @ApiModelProperty(value="使用部门ID")
    @TableField(value="use_user_dept_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "使用部门ID")
    private Long useUserDeptId;
    /**
     * use_user_dept_name
     * 申请人部门名称
     */
    @ApiModelProperty(value="使用部门名称")
    @TableField(value="use_user_dept_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "使用部门名称")
    private String useUserDeptName;
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
