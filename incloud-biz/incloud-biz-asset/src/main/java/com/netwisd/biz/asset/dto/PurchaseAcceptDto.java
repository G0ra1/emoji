package com.netwisd.biz.asset.dto;

import com.netwisd.base.wf.starter.dto.WfDto;
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
import com.netwisd.biz.asset.dto.PurchaseAcceptDetailDto;
import com.netwisd.biz.asset.dto.PurchaseAcceptAssetDto;
/**
 * @Description 物资购置验收 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-27 18:45:29
 */
@Data
@Map("incloud_biz_asset_purchase_accept")
@ApiModel(value = "物资购置验收 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PurchaseAcceptDto extends WfDto {

    public PurchaseAcceptDto(Args args){
        super(args);
    }
    /**
     * code
     * 编号
     */
    @ApiModelProperty(value="编号")
    private String code;
    /**
     * acceptance_type
     * 验收类型
     */
    @ApiModelProperty(value="验收类型")
    private Integer acceptanceType;
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
     * explanation
     * 说明
     */
    @ApiModelProperty(value="说明")
    private String explanation;
    /**
     * sum_total_amount
     * 合计金额
     */
    @ApiModelProperty(value="合计金额")
    private BigDecimal sumTotalAmount;
    /**
     * sum_total_untaxed_amount
     * 合计金额-未税
     */
    @ApiModelProperty(value="合计金额-未税")
    private BigDecimal sumTotalUntaxedAmount;
    /**
     * sum_total_tax_amount
     * 合计金额-税额
     */
    @ApiModelProperty(value="合计金额-税额")
    private BigDecimal sumTotalTaxAmount;
    /**
     * sum_total_number
     * 合计数量
     */
    @ApiModelProperty(value="合计数量")
    private BigDecimal sumTotalNumber;
    /**
     * register_code
     * 采购登记编号
     */
    @ApiModelProperty(value="采购登记编号")
    private String registerCode;
    /**
     * register_id
     * 采购登记Id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="采购登记Id")
    private Long registerId;
    /**
     * audit_success_tiem
     * 审批通过时间
     */
    @ApiModelProperty(value="审批通过时间")
    private LocalDateTime auditSuccessTiem;
    /**
     * purchase_type
     * 物资购置类型
     */
    @ApiModelProperty(value="物资购置类型")
    private Integer purchaseType;
    /**
     * asset_source
     * 物资来源
     */
    @ApiModelProperty(value="物资来源")
    private Integer assetSource;
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
     * contract_id
     * 采购合同id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="采购合同id")
    private Long contractId;
    /**
     * contract_code
     * 采购合同code
     */
    @ApiModelProperty(value="采购合同code")
    private String contractCode;
    /**
     * contract_time
     * 采购合同签订时间
     */
    @ApiModelProperty(value="采购合同签订时间")
    private LocalDateTime contractTime;
    /**
     * contract_file_ids
     * 采购合同附件ids
     */
    @ApiModelProperty(value="采购合同附件ids")
    private String contractFileIds;
    /**
     * contract_file_name
     * 采购合同附件名称
     */
    @ApiModelProperty(value="采购合同附件名称")
    private String contractFileName;

    /**
     * file_ids
     * 附件ids
     */
    @ApiModelProperty(value="附件ids")
    private String fileIds;
    /**
     * status
     * 入库状态
     */
    @ApiModelProperty(value="入库状态")
    private Integer status;

    /**
     * storage_number
     * 入库数量
     */
    @ApiModelProperty(value="入库数量")
    private BigDecimal storageNumber;

    /**
     * not_storage_number
     * 未入库数量
     */
    @ApiModelProperty(value="未入库数量")
    private BigDecimal notStorageNumber;
    /**
     * not_storage_amount
     * 未入库金额
     */
    @ApiModelProperty(value="未入库金额")
    private BigDecimal notStorageAmount;


    @ApiModelProperty(value="购置验收明细")
    private List<PurchaseAcceptAssetDto> assetsList;

    @ApiModelProperty(value="购置验收详情")
    private List<PurchaseAcceptDetailDto> detailList;

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
