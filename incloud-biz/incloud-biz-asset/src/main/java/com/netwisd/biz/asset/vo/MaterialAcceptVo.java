package com.netwisd.biz.asset.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netwisd.base.common.center.dto.TodoDto;
import com.netwisd.base.common.center.vo.TodoVo;
import com.netwisd.base.wf.starter.vo.WfVo;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import com.netwisd.biz.asset.vo.MaterialAcceptDetailVo;
import com.netwisd.biz.asset.vo.MaterialAcceptAssetsVo;
/**
 * @Description 资产领用 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:45:59
 */
@Data
@ApiModel(value = "资产领用 Vo")
public class MaterialAcceptVo extends WfVo {

    /**
     * code
     * 编号
     */
    @ApiModelProperty(value="编号")
    private String code;
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
     * explanation
     * 说明
     */
    @ApiModelProperty(value="说明")
    private String explanation;

    /**
     * task_inst_id
     * 任务实例Id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="任务实例Id")
    private Long taskInstId;
    /**
     * distribute_number
     * 派发数量
     */
    @ApiModelProperty(value="派发数量")
    private BigDecimal distributeNumber;
    /**
     * not_distribute_number
     * 未派发数量
     */
    @ApiModelProperty(value="派发数量")
    private BigDecimal notDistributeNumber;
    /**
     * sign_number
     * 签收数量
     */
    @ApiModelProperty(value="签收数量")
    private BigDecimal signNumber;
    /**
     * not_sign_number
     * 未签收数量
     */
    @ApiModelProperty(value="未签收数量")
    private BigDecimal notSignNumber;
    /**
     * asset_user_id
     * 资产所属人
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="资产所属人")
    private Long assetUserId;
    /**
     * asset_user_name
     * 资产所属人名称
     */
    @ApiModelProperty(value="资产所属人名称")
    private String assetUserName;
    /**
     * asset_dept_id
     * 使用人单位
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="使用人部门")
    private Long assetDeptId;
    /**
     * asset_dept_name
     * 使用人单位
     */
    @ApiModelProperty(value="使用人部门")
    private String assetDeptName;
    /**
     * asset_org_id
     * 使用人单位
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="使用人单位")
    private Long assetOrgId;
    /**
     * asset_org_name
     * 使用人单位
     */
    @ApiModelProperty(value="使用人单位")
    private String assetOrgName;



    /**
     * parent_task_node_id
     * 父任务节点Id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="父任务节点Id")
    private Long parentTaskNodeId;

    /**
     * task_id
     * 当前任务节点id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="当前任务节点id")
    private Long taskId;

    @ApiModelProperty(value="materialAcceptDetailList")
    private List<MaterialAcceptDetailVo> materialAcceptDetailList;
    @ApiModelProperty(value="materialAcceptAssetsList")
    private List<MaterialAcceptAssetsVo> materialAcceptAssetsList;

    public TodoDto getTodo(){
        TodoDto todoDto=new TodoDto();
        todoDto.setSysCode("asset");
        todoDto.setSysName("物资");
        todoDto.setTaskSource("领用");
        todoDto.setTaskStartUserid(this.getApplyUserId());
        todoDto.setTaskStartUserName(this.getApplyUserName());
        todoDto.setTaskName("领用");
        todoDto.setTaskTodoPersonType(1);
        todoDto.setTaskTodoTypeId(0l);
        todoDto.setApplyCode(this.getCode());
        todoDto.setApplyWfId(this.getCamundaTaskId());
        todoDto.setApplyReason(this.getReason());
        return todoDto;
    }
}
