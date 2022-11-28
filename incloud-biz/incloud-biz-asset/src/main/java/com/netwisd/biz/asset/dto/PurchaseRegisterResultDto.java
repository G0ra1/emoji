package com.netwisd.biz.asset.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.base.common.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @Description 采购登记结果表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-06-14 10:39:32
 */
@Data
@ApiModel(value = "采购登记结果表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PurchaseRegisterResultDto extends IDto{

    public PurchaseRegisterResultDto(Args args){
        super(args);
    }
    /**
     * classify_id
     * 分类id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="分类id")
    private Long classifyId;

    /**
     * classify_code
     * 分类编码
     */
    @ApiModelProperty(value="分类编码")
    private String classifyCode;

    /**
     * classify_name
     * 分类名称
     */
    @ApiModelProperty(value="分类名称")
    private String classifyName;

    /**
     * item_type
     * 物资类型
     */
    @ApiModelProperty(value="物资类型")
    private String itemType;

    /**
     * route
     * 物资分类全路径
     */
    @ApiModelProperty(value="物资分类全路径")
    private String route;

    /**
     * route_name
     * 物资分类全路径名称
     */
    @ApiModelProperty(value="物资分类全路径名称")
    private String routeName;

    /**
     * item_code
     * 物资编码
     */
    @ApiModelProperty(value="物资编码")
    private String itemCode;

    /**
     * item_name
     * 物资名称
     */
    @ApiModelProperty(value="物资名称")
    private String itemName;

    /**
     * desclong
     * 物资长描述
     */
    @ApiModelProperty(value="物资长描述")
    private String desclong;

    /**
     * descshort
     * 物资短描述
     */
    @ApiModelProperty(value="物资短描述")
    private String descshort;

    /**
     * unit_code
     * 物资单位编码
     */
    @ApiModelProperty(value="物资单位编码")
    private String unitCode;

    /**
     * unit_name
     * 物资单位名称
     */
    @ApiModelProperty(value="物资单位名称")
    private String unitName;

    /**
     * material_quality
     * 物资材质
     */
    @ApiModelProperty(value="物资材质")
    private String materialQuality;

    /**
     * standard
     * 物资标准
     */
    @ApiModelProperty(value="物资标准")
    private String standard;

    /**
     * specs
     * 物资规格
     */
    @ApiModelProperty(value="物资规格")
    private String specs;

    /**
     * supplier_name
     * 供应商
     */
    @ApiModelProperty(value="供应商")
    private String supplierName;

    /**
     * contract_code
     * 合同号
     */
    @ApiModelProperty(value="合同号")
    private String contractCode;

    /**
     * acceptance_number
     * 验收数量
     */
    @ApiModelProperty(value="验收数量")
    private BigDecimal acceptanceNumber;

    /**
     * create_user_id
     * 创建人ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="创建人ID")
    private Long createUserId;

    /**
     * create_user_name
     * 创建人名称
     */
    @ApiModelProperty(value="创建人名称")
    private String createUserName;

    /**
     * create_user_parent_org_id
     * 创建人父级机构ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="创建人父级机构ID")
    private Long createUserParentOrgId;

    /**
     * create_user_parent_org_name
     * 创建人父级机构名称
     */
    @ApiModelProperty(value="创建人父级机构名称")
    private String createUserParentOrgName;

    /**
     * create_user_parent_dept_id
     * 创建人父级部门ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="创建人父级部门ID")
    private Long createUserParentDeptId;

    /**
     * create_user_parent_dept_name
     * 创建人父级部门名称
     */
    @ApiModelProperty(value="创建人父级部门名称")
    private String createUserParentDeptName;

    /**
     * create_user_org_full_id
     * 创建人父级组织全路径ID
     */
    @ApiModelProperty(value="创建人父级组织全路径ID")
    private String createUserOrgFullId;

    /**
     * reason
     * 事由
     */
    @ApiModelProperty(value="事由")
    private String reason;

    /**
     * apply_time
     * 申请时间
     */
    @ApiModelProperty(value="申请时间")
    private LocalDateTime applyTime;

    /**
     * apply_user_id
     * 申请人ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="申请人ID")
    private Long applyUserId;

    /**
     * apply_user_name
     * 申请人名称
     */
    @ApiModelProperty(value="申请人名称")
    private String applyUserName;

    /**
     * register_id
     * 采购id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="采购id")
    private Long registerId;

    /**
     * register_code
     * 采购编码
     */
    @ApiModelProperty(value="采购编码")
    private String registerCode;

    /**
     * register_tax_rate
     * 登记税率
     */
    @ApiModelProperty(value="登记税率")
    private BigDecimal registerTaxRate;

    /**
     * register_number
     * 登记数量
     */
    @ApiModelProperty(value="登记数量")
    private BigDecimal registerNumber;

    /**
     * register_amount
     * 登记单价
     */
    @ApiModelProperty(value="登记单价")
    private BigDecimal registerAmount;

    /**
     * register_untaxed_amount
     * 登记单价-未税
     */
    @ApiModelProperty(value="登记单价-未税")
    private BigDecimal registerUntaxedAmount;

    /**
     * register_tax_amount
     * 登记单价-税额
     */
    @ApiModelProperty(value="登记单价-税额")
    private BigDecimal registerTaxAmount;

    /**
     * register_sum_amount
     * 登记总价
     */
    @ApiModelProperty(value="登记总价")
    private BigDecimal registerSumAmount;

    /**
     * register_sum_untaxed_amount
     * 登记总价-未税
     */
    @ApiModelProperty(value="登记总价-未税")
    private BigDecimal registerSumUntaxedAmount;

    /**
     * register_sum_tax_amount
     * 登记总价-税额
     */
    @ApiModelProperty(value="登记总价-税额")
    private BigDecimal registerSumTaxAmount;

    /**
     * register_assets_id
     * 采购明细id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="采购明细id")
    private Long registerAssetsId;

    /**
     * not_acceptance_number
     * 未验收数量
     */
    @ApiModelProperty(value="未验收数量")
    private BigDecimal notAcceptanceNumber;

    /**
     * storage_number
     * 入库数量
     */
    @ApiModelProperty(value="入库数量")
    private BigDecimal storageNumber;

    /**
     * not_storage_number
     * 未入库数量
     */
    @ApiModelProperty(value="未入库数量")
    private BigDecimal notStorageNumber;

    /**
     * purchase_type
     * 物资购置类型
     */
    @ApiModelProperty(value="物资购置类型")
    private Integer purchaseType;

    /**
     * apply_user_org_id
     * 申请人机构ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="申请人机构ID")
    private Long applyUserOrgId;

    /**
     * apply_user_org_name
     * 申请人机构名称
     */
    @ApiModelProperty(value="申请人机构名称")
    private String applyUserOrgName;

    /**
     * apply_user_dept_id
     * 申请人部门ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="申请人部门ID")
    private Long applyUserDeptId;

    /**
     * apply_user_dept_name
     * 申请人部门名称
     */
    @ApiModelProperty(value="申请人部门名称")
    private String applyUserDeptName;

    /**
     * sku_codes
     * sku属性
     */
    @ApiModelProperty(value="sku属性")
    private String skuCodes;

    /**
     * item_id
     * 物资Id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="物资Id")
    private Long itemId;

    /**
     * register_detail_id
     * 采购详情id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="采购详情id")
    private Long registerDetailId;

    /**
     * supplier_id
     * 供应商id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="供应商id")
    private Long supplierId;

    /**
     * contract_id
     * 合同id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="合同id")
    private Long contractId;

    /**
     * 指定字段模糊查询
     */
    @ApiModelProperty(value="查询条件")
    private String searchCondition;
}
