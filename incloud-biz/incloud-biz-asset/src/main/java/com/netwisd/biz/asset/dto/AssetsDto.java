package com.netwisd.biz.asset.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.constants.Args;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.db.anntation.Map;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
/**
 * @Description 资产台账 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-05-30 19:08:39
 */
@Data
@Map("incloud_biz_asset_assets")
@ApiModel(value = "资产台账 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AssetsDto extends IDto{

    public AssetsDto(Args args){
        super(args);
    }
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
    @ApiModelProperty(value="组织全路径ID")
    private String assetOrgFullId;
    /**
     * reason
     * 事由
     */
    @ApiModelProperty(value="事由")
    private String reason;
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
     * assets_number
     * 总数量
     */
    @ApiModelProperty(value="总数量")
    private BigDecimal assetsNumber;
    /**
     * delivery_number
     * 出库数量
     */
    @ApiModelProperty(value="出库数量")
    private BigDecimal deliveryNumber;
    /**
     * accept_number
     * 领用数量
     */
    @ApiModelProperty(value="领用数量")
    private BigDecimal acceptNumber;
    /**
     * stock_number
     * 库存数量
     */
    @ApiModelProperty(value="库存数量")
    private BigDecimal stockNumber;
    /**
     * repair_number
     * 维修数量
     */
    @ApiModelProperty(value="维修数量")
    private BigDecimal repairNumber;
    /**
     * scrapped_number
     * 报废数量
     */
    @ApiModelProperty(value="报废数量")
    private BigDecimal scrappedNumber;
    /**
     * transfer_number
     * 调拨数量
     */
    @ApiModelProperty(value="调拨数量")
    private BigDecimal transferNumber;
    /**
     * borrow_number
     * 借入数量
     */
    @ApiModelProperty(value="借入数量")
    private BigDecimal borrowNumber;
    /**
     * storage_number
     * 入库数量
     */
    @ApiModelProperty(value="入库数量")
    private BigDecimal storageNumber;
    /**
     * acceptance_number
     * 验收数量
     */
    @ApiModelProperty(value="验收数量")
    private BigDecimal acceptanceNumber;
    /**
     * usable_number
     * 可用数量
     */
    @ApiModelProperty(value="可用数量")
    private BigDecimal usableNumber;
    /**
     * entry_number
     * 入账数量
     */
    @ApiModelProperty(value="入账数量")
    private BigDecimal entryNumber;
    /**
     * lend_number
     * 借出数量
     */
    @ApiModelProperty(value="借出数量")
    private BigDecimal lendNumber;
    /**
     * item_type
     * 物资类型
     */
    @ApiModelProperty(value="物资类型")
    private String itemType;
    /**
     * source_id
     * 登记/验收id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="登记/验收id")
    private Long sourceId;
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
     * asset_source
     * 物资来源
     */
    @ApiModelProperty(value="物资来源")
    private Integer assetSource;
    /**
     * register_user_id
     * 采购登记人
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="采购登记人")
    private Long registerUserId;
    /**
     * register_user_name
     * 采购登记人
     */
    @ApiModelProperty(value="采购登记人")
    private String registerUserName;
    /**
     * accept_user_id
     * 验收人
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="验收人")
    private Long acceptUserId;
    /**
     * accept_user_name
     * 申请人部门名称
     */
    @ApiModelProperty(value="验收人")
    private String acceptUserName;

    /**
     * use_user_org_id
     * 使用机构ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="使用机构ID")
    private Long useUserOrgId;
    /**
     * use_user_org_name
     * 使用机构名称
     */
    @ApiModelProperty(value="使用机构名称")
    private String useUserOrgName;
    /**
     * use_user_dept_id
     * 使用部门ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="使用部门ID")
    private Long useUserDeptId;
    /**
     * use_user_dept_name
     * 使用部门名称
     */
    @ApiModelProperty(value="使用部门名称")
    private String useUserDeptName;


    @ApiModelProperty(value="assetsDetailList")
    private List<AssetsDetailDto> assetsDetailList;
    @ApiModelProperty(value="assetsSkuList")
    private List<AssetsSkuDto> assetsSkuList;
    /**
     * classify_type_code
     * 资产分类编码
     */
    @ApiModelProperty(value="资产类型编码")
    private String classifyTypeCode;
    /**
     * classify_type_name
     * 资产分类名称
     */
    @ApiModelProperty(value="资产分类名称")
    private String classifyTypeName;
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
