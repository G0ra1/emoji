package com.netwisd.biz.asset.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 资产领用详情结果 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:45:59
 */
@Data
@ApiModel(value = "资产领用详情结果 Vo")
public class MaterialAcceptResultVo extends IVo{

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
     * accept_id
     * 领用id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="领用id")
    private Long acceptId;
    /**
     * accept_code
     * 领用编码
     */
    
    @ApiModelProperty(value="领用编码")
    private String acceptCode;
    /**
     * accept_assets_id
     * 领用资产id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="领用资产id")
    private Long acceptAssetsId;
    /**
     * accept_detail_id
     * 领用资产明细id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="领用资产明细id")
    private Long acceptDetailId;
    /**
     * item_id
     * 物资Id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="物资Id")
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
     * tax_rate
     * 税率
     */
    
    @ApiModelProperty(value="税率")
    private BigDecimal taxRate;
    /**
     * accept_amount
     * 领用单价
     */
    
    @ApiModelProperty(value="领用单价")
    private BigDecimal acceptAmount;
    /**
     * accept_untaxed_amount
     * 物资领用单价-未税
     */
    
    @ApiModelProperty(value="物资领用单价-未税")
    private BigDecimal acceptUntaxedAmount;
    /**
     * accept_tax_amount
     * 物资领用税额
     */
    
    @ApiModelProperty(value="物资领用税额")
    private BigDecimal acceptTaxAmount;
    /**
     * accept_number
     * 领用数量
     */
    
    @ApiModelProperty(value="领用数量")
    private BigDecimal acceptNumber;
    /**
     * refund_number
     * 退还数量
     */
    
    @ApiModelProperty(value="退还数量")
    private BigDecimal refundNumber;
    /**
     * not_refund_number
     * 未退还数量
     */
    
    @ApiModelProperty(value="未退还数量")
    private BigDecimal notRefundNumber;
    /**
     * item_type
     * 物资类型
     */
    @ApiModelProperty(value="物资类型")
    private String itemType;
    /**
     * asset_org_id
     * 资产所属机构id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="资产所属机构id")
    private Long assetOrgId;
    /**
     * asset_org_name
     * 资产所属机构名称
     */
    @ApiModelProperty(value="资产所属机构名称")
    private String assetOrgName;
    /**
     * asset_org_full_id
     * 组织全路径ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="组织全路径ID")
    private Long assetOrgFullId;
    /**
     * asset_dept_id
     * 资产所属部门
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="资产所属部门")
    private Long assetDeptId;
    /**
     * asset_dept_name
     * 资产所属部门名称
     */
    @ApiModelProperty(value="资产所属部门名称")
    private String assetDeptName;
    /**
     * asset_user_id
     * 资产所属人
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="资产所属人")
    private Long assetUserId;
    /**
     * asset_user_name
     * 资产所属人名称
     */
    @ApiModelProperty(value="资产所属人名称")
    private String assetUserName;
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
     * classify_id
     * 分类id
     */
    @ApiModelProperty(value="分类id")
    private String classifyId;
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
     * item_type_name
     * 物资类型名称
     */
    @ApiModelProperty(value="物资类型名称")
    private String itemTypeName;
    /**
     * quality_assurance_level
     * 质保等级
     */
    @ApiModelProperty(value="质保等级")
    private String qualityAssuranceLevel;
}
