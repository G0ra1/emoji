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
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @Description 验收详情 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-06-14 10:39:29
 */
@Data
@Map("incloud_biz_asset_purchase_accept_detail")
@ApiModel(value = "验收详情 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PurchaseAcceptDetailDto extends IDto{

    public PurchaseAcceptDetailDto(Args args){
        super(args);
    }
    /**
     * acceptance_id
     * 验收id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Fk(table = "incloud_biz_asset_purchase_accept" ,field = "id")
    @ApiModelProperty(value="验收id")
    private Long acceptanceId;
    /**
     * acceptance_code
     * 验收编码
     */
    @ApiModelProperty(value="验收编码")
    private String acceptanceCode;
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
     * warehouse_id
     * 仓库地点
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="仓库地点")
    private Long warehouseId;
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
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="货架号")
    private Long shelfId;
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
     * shelf_name
     * 货架号
     */
    @ApiModelProperty(value="货架号")
    private String shelfName;
    /**
     * reception_date
     * 接收日期
     */
    @ApiModelProperty(value="接收日期")
    private LocalDateTime receptionDate;
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
     * acceptance_assets_id
     * 验收明细Id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="验收明细Id")
    private Long acceptanceAssetsId;
    /**
     * acceptance_tax_rate
     * 验收税率
     */
    @ApiModelProperty(value="验收税率")
    private BigDecimal acceptanceTaxRate;
    /**
     * acceptance_amount
     * 验收单价
     */
    @ApiModelProperty(value="验收单价")
    private BigDecimal acceptanceAmount;
    /**
     * acceptance_untaxed_amount
     * 验收单价-未税
     */
    @ApiModelProperty(value="验收单价-未税")
    private BigDecimal acceptanceUntaxedAmount;
    /**
     * acceptance_tax_amount
     * 验收单价-税额
     */
    @ApiModelProperty(value="验收单价-税额")
    private BigDecimal acceptanceTaxAmount;
    /**
     * acceptance_sum_amount
     * 验收总价
     */
    @ApiModelProperty(value="验收总价")
    private BigDecimal acceptanceSumAmount;
    /**
     * acceptance_sum_untaxed_amount
     * 验收总价-未税
     */
    @ApiModelProperty(value="验收总价-未税")
    private BigDecimal acceptanceSumUntaxedAmount;
    /**
     * acceptance_sum_tax_amount
     * 验收总价-税额
     */
    @ApiModelProperty(value="验收总价-税额")
    private BigDecimal acceptanceSumTaxAmount;
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
     * register_result_id
     * 采购结果id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="采购结果id")
    private Long registerResultId;
    /**
     * source_id
     * 资产详情id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="资产详情id")
    private Long sourceId;
    /**
     * item_id
     * 物资Id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="物资Id")
    private Long itemId;
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
     * exterior_check
     * 外观检查
     */
    @ApiModelProperty(value="外观检查")
    private String exteriorCheck;
    /**
     * quality_acceptance
     * 质量验收
     */
    @ApiModelProperty(value="质量验收")
    private String qualityAcceptance;
    /**
     * document
     * 文件资料
     */
    @ApiModelProperty(value="文件资料")
    private String document;
    /**
     * acceptance_status
     * 验收状态
     */
    @ApiModelProperty(value="验收状态")
    private Integer acceptanceStatus;
    /**
     * acceptanceBatch
     * 验收批次号
     */
    @ApiModelProperty(value="验收批次号")
    private String acceptanceBatch;
    /**
     * file_ids
     * 附件ids
     */
    @ApiModelProperty(value="附件ids")
    private String fileIds;
}
