package com.netwisd.biz.asset.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 资产出库结果数据 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-09-08 14:34:54
 */
@Data
@ApiModel(value = "资产出库结果数据 Vo")
public class MaterialDeliveryResultVo extends IVo{

    /**
     * assets_id
     * 资产id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="资产id")
    private Long assetsId;
    /**
     * assets_detail_id
     * 资产明细id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="资产明细id")
    private Long assetsDetailId;
    /**
     * delivery_id
     * 出库id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="出库id")
    private Long deliveryId;
    /**
     * delivery_code
     * 出库编码
     */
    
    @ApiModelProperty(value="出库编码")
    private String deliveryCode;
    /**
     * delivery_detail_id
     * 出库资产id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="出库资产id")
    private Long deliveryDetailId;
    /**
     * type
     * 类型
     */
    
    @ApiModelProperty(value="类型")
    private String type;
    /**
     * classify_id
     * 分类id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
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
     * item_id
     * 物资id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="物资id")
    private Long itemId;
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
     * sku_code
     * sku属性
     */
    
    @ApiModelProperty(value="sku属性")
    private String skuCode;
    /**
     * sku_full_id
     * sku全路径id
     */
    
    @ApiModelProperty(value="sku全路径id")
    private String skuFullId;
    /**
     * sku_full_name
     * sku全路径名称
     */
    
    @ApiModelProperty(value="sku全路径名称")
    private String skuFullName;
    /**
     * apply_nuimber
     * 申请数量
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="申请数量")
    private Long applyNuimber;
    /**
     * delivery_number
     * 出库数量
     */
    
    @ApiModelProperty(value="出库数量")
    private BigDecimal deliveryNumber;
    /**
     * delivery_amount
     * 出库单价
     */
    
    @ApiModelProperty(value="出库单价")
    private BigDecimal deliveryAmount;
    /**
     * delivery_untaxed_amount
     * 物资出库单价-未税
     */
    
    @ApiModelProperty(value="物资出库单价-未税")
    private BigDecimal deliveryUntaxedAmount;
    /**
     * delivery_tax_amount
     * 物资出库税额
     */
    
    @ApiModelProperty(value="物资出库税额")
    private BigDecimal deliveryTaxAmount;
    /**
     * withdrawal_number
     * 退库数量
     */
    
    @ApiModelProperty(value="退库数量")
    private BigDecimal withdrawalNumber;
    /**
     * not_withdrawal_number
     * 未退库数量
     */
    
    @ApiModelProperty(value="未退库数量")
    private BigDecimal notWithdrawalNumber;
    /**
     * create_user_id
     * 创建人ID
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
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
    @JsonSerialize(using = IdTypeSerializer.class) 
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
    @JsonSerialize(using = IdTypeSerializer.class) 
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
     * apply_time
     * 申请时间
     */
    
    @ApiModelProperty(value="申请时间")
    private LocalDateTime applyTime;
    /**
     * apply_user_id
     * 申请人ID
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="申请人ID")
    private Long applyUserId;
    /**
     * apply_user_name
     * 申请人名称
     */
    
    @ApiModelProperty(value="申请人名称")
    private String applyUserName;
    /**
     * apply_user_org_id
     * 申请人机构ID
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
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
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="申请人部门ID")
    private Long applyUserDeptId;
    /**
     * apply_user_dept_name
     * 申请人部门名称
     */
    
    @ApiModelProperty(value="申请人部门名称")
    private String applyUserDeptName;
    /**
     * type_name
     * 类型名称
     */
    
    @ApiModelProperty(value="类型名称")
    private String typeName;
    /**
     * asset_self_code
     * 资产自编码
     */
    
    @ApiModelProperty(value="资产自编码")
    private String assetSelfCode;
    /**
     * assets_code
     * 资产编码
     */
    
    @ApiModelProperty(value="资产编码")
    private String assetsCode;
    /**
     * delivery_assets_code
     * 出库后资产编码
     */
    
    @ApiModelProperty(value="出库后资产编码")
    private String deliveryAssetsCode;
    /**
     * assets_nuimber
     * 当前库存数量
     */
    
    @ApiModelProperty(value="当前库存数量")
    private BigDecimal assetsNuimber;
    /**
     * withdrawal_amount
     * 退库金额
     */
    
    @ApiModelProperty(value="退库金额")
    private BigDecimal withdrawalAmount;
    /**
     * not_withdrawal_amount
     * 未退库金额
     */
    
    @ApiModelProperty(value="未退库金额")
    private BigDecimal notWithdrawalAmount;
}
