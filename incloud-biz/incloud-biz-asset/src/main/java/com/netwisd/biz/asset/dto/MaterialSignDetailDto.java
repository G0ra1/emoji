package com.netwisd.biz.asset.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.base.common.data.IDto;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.data.DataType;
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
 * @Description 签收详情 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-06-24 11:09:16
 */
@Data
@Map("incloud_biz_asset_material_sign_detail")
@ApiModel(value = "签收详情 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MaterialSignDetailDto extends IDto{

    public MaterialSignDetailDto(Args args){
        super(args);
    }
    /**
     * sign_id
     * 签收id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Fk(table = "incloud_biz_asset_material_sign" ,field = "id")
    @ApiModelProperty(value="签收id")
    private Long signId;
    /**
     * sign_code
     * 签收code
     */
    @ApiModelProperty(value="签收code")
    private String signCode;
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
     * tax_rate
     * 税率
     */
    @ApiModelProperty(value="税率")
    private BigDecimal taxRate;
    /**
     * item_id
     * 物资id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
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
     * assets_classification
     * 资产分类
     */
    @ApiModelProperty(value="资产分类")
    private String assetsClassification;
    /**
     * sign_number
     * 签收数量
     */
    @ApiModelProperty(value="签收数量")
    private BigDecimal signNumber;
    /**
     * distribute_number
     * 派发数量
     */
    @ApiModelProperty(value="派发数量")
    private BigDecimal distributeNumber;
    /**
     * assets_code
     * 资产编号
     */
    @ApiModelProperty(value="资产编号")
    private String assetsCode;
    /**
     * quality_assurance_level
     * 质保等级
     */
    @ApiModelProperty(value="质保等级")
    private String qualityAssuranceLevel;
    /**
     * manufacturer
     * 生产商
     */
    @ApiModelProperty(value="生产商")
    private String manufacturer;
    /**
     * production_date
     * 生产日期
     */
    @ApiModelProperty(value="生产日期")
    private LocalDateTime productionDate;
    /**
     * service_life
     * 使用年限
     */
    @ApiModelProperty(value="使用年限")
    private BigDecimal serviceLife;
    /**
     * valid_period
     * 有效期
     */
    @ApiModelProperty(value="有效期")
    private String validPeriod;
    /**
     * batch_number
     * 批次/炉号
     */
    @ApiModelProperty(value="批次/炉号")
    private String batchNumber;
    /**
     * acceptance_date
     * 验收日期
     */
    @ApiModelProperty(value="验收日期")
    private LocalDateTime acceptanceDate;
    /**
     * factory_date
     * 出厂日期
     */
    @ApiModelProperty(value="出厂日期")
    private LocalDateTime factoryDate;
    /**
     * factory_code
     * 出厂编号
     */
    @ApiModelProperty(value="出厂编号")
    private String factoryCode;
    /**
     * approach_date
     * 进场日期
     */
    @ApiModelProperty(value="进场日期")
    private LocalDateTime approachDate;
    /**
     * factory_sn
     * 设备出厂SN号
     */
    @ApiModelProperty(value="设备出厂SN号")
    private String factorySn;
    /**
     * factory_data
     * 设备出厂资料
     */
    @ApiModelProperty(value="设备出厂资料")
    private String factoryData;
    /**
     * purchase_type
     * 物资购置类型
     */
    @ApiModelProperty(value="物资购置类型")
    private Integer purchaseType;
    /**
     * accept_photo
     * 设备验收照片
     */
    @ApiModelProperty(value="设备验收照片")
    private String acceptPhoto;
    /**
     * reception_date
     * 接收日期
     */
    @ApiModelProperty(value="接收日期")
    private String receptionDate;
    /**
     * apply_dept_id
     * 购置申请部门
     */
    @ApiModelProperty(value="购置申请部门")
    private String applyDeptId;
    /**
     * apply_dept_nema
     * 购置申请部门名称
     */
    @ApiModelProperty(value="购置申请部门名称")
    private String applyDeptNema;
    /**
     * apply_org_id
     * 购置申请机构id
     */
    @ApiModelProperty(value="购置申请机构id")
    private String applyOrgId;
    /**
     * apply_org_name
     * 购置申请机构名称
     */
    @ApiModelProperty(value="购置申请机构名称")
    private String applyOrgName;
    /**
     * series_number
     * 物资序列号;出厂时的唯一序号
     */
    @ApiModelProperty(value="物资序列号;出厂时的唯一序号")
    private String seriesNumber;
    /**
     * sign_status
     * 签收状态
     */
    @ApiModelProperty(value="签收状态")
    private Integer signStatus;
    /**
     * business_id
     * 对应业务id
     * 派发id、或其他业务id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="对应业务id")
    private Long businessId;
    /**
     * business_assets_id
     * 对应业务明细id
     * 派发明细id、或其他业务明细id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="对应业务明细id")
    private Long businessAssetsId;
    /**
     * business_detail_id
     * 对应业务详情id
     * 派发明细id、或其他业务明细id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="对应业务详情id")
    private Long businessDetailId;
    /**
     * source_id
     * 来源id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="来源id")
    private Long sourceId;
    /**
     * source_code
     * 来源code
     */
    @ApiModelProperty(value="来源code")
    private String sourceCode;
    /**
     * source_assets_id
     * 来源明细id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="来源id")
    private Long sourceAssetsId;

}
