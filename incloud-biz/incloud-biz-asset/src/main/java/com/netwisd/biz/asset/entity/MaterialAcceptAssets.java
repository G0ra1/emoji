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
 * @Description $资产领用资产明细 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:45:59
 */
@Data
@Table(value = "incloud_biz_asset_material_accept_assets",comment = "资产领用资产明细")
@TableName("incloud_biz_asset_material_accept_assets")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "资产领用资产明细 Entity")
public class MaterialAcceptAssets extends IModel<MaterialAcceptAssets> {

    /**
     * accept_id
     * 领用id
     */
    @ApiModelProperty(value="领用id")
    @TableField(value="accept_id")
    @Column(type = DataType.BIGINT, length = 19, fkTableName = "incloud_biz_asset_maint_accept" ,fkFieldName = "id" , isNull = true, comment = "领用id")
    private Long acceptId;
    /**
     * assets_id
     * 资产id
     */
    @ApiModelProperty(value="资产id")
    @TableField(value="assets_id")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "资产id")
    private Long assetsId;
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
     * category_id
     * 分类类别Id
     */
    @ApiModelProperty(value="分类类别Id")
    @TableField(value="category_id")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "分类类别Id")
    private String categoryId;
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
     * stock_number
     * 当前库存数量
     */
    @ApiModelProperty(value="当前库存数量")
    @TableField(value="stock_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "当前库存数量")
    private BigDecimal stockNumber;
    /**
     * accept_number
     * 领用数量
     */
    @ApiModelProperty(value="领用数量")
    @TableField(value="accept_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "领用数量")
    private BigDecimal acceptNumber;
    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    @TableField(value="remark")
    @Column(type = DataType.VARCHAR, length = 500,  isNull = true, comment = "备注")
    private String remark;
    /**
     * item_type
     * 物资类型
     */
    @ApiModelProperty(value="物资类型")
    @TableField(value="item_type")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资类型")
    private String itemType;
    /**
     * distribute_number
     * 派发数量
     */
    @ApiModelProperty(value="派发数量")
    @TableField(value="distribute_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "派发数量")
    private BigDecimal distributeNumber;
    /**
     * not_distribute_number
     * 未派发数量
     */
    @ApiModelProperty(value="未派发数量")
    @TableField(value="not_distribute_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "未派发数量")
    private BigDecimal notDistributeNumber;
    /**
     * sign_number
     * 签收数量
     */
    @ApiModelProperty(value="签收数量")
    @TableField(value="sign_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "签收数量")
    private BigDecimal signNumber;
    /**
     * not_sign_number
     * 未签收数量
     */
    @ApiModelProperty(value="未签收数量")
    @TableField(value="not_sign_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "未签收数量")
    private BigDecimal notSignNumber;
    /**
     * usable_number
     * 可用数量
     */
    @ApiModelProperty(value="可用数量")
    @TableField(value="usable_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "可用数量")
    private BigDecimal usableNumber;
}
