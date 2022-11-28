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
 * @Description $资产领用详情结果 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:45:59
 */
@Data
@Table(value = "incloud_biz_asset_material_accept_result",comment = "资产领用详情结果")
@TableName("incloud_biz_asset_material_accept_result")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "资产领用详情结果 Entity")
public class MaterialAcceptResult extends IModel<MaterialAcceptResult> {

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
    * accept_id
    * 领用id
    */
    @ApiModelProperty(value="领用id")
    @TableField(value="accept_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "领用id")
    private Long acceptId;

    /**
    * accept_code
    * 领用编码
    */
    @ApiModelProperty(value="领用编码")
    @TableField(value="accept_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "领用编码")
    private String acceptCode;

    /**
    * accept_assets_id
    * 领用资产id
    */
    @ApiModelProperty(value="领用资产id")
    @TableField(value="accept_assets_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "领用资产id")
    private Long acceptAssetsId;

    /**
    * accept_detail_id
    * 领用资产明细id
    */
    @ApiModelProperty(value="领用资产明细id")
    @TableField(value="accept_detail_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "领用资产明细id")
    private Long acceptDetailId;
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
    * tax_rate
    * 税率
    */
    @ApiModelProperty(value="税率")
    @TableField(value="tax_rate")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "税率")
    private BigDecimal taxRate;

    /**
    * accept_amount
    * 领用单价
    */
    @ApiModelProperty(value="领用单价")
    @TableField(value="accept_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "领用单价")
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
    * accept_number
    * 领用数量
    */
    @ApiModelProperty(value="领用数量")
    @TableField(value="accept_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "领用数量")
    private BigDecimal acceptNumber;

    /**
    * refund_number
    * 退还数量
    */
    @ApiModelProperty(value="退还数量")
    @TableField(value="refund_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "退还数量")
    private BigDecimal refundNumber;

    /**
    * not_refund_number
    * 未退还数量
    */
    @ApiModelProperty(value="未退还数量")
    @TableField(value="not_refund_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "未退还数量")
    private BigDecimal notRefundNumber;
    /**
     * item_type
     * 物资类型
     */
    @ApiModelProperty(value="物资类型")
    @TableField(value="item_type")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资类型")
    private String itemType;
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
     * asset_user_id
     * 资产所属人（使用人）
     */
    @ApiModelProperty(value="资产所属人")
    @TableField(value="asset_user_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "资产所属人")
    private Long assetUserId;
    /**
     * asset_user_name
     * 资产所属部人（使用人）名称
     */
    @ApiModelProperty(value="资产所属人名称")
    @TableField(value="asset_user_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "资产所属人名称")
    private String assetUserName;
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
     * classify_id
     * 分类id
     */
    @ApiModelProperty(value="分类id")
    @TableField(value="classify_id")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "分类id")
    private String classifyId;
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
     * quality_assurance_level
     * 质保等级
     */
    @ApiModelProperty(value="质保等级")
    @TableField(value="quality_assurance_level")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "质保等级")
    private String qualityAssuranceLevel;
    /**
     * item_type_name
     * 质保等级
     */
    @ApiModelProperty(value="物资类型名称")
    @TableField(value="item_type_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资类型名称")
    private String itemTypeName;

}
