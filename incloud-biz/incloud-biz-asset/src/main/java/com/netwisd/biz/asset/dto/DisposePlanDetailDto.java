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
import java.util.List;
import com.netwisd.common.db.anntation.Fk;
import com.netwisd.common.db.anntation.Map;
/**
 * @Description 处置计划明细 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-07 17:50:50
 */
@Data
@Map("incloud_biz_asset_dispose_plan_detail")
@ApiModel(value = "处置计划明细 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DisposePlanDetailDto extends IDto{

    public DisposePlanDetailDto(Args args){
        super(args);
    }
    /**
     * dispose_plan_id
     * 处置计划id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Fk(table = "incloud_biz_asset_dispose_plan" ,field = "id")
    @ApiModelProperty(value="处置计划id")
    private Long disposePlanId;
    /**
     * assets_id
     * 资产Id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="资产Id")
    private Long assetsId;
    /**
     * assets_detail_id
     * 资产明细id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="资产明细id")
    private Long assetsDetailId;
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
    @JsonDeserialize(using = IdTypeDeserializer.class)
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
     * 库存数量
     */
    @ApiModelProperty(value="库存数量")
    private BigDecimal stockNumber;
    /**
     * dispose_plan_number
     * 计划数量
     */
    @ApiModelProperty(value="计划数量")
    private BigDecimal disposePlanNumber;
    /**
     * assets_amount
     * 资产原值单价
     */
    @ApiModelProperty(value="资产原值单价")
    private BigDecimal assetsAmount;
    /**
     * assets_untaxed_amount
     * 资产原值单价-未税
     */
    @ApiModelProperty(value="资产原值单价-未税")
    private BigDecimal assetsUntaxedAmount;
    /**
     * assets_tax_amount
     * 资产原值税额
     */
    @ApiModelProperty(value="资产原值税额")
    private BigDecimal assetsTaxAmount;
    /**
     * assets_sum_amount
     * 资产原值总价
     */
    @ApiModelProperty(value="资产原值总价")
    private BigDecimal assetsSumAmount;
    /**
     * assets_sum_untaxed_amount
     * 资产原值总价-未税
     */
    @ApiModelProperty(value="资产原值总价-未税")
    private BigDecimal assetsSumUntaxedAmount;
    /**
     * assets_sum_tax_amount
     * 物资原值总税额
     */
    @ApiModelProperty(value="物资原值总税额")
    private BigDecimal assetsSumTaxAmount;
    /**
     * use_data
     * 开始使用日期
     */
    @ApiModelProperty(value="开始使用日期")
    private LocalDateTime useData;
    /**
     * withdrawal_month
     * 已计提月份
     */
    @ApiModelProperty(value="已计提月份")
    private String withdrawalMonth;
    /**
     * depreciation
     * 累计折旧
     */
    @ApiModelProperty(value="累计折旧")
    private BigDecimal depreciation;
    /**
     * valid_period
     * 规定使用年限
     */
    @ApiModelProperty(value="规定使用年限")
    private String validPeriod;
    /**
     * jing_zhi
     * 净值
     */
    @ApiModelProperty(value="净值")
    private BigDecimal jingZhi;
    /**
     * jing_can_zhi
     * 净残值
     */
    @ApiModelProperty(value="净残值")
    private BigDecimal jingCanZhi;
    /**
     * jinge
     * 净额
     */
    @ApiModelProperty(value="净额")
    private BigDecimal jinge;
    /**
     * dispose_reason
     * 处置原因
     */
    @ApiModelProperty(value="处置原因")
    private String disposeReason;
    /**
     * dispose_plan_date
     * 预计处置时间
     */
    @ApiModelProperty(value="预计处置时间")
    private LocalDateTime disposePlanDate;


}
