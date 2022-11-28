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
import java.util.List;
/**
 * @Description 维修申请详情 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-04-27 10:28:25
 */
@Data
@ApiModel(value = "维修申请详情 Vo")
public class MaintainApplyDetailVo extends IVo{

    /**
     * maintain_apply_id
     * 维修申请id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="维修申请id")
    private Long maintainApplyId;
    /**
     * maintain_plan_detail_id
     * 维修计划详情id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="维修计划详情id")
    private Long maintainPlanDetailId;
    /**
     * maintain_assets_detail_id
     * 维修资产详情id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="维修资产详情id")
    private Long maintainAssetsDetailId;
    /**
     * assets_id
     * 资产Id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="资产Id")
    private Long assetsId;
    /**
     * assets_detail_id
     * 资产明细id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="资产明细id")
    private Long assetsDetailId;
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
     * category_id
     * 分类类别Id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="分类类别Id")
    private Long categoryId;
    /**
     * category_code
     * 分类类别编码
     */
    @ApiModelProperty(value="分类类别编码")
    private String categoryCode;
    /**
     * category_name
     * 分类类别名称
     */
    @ApiModelProperty(value="分类类别名称")
    private String categoryName;
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
     * assets_classification
     * 资产分类
     */
    @ApiModelProperty(value="资产分类")
    private String assetsClassification;
    /**
     * assets_code
     * 资产编号
     */
    @ApiModelProperty(value="资产编号")
    private String assetsCode;
    /**
     * tax_rate
     * 税率
     */
    @ApiModelProperty(value="税率")
    private BigDecimal taxRate;
    /**
     * stock_number
     * 当前库存数量
     */
    @ApiModelProperty(value="当前库存数量")
    private BigDecimal stockNumber;
    /**
     * maintain_plan_number
     * 计划维修数量
     */
    @ApiModelProperty(value="计划维修数量")
    private BigDecimal maintainPlanNumber;
    /**
     * maintain_apply_number
     * 维修申请数量
     */
    @ApiModelProperty(value="维修申请数量")
    private BigDecimal maintainApplyNumber;
    /**
     * not_maintain_number
     * 未维修数量
     */
    @ApiModelProperty(value="未维修数量")
    private BigDecimal notMaintainNumber;
    /**
     * purchase_amount
     * 购置单价
     */
    @ApiModelProperty(value="购置单价")
    private BigDecimal purchaseAmount;
    /**
     * purchase_untaxed_amount
     * 购置单价-未税
     */
    @ApiModelProperty(value="购置单价-未税")
    private BigDecimal purchaseUntaxedAmount;
    /**
     * purchase_tax_amount
     * 购置税额
     */
    @ApiModelProperty(value="购置税额")
    private BigDecimal purchaseTaxAmount;
    /**
     * purchase_sum_amount
     * 购置总价
     */
    @ApiModelProperty(value="购置总价")
    private BigDecimal purchaseSumAmount;
    /**
     * purchase_sum_untaxed_amount
     * 购置总价-未税
     */
    @ApiModelProperty(value="购置总价-未税")
    private BigDecimal purchaseSumUntaxedAmount;
    /**
     * purchase_sum_tax_amount
     * 购置总税额
     */
    @ApiModelProperty(value="购置总税额")
    private BigDecimal purchaseSumTaxAmount;
    /**
     * is_secret
     * 是否涉密
     */
    @ApiModelProperty(value="是否涉密")
    private Integer isSecret;
    /**
     * maintain_apply_data
     * 维修日期
     */
    @ApiModelProperty(value="维修日期")
    private LocalDateTime maintainApplyData;
    /**
     * maintain_content
     * 保养内容
     */
    @ApiModelProperty(value="保养内容")
    private String maintainContent;
    /**
     * apply_type
     * 0计划内，1计划外
     */
    @ApiModelProperty(value="0计划内，1计划外")
    private Integer applyType;
    /**
     * maintain_state
     * 维修状态0未登记，1已登记
     */
    @ApiModelProperty(value="维修状态0未登记，1已登记")
    private Integer maintainState;


}
