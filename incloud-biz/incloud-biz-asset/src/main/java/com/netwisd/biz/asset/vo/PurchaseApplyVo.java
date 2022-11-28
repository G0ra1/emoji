package com.netwisd.biz.asset.vo;

import com.netwisd.base.wf.starter.vo.WfVo;
import com.netwisd.biz.asset.dto.PurchaseApplyDetailDto;
import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 购置申请 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-04-20 10:10:21
 */
@Data
@ApiModel(value = "购置申请 Vo")
public class PurchaseApplyVo extends WfVo {

    /**
     * code
     * 编号
     */
    
    @ApiModelProperty(value="编号")
    private String code;
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
     * item_type
     * 申请类型
     */
    @ApiModelProperty(value="申请类型")
    private String itemType;

    /**
     * item_type_name
     * 申请类型名称
     */
    @ApiModelProperty(value="申请类型名称")
    private String itemTypeName;

    /**
     * status
     * 采购状态
     */
    @ApiModelProperty(value="采购状态")
    private String status;

    /**
     * explanation
     * 说明
     */
    @ApiModelProperty(value="说明")
    private String explanation;

    /**
     * planYear
     * 计划年度
     */
    @ApiModelProperty(value="计划年度")
    private String planYear;
    /**
     * apply_user_id
     * 申请人
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="申请人")
    private Long applyUserId;
    /**
     * apply_user_name
     * 申请人
     */
    @ApiModelProperty(value="申请人")
    private String applyUserName;
    /**
     * apply_user_org_id
     * 申请人机构
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="申请人机构")
    private Long applyUserOrgId;
    /**
     * apply_user_org_name
     * 申请人机构
     */
    @ApiModelProperty(value="申请人机构")
    private String applyUserOrgName;
    /**
     * apply_user_dept_id
     * 申请人部门
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="申请人部门")
    private Long applyUserDeptId;
    /**
     * apply_user_dept_name
     * 申请人部门
     */
    @ApiModelProperty(value="申请人部门")
    private String applyUserDeptName;
    /**
     * apply_time
     * 申请时间
     */
    @ApiModelProperty(value="申请时间")
    private LocalDateTime applyTime;

    /**
     * audit_success_tiem
     * 审批通过时间
     */
    
    @ApiModelProperty(value="审批通过时间")
    private LocalDateTime auditSuccessTiem;

    /**
     * file_ids
     * 附件ids
     */
    @ApiModelProperty(value="附件ids")
    private String fileIds;

    /**
     * not_register_amount
     * 未采购金额
     */
    @ApiModelProperty(value="未采购金额")
    private BigDecimal notRegisterAmount;

    /**
     * register_number
     * 采购数量
     */
    @ApiModelProperty(value="采购数量")
    private BigDecimal registerNumber;
    /**
     * not_register_number
     * 未采购数量
     */
    @ApiModelProperty(value="未采购数量")
    private BigDecimal notRegisterNumber;


    @ApiModelProperty(value="购置申请详情")
    List<PurchaseApplyDetailVo> detailList;

}
