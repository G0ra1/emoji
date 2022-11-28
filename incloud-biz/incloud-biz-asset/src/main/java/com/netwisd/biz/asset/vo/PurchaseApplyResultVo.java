package com.netwisd.biz.asset.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 购置申请结果表 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-04-21 11:18:24
 */
@Data
@ApiModel(value = "购置申请结果表 Vo")
public class PurchaseApplyResultVo extends IVo{

    /**
     * apply_id
     * 购置申请id;购置申请id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="购置申请id;购置申请id")
    private Long applyId;
    /**
     * apply_code
     * 购置申请编号
     */

    @ApiModelProperty(value="购置申请编号")
    private String applyCode;
    /**
     * apply_detail_id
     * 购置申请详情id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="购置申请详情id")
    private Long applyDetailId;
    /**
     * route
     * 全路径
     */
    
    @ApiModelProperty(value="全路径")
    private String route;
    /**
     * route_name
     * 全路径名称
     */
    
    @ApiModelProperty(value="全路径名称")
    private String routeName;
    /**
     * classify_code
     * 物资分类编码;物资分类编码
     */
    
    @ApiModelProperty(value="物资分类编码;物资分类编码")
    private String classifyCode;
    /**
     * classify_name
     * 物资分类名称;物资分类名称
     */
    
    @ApiModelProperty(value="物资分类名称;物资分类名称")
    private String classifyName;
    /**
     * classify_type
     * 物资类型;物资类型
     */
    
    @ApiModelProperty(value="物资类型;物资类型")
    private String classifyType;
    /**
     * item_id
     * 物资Id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="物资Id")
    private Long itemId;
    /**
     * item_code
     * 物资编码;物资编码
     */
    
    @ApiModelProperty(value="物资编码;物资编码")
    private String itemCode;
    /**
     * item_name
     * 物资名称;物资名称
     */
    
    @ApiModelProperty(value="物资名称;物资名称")
    private String itemName;
    /**
     * desclong
     * 物资长描述;物资长描述
     */
    
    @ApiModelProperty(value="物资长描述;物资长描述")
    private String desclong;
    /**
     * descshort
     * 物资短描述;物资短描述
     */
    
    @ApiModelProperty(value="物资短描述;物资短描述")
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
     * specs
     * 物资规格
     */
    
    @ApiModelProperty(value="物资规格")
    private String specs;
    /**
     * standard
     * 物资标准
     */
    
    @ApiModelProperty(value="物资标准")
    private String standard;
    /**
     * tax_rate
     * 税率
     */
    
    @ApiModelProperty(value="税率")
    private String taxRate;
    /**
     * tax_amount
     * 税额
     */
    
    @ApiModelProperty(value="税额")
    private BigDecimal taxAmount;
    /**
     * apply_number
     * 申请数量
     */
    
    @ApiModelProperty(value="申请数量")
    private BigDecimal applyNumber;
    /**
     * apply_amount
     * 申请含税单价
     */
    
    @ApiModelProperty(value="申请含税单价")
    private BigDecimal applyAmount;
    /**
     * apply_untaxed_amount
     * 申请不含税单价
     */
    
    @ApiModelProperty(value="申请不含税单价")
    private BigDecimal applyUntaxedAmount;
    /**
     * apply_sum_amount
     * 申请含税总价
     */
    
    @ApiModelProperty(value="申请含税总价")
    private BigDecimal applySumAmount;
    /**
     * apply_sum_untaxed_amount
     * 申请不含税总价
     */
    
    @ApiModelProperty(value="申请不含税总价")
    private BigDecimal applySumUntaxedAmount;
    /**
     * purpose
     * 用途
     */
    
    @ApiModelProperty(value="用途")
    private String purpose;
    /**
     * explanation
     * 说明
     */
    
    @ApiModelProperty(value="说明")
    private String explanation;
    /**
     * material_quality
     * 物资材质
     */
    
    @ApiModelProperty(value="物资材质")
    private String materialQuality;

}
