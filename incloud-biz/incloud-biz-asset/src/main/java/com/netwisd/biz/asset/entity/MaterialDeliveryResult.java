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
 * @Description $资产出库结果数据 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-09-08 14:34:54
 */
@Data
@Table(value = "incloud_biz_asset_material_delivery_result",comment = "资产出库结果数据")
@TableName("incloud_biz_asset_material_delivery_result")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "资产出库结果数据 Entity")
public class MaterialDeliveryResult extends IModel<MaterialDeliveryResult> {

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
    * delivery_id
    * 出库id
    */
    @ApiModelProperty(value="出库id")
    @TableField(value="delivery_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "出库id")
    private Long deliveryId;

    /**
    * delivery_code
    * 出库编码
    */
    @ApiModelProperty(value="出库编码")
    @TableField(value="delivery_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "出库编码")
    private String deliveryCode;

    /**
    * delivery_detail_id
    * 出库资产id
    */
    @ApiModelProperty(value="出库资产id")
    @TableField(value="delivery_detail_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "出库资产id")
    private Long deliveryDetailId;

    /**
    * type
    * 类型
    */
    @ApiModelProperty(value="类型")
    @TableField(value="type")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "类型")
    private String type;

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
    * apply_nuimber
    * 申请数量
    */
    @ApiModelProperty(value="申请数量")
    @TableField(value="apply_nuimber")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "申请数量")
    private Long applyNuimber;

    /**
    * delivery_number
    * 出库数量
    */
    @ApiModelProperty(value="出库数量")
    @TableField(value="delivery_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "出库数量")
    private BigDecimal deliveryNumber;

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
    * 物资出库单价-未税
    */
    @ApiModelProperty(value="物资出库单价-未税")
    @TableField(value="delivery_untaxed_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "物资出库单价-未税")
    private BigDecimal deliveryUntaxedAmount;

    /**
    * delivery_tax_amount
    * 物资出库税额
    */
    @ApiModelProperty(value="物资出库税额")
    @TableField(value="delivery_tax_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "物资出库税额")
    private BigDecimal deliveryTaxAmount;

    /**
    * withdrawal_number
    * 退库数量
    */
    @ApiModelProperty(value="退库数量")
    @TableField(value="withdrawal_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "退库数量")
    private BigDecimal withdrawalNumber;

    /**
    * not_withdrawal_number
    * 未退库数量
    */
    @ApiModelProperty(value="未退库数量")
    @TableField(value="not_withdrawal_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "未退库数量")
    private BigDecimal notWithdrawalNumber;

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
    * type_name
    * 类型名称
    */
    @ApiModelProperty(value="类型名称")
    @TableField(value="type_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "类型名称")
    private String typeName;

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
    * assets_nuimber
    * 当前库存数量
    */
    @ApiModelProperty(value="当前库存数量")
    @TableField(value="assets_nuimber")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "当前库存数量")
    private BigDecimal assetsNuimber;

    /**
    * withdrawal_amount
    * 退库金额
    */
    @ApiModelProperty(value="退库金额")
    @TableField(value="withdrawal_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "退库金额")
    private BigDecimal withdrawalAmount;

    /**
    * not_withdrawal_amount
    * 未退库金额
    */
    @ApiModelProperty(value="未退库金额")
    @TableField(value="not_withdrawal_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "未退库金额")
    private BigDecimal notWithdrawalAmount;

}
