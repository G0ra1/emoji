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
 * @Description 资产退还详情 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:46:00
 */
@Data
@Map("incloud_biz_asset_material_refund_detail")
@ApiModel(value = "资产退还详情 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MaterialRefundDetailDto extends IDto{

    public MaterialRefundDetailDto(Args args){
        super(args);
    }
    /**
     * refund_id
     * 退库id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Fk(table = "incloud_biz_asset_maint_refund" ,field = "id")
    @ApiModelProperty(value="退库id")
    private Long refundId;
    /**
     * assets_id
     * 资产id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="资产id")
    private Long assetsId;
    /**
     * assets_detail_id
     * 资产明细id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="资产明细id")
    private Long assetsDetailId;
    /**
     * assets_accept_id
     * 资产出库id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="资产出库id")
    private Long assetsAcceptId;
    /**
     * item_id
     * 物资Id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
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
     * assets_code
     * 资产编号
     */
    @ApiModelProperty(value="资产编号")
    private String assetsCode;
    /**
     * assets_label
     * 资产标签
     */
    @ApiModelProperty(value="资产标签")
    private String assetsLabel;
    /**
     * warehouse_id
     * 仓库地点
     */
    @ApiModelProperty(value="仓库地点")
    private String warehouseId;
    /**
     * warehouse_name
     * 仓库地点
     */
    @ApiModelProperty(value="仓库地点")
    private String warehouseName;
    /**
     * shelf_id
     * 货架号
     */
    @ApiModelProperty(value="货架号")
    private String shelfId;
    /**
     * shelf_name
     * 货架号
     */
    @ApiModelProperty(value="货架号")
    private String shelfName;
    /**
     * tax_rate
     * 税率
     */
    @ApiModelProperty(value="税率")
    private BigDecimal taxRate;
    /**
     * accept_number
     * 出库数量
     */
    @ApiModelProperty(value="出库数量")
    private BigDecimal acceptNumber;
    /**
     * refund_number
     * 退库数量
     */
    @ApiModelProperty(value="退库数量")
    private BigDecimal refundNumber;
    /**
     * refund_amount
     * 物资退库单价
     */
    @ApiModelProperty(value="物资退库单价")
    private BigDecimal refundAmount;
    /**
     * refund_untaxed_amount
     * 物资退库单价-未税
     */
    @ApiModelProperty(value="物资退库单价-未税")
    private BigDecimal refundUntaxedAmount;
    /**
     * refund_tax_amount
     * 物资退库税额
     */
    @ApiModelProperty(value="物资退库税额")
    private BigDecimal refundTaxAmount;


}
