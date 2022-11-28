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
 * @Description 资产变更详情 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-08-03 09:56:31
 */
@Data
@Map("incloud_biz_asset_material_change_detail")
@ApiModel(value = "资产变更详情 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MaterialChangeDetailDto extends IDto{

    public MaterialChangeDetailDto(Args args){
        super(args);
    }
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
     * tax_rate
     * 税率
     */
    @ApiModelProperty(value="税率")
    private BigDecimal taxRate;
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
     * bill_codes
     * 发票号
     */
    @ApiModelProperty(value="发票号")
    private String billCodes;
    /**
     * bill_code_files_ids
     * 发票附件
     */
    @ApiModelProperty(value="发票附件")
    private String billCodeFilesIds;
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
     * rz_date
     * 入账日期
     */
    @ApiModelProperty(value="入账日期")
    private LocalDateTime rzDate;
    /**
     * assets_number
     * 总数量/领用数量
     */
    @ApiModelProperty(value="总数量/领用数量")
    private BigDecimal assetsNumber;
    /**
     * assets_amount
     * 物资原值单价
     */
    @ApiModelProperty(value="物资原值单价")
    private BigDecimal assetsAmount;
    /**
     * assets_untaxed_amount
     * 物资原值单价-未税
     */
    @ApiModelProperty(value="物资原值单价-未税")
    private BigDecimal assetsUntaxedAmount;
    /**
     * assets_tax_amount
     * 物资原值税额
     */
    @ApiModelProperty(value="物资原值税额")
    private BigDecimal assetsTaxAmount;
    /**
     * assets_sum_amount
     * 物资原值总价
     */
    @ApiModelProperty(value="物资原值总价")
    private BigDecimal assetsSumAmount;
    /**
     * assets_sum_untaxed_amount
     * 物资原值总价-未税
     */
    @ApiModelProperty(value="物资原值总价-未税")
    private BigDecimal assetsSumUntaxedAmount;
    /**
     * assets_sum_tax_amount
     * 物资原值总税额
     */
    @ApiModelProperty(value="物资原值总税额")
    private BigDecimal assetsSumTaxAmount;
    /**
     * net_rate
     * 净值率
     */
    @ApiModelProperty(value="净值率")
    private BigDecimal netRate;
    /**
     * estlimate_net_salvage
     * 预计净残值
     */
    @ApiModelProperty(value="预计净残值")
    private BigDecimal estlimateNetSalvage;
    /**
     * secret_related
     * 是否涉密
     */
    @ApiModelProperty(value="是否涉密")
    private Integer secretRelated;
    /**
     * asset_dept_id
     * 资产所属部门
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="资产所属部门")
    private Long assetDeptId;
    /**
     * asset_dept_name
     * 资产所属部门名称
     */
    @ApiModelProperty(value="资产所属部门名称")
    private String assetDeptName;
    /**
     * asset_org_id
     * 资产所属机构id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
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
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="组织全路径ID")
    private Long assetOrgFullId;
    /**
     * item_type
     * 物资类型
     */
    @ApiModelProperty(value="物资类型")
    private String itemType;
    /**
     * asset_source
     * 物资来源
     */
    @ApiModelProperty(value="物资来源")
    private Integer assetSource;
    /**
     * series_number
     * 物资序列号;出厂时的唯一序号
     */
    @ApiModelProperty(value="物资序列号;出厂时的唯一序号")
    private String seriesNumber;
    /**
     * type
     * 类型;0-历史数据，1-表单数据
     */
    @ApiModelProperty(value="类型;0-历史数据，1-表单数据")
    private Integer type;
    /**
     * change_id
     * 资产变更id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Fk(table = "incloud_biz_asset_material_change" ,field = "id")
    @ApiModelProperty(value="资产变更id")
    private Long changeId;
    /**
     * assets_detail_id
     * 资产明细id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="资产明细id")
    private Long assetsDetailId;
    /**
     * item_id
     * 物资id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="物资id")
    private Long itemId;
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
     * apply_time
     * 申请时间
     */
    @ApiModelProperty(value="申请时间")
    private LocalDateTime applyTime;
    /**
     * apply_user_id
     * 申请人ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
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
    @JsonDeserialize(using = IdTypeDeserializer.class)
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
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="申请人部门ID")
    private Long applyUserDeptId;
    /**
     * apply_user_dept_name
     * 申请人部门名称
     */
    @ApiModelProperty(value="申请人部门名称")
    private String applyUserDeptName;
    /**
     * change_number
     * 变更数量
     */
    @ApiModelProperty(value="变更数量")
    private BigDecimal changeNumber;
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
     * status
     * 物资状态
     */
    @ApiModelProperty(value="物资状态")
    private String status;
    /**
     * asset_user_id
     * 资产所属人
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="资产所属人")
    private Long assetUserId;
    /**
     * asset_user_name
     * 资产所属人名称
     */
    @ApiModelProperty(value="资产所属人名称")
    private String assetUserName;

    /**
     * 指定字段模糊查询
     */
    @ApiModelProperty(value="查询条件")
    private String searchCondition;

    public String getSearchCondition() {
        if(searchCondition != null){
            searchCondition = searchCondition.replaceAll("\\\\","\\\\\\\\\\\\\\\\");
        }
        return searchCondition;
    }

    public void setSearchCondition(String searchCondition) {
        this.searchCondition = searchCondition;
    }


}
