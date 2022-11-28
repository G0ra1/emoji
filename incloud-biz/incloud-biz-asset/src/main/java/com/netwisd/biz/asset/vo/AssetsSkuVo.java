package com.netwisd.biz.asset.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Description 资产sku信息 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-30 19:08:39
 */
@Data
@ApiModel(value = "资产sku信息 Vo")
public class AssetsSkuVo extends IVo{

    /**
     * assets_id
     * 资产台账id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="资产台账id")
    private Long assetsId;
    /**
     * asset_org_id
     * 资产所属机构id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="资产所属机构id")
    private Long assetOrgId;
    /**
     * asset_org_name
     * 资产所属机构名称
     */
    @ApiModelProperty(value="资产所属机构名称")
    private String assetOrgName;
    /**
     * asset_dept_id
     * 资产所属部门
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="资产所属部门")
    private Long assetDeptId;
    /**
     * asset_dept_name
     * 资产所属部门名称
     */
    @ApiModelProperty(value="资产所属部门名称")
    private String assetDeptName;
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
     * item_id
     * 物资Id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
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
     * acceptance_number
     * 验收数量
     */
    @ApiModelProperty(value="验收数量")
    private BigDecimal acceptanceNumber;
    /**
     * storage_number
     * 入库数量
     */
    @ApiModelProperty(value="入库数量")
    private BigDecimal storageNumber;
    /**
     * stock_number
     * 库存数量
     */
    @ApiModelProperty(value="库存数量")
    private BigDecimal stockNumber;
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
     * borrow_number
     * 借入数量
     */
    @ApiModelProperty(value="借入数量")
    private BigDecimal borrowNumber;
    /**
     * lend_number
     * 借出数量
     */
    @ApiModelProperty(value="借出数量")
    private BigDecimal lendNumber;
    /**
     * transfer_number
     * 调拨数量
     */
    @ApiModelProperty(value="调拨数量")
    private BigDecimal transferNumber;
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
     * item_type
     * 物资类型
     */
    @ApiModelProperty(value="物资类型")
    private String itemType;
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
    @JsonSerialize(using = IdTypeSerializer.class)
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
    @JsonSerialize(using = IdTypeSerializer.class)
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
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="申请人部门ID")
    private Long applyUserDeptId;
    /**
     * apply_user_dept_name
     * 申请人部门名称
     */
    @ApiModelProperty(value="申请人部门名称")
    private String applyUserDeptName;
    /**
     * source_id
     * 登记/验收id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="登记/验收id")
    private Long sourceId;
    /**
     * asset_source
     * 物资来源
     */
    @ApiModelProperty(value="物资来源")
    private Integer assetSource;
    /**
     * sku_codes
     * 子集编码拼接
     */
    @ApiModelProperty(value="子集编码拼接")
    private String skuCodes;
    /**
     * register_user_id
     * 采购登记人
     */
    @JsonSerialize(using = IdTypeSerializer.class)
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
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="验收人")
    private Long acceptUserId;
    /**
     * accept_user_name
     * 验收人
     */
    @ApiModelProperty(value="验收人")
    private String acceptUserName;

    /**
     * use_user_org_id
     * 使用机构ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
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
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="使用部门ID")
    private Long useUserDeptId;
    /**
     * use_user_dept_name
     * 使用部门名称
     */
    @ApiModelProperty(value="使用部门名称")
    private String useUserDeptName;
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

}
