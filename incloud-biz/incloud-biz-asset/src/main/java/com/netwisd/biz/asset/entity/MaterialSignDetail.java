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
 * @Description $签收详情 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-06-24 11:09:16
 */
@Data
@Table(value = "incloud_biz_asset_material_sign_detail",comment = "签收详情")
@TableName("incloud_biz_asset_material_sign_detail")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "签收详情 Entity")
public class MaterialSignDetail extends IModel<MaterialSignDetail> {

    /**
     * sign_id
     * 签收id
     */
    @ApiModelProperty(value="签收id")
    @TableField(value="sign_id")
    @Column(type = DataType.BIGINT, length = 19, fkTableName = "incloud_biz_asset_material_sign" ,fkFieldName = "id" , isNull = true, comment = "签收id")
    private Long signId;
    /**
     * sign_code
     * 签收code
     */
    @ApiModelProperty(value="签收code")
    @TableField(value="sign_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "签收code")
    private String signCode;
    /**
     * assets_id
     * 资产Id
     */
    @ApiModelProperty(value="资产Id")
    @TableField(value="assets_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "资产Id")
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
     * item_type
     * 物资类型
     */
    @ApiModelProperty(value="物资类型")
    @TableField(value="item_type")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资类型")
    private String itemType;
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
     * tax_rate
     * 税率
     */
    @ApiModelProperty(value="税率")
    @TableField(value="tax_rate")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "税率")
    private BigDecimal taxRate;
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
     * supplier_name
     * 供应商
     */
    @ApiModelProperty(value="供应商")
    @TableField(value="supplier_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "供应商")
    private String supplierName;
    /**
     * contract_code
     * 合同号
     */
    @ApiModelProperty(value="合同号")
    @TableField(value="contract_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "合同号")
    private String contractCode;
    /**
     * assets_classification
     * 资产分类
     */
    @ApiModelProperty(value="资产分类")
    @TableField(value="assets_classification")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "资产分类")
    private String assetsClassification;
    /**
     * sign_number
     * 签收数量
     */
    @ApiModelProperty(value="签收数量")
    @TableField(value="sign_number")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "签收数量")
    private BigDecimal signNumber;
    /**
     * distribute_number
     * 派发数量
     */
    @ApiModelProperty(value="派发数量")
    @TableField(value="distribute_number")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "签收数量")
    private BigDecimal distributeNumber;
    /**
     * assets_code
     * 资产编号
     */
    @ApiModelProperty(value="资产编号")
    @TableField(value="assets_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "资产编号")
    private String assetsCode;
    /**
     * quality_assurance_level
     * 质保等级
     */
    @ApiModelProperty(value="质保等级")
    @TableField(value="quality_assurance_level")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "质保等级")
    private String qualityAssuranceLevel;
    /**
     * manufacturer
     * 生产商
     */
    @ApiModelProperty(value="生产商")
    @TableField(value="manufacturer")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "生产商")
    private String manufacturer;
    /**
     * production_date
     * 生产日期
     */
    @ApiModelProperty(value="生产日期")
    @TableField(value="production_date")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "生产日期")
    private LocalDateTime productionDate;
    /**
     * service_life
     * 使用年限
     */
    @ApiModelProperty(value="使用年限")
    @TableField(value="service_life")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "使用年限")
    private BigDecimal serviceLife;
    /**
     * valid_period
     * 有效期
     */
    @ApiModelProperty(value="有效期")
    @TableField(value="valid_period")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "有效期")
    private String validPeriod;
    /**
     * batch_number
     * 批次/炉号
     */
    @ApiModelProperty(value="批次/炉号")
    @TableField(value="batch_number")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "批次/炉号")
    private String batchNumber;
    /**
     * acceptance_date
     * 验收日期
     */
    @ApiModelProperty(value="验收日期")
    @TableField(value="acceptance_date")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "验收日期")
    private LocalDateTime acceptanceDate;
    /**
     * factory_date
     * 出厂日期
     */
    @ApiModelProperty(value="出厂日期")
    @TableField(value="factory_date")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "出厂日期")
    private LocalDateTime factoryDate;
    /**
     * factory_code
     * 出厂编号
     */
    @ApiModelProperty(value="出厂编号")
    @TableField(value="factory_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "出厂编号")
    private String factoryCode;
    /**
     * approach_date
     * 进场日期
     */
    @ApiModelProperty(value="进场日期")
    @TableField(value="approach_date")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "进场日期")
    private LocalDateTime approachDate;
    /**
     * factory_sn
     * 设备出厂SN号
     */
    @ApiModelProperty(value="设备出厂SN号")
    @TableField(value="factory_sn")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "设备出厂SN号")
    private String factorySn;
    /**
     * factory_data
     * 设备出厂资料
     */
    @ApiModelProperty(value="设备出厂资料")
    @TableField(value="factory_data")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "设备出厂资料")
    private String factoryData;
    /**
     * purchase_type
     * 物资购置类型
     */
    @ApiModelProperty(value="物资购置类型")
    @TableField(value="purchase_type")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "物资购置类型")
    private Integer purchaseType;
    /**
     * accept_photo
     * 设备验收照片
     */
    @ApiModelProperty(value="设备验收照片")
    @TableField(value="accept_photo")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "设备验收照片")
    private String acceptPhoto;
    /**
     * reception_date
     * 接收日期
     */
    @ApiModelProperty(value="接收日期")
    @TableField(value="reception_date")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "接收日期")
    private String receptionDate;
    /**
     * apply_dept_id
     * 购置申请部门
     */
    @ApiModelProperty(value="购置申请部门")
    @TableField(value="apply_dept_id")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "购置申请部门")
    private String applyDeptId;
    /**
     * apply_dept_nema
     * 购置申请部门名称
     */
    @ApiModelProperty(value="购置申请部门名称")
    @TableField(value="apply_dept_nema")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "购置申请部门名称")
    private String applyDeptNema;
    /**
     * apply_org_id
     * 购置申请机构id
     */
    @ApiModelProperty(value="购置申请机构id")
    @TableField(value="apply_org_id")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "购置申请机构id")
    private String applyOrgId;
    /**
     * apply_org_name
     * 购置申请机构名称
     */
    @ApiModelProperty(value="购置申请机构名称")
    @TableField(value="apply_org_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "购置申请机构名称")
    private String applyOrgName;
    /**
     * series_number
     * 物资序列号;出厂时的唯一序号
     */
    @ApiModelProperty(value="物资序列号;出厂时的唯一序号")
    @TableField(value="series_number")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "物资序列号;出厂时的唯一序号")
    private String seriesNumber;
    /**
     * sign_status
     * 签收状态
     */
    @ApiModelProperty(value="签收状态")
    @TableField(value="sign_status")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "签收状态")
    private Integer signStatus;
    /**
     * business_id
     * 对应业务id
     * 派发id、或其他业务id
     */
    @ApiModelProperty(value="对应业务id")
    @TableField(value="business_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "派发id、或其他业务id")
    private Long businessId;
    /**
     * business_assets_id
     * 对应业务明细id
     * 派发明细id、或其他业务明细id
     */
    @ApiModelProperty(value="对应业务明细id")
    @TableField(value="business_assets_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "派发明细id、或其他业务明细id")
    private Long businessAssetsId;
    /**
     * business_detail_id
     * 对应业务详情id
     * 派发明细id、或其他业务明细id
     */
    @ApiModelProperty(value="对应业务详情id")
    @TableField(value="business_detail_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "派发详情id、或其他业务详情id")
    private Long businessDetailId;
    /**
     * source_id
     * 来源id
     */
    @ApiModelProperty(value="来源id")
    @TableField(value="source_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "领用code、或其他业务code")
    private Long sourceId;
    /**
     * source_code
     * 来源code
     */
    @ApiModelProperty(value="来源code")
    @TableField(value="source_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "领用code、或其他业务code")
    private String sourceCode;
    /**
     * source_assets_id
     * 来源明细id
     */
    @ApiModelProperty(value="来源id")
    @TableField(value="source_assets_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "领用明细id、或其他业务明细id")
    private Long sourceAssetsId;
}
