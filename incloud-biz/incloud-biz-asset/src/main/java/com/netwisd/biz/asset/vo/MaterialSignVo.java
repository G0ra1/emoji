package com.netwisd.biz.asset.vo;

import com.netwisd.base.wf.starter.vo.WfVo;
import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import com.netwisd.biz.asset.vo.MaterialSignDetailVo;
/**
 * @Description 签收 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-06-24 11:09:16
 */
@Data
@ApiModel(value = "签收 Vo")
public class MaterialSignVo extends WfVo{

    /**
     * code
     * 编号
     */
    @ApiModelProperty(value="编号")
    private String code;
    /**
     * sign_type
     * 签收类型;派发签收、
     */
    @ApiModelProperty(value="签收类型;派发签收、")
    private Integer signType;
    /**
     * task_id
     * 任务id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="任务id")
    private Long taskId;
    /**
     * business_id
     * 对应业务id;派发id、或其他业务id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="对应业务id;派发id、或其他业务id")
    private Long businessId;
    /**
     * sign_user_id
     * 签收人id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="签收人id")
    private Long signUserId;
    /**
     * sign_user_name
     * 签收人姓名
     */
    @ApiModelProperty(value="签收人姓名")
    private String signUserName;
    /**
     * sign_user_parent_org_id
     * 签收人父级机构id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="签收人父级机构id")
    private Long signUserParentOrgId;
    /**
     * sign_user_parent_org_name
     * 签收人父级机构名称
     */
    @ApiModelProperty(value="签收人父级机构名称")
    private String signUserParentOrgName;
    /**
     * sign_user_parent_dept_id
     * 签收人父级部门id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="签收人父级部门id")
    private Long signUserParentDeptId;
    /**
     * sign_user_parent_dept_name
     * 签收人父级部门名称
     */
    @ApiModelProperty(value="签收人父级部门名称")
    private String signUserParentDeptName;
    /**
     * sign_user_org_full_id
     * 签收人父级组织全路径id
     */
    @ApiModelProperty(value="签收人父级组织全路径id")
    private String signUserOrgFullId;
    /**
     * sign_time
     * 签收时间
     */
    @ApiModelProperty(value="签收时间")
    private LocalDateTime signTime;
    /**
     * file_ids
     * 附件ids
     */
    @ApiModelProperty(value="附件ids")
    private String fileIds;
    /**
     * create_user_id
     * 创建人ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
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
    @JsonSerialize(using = IdTypeSerializer.class)
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
    @JsonSerialize(using = IdTypeSerializer.class)
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
     * audit_success_tiem
     * 审批通过时间
     */
    @ApiModelProperty(value="审批通过时间")
    private LocalDateTime auditSuccessTiem;
    /**
     * camunda_procdef_key
     * camunda流程定义key
     */
    @ApiModelProperty(value="camunda流程定义key")
    private String camundaProcdefKey;
    /**
     * camunda_procdef_id
     * camunda流程定义ID
     */
    @ApiModelProperty(value="camunda流程定义ID")
    private String camundaProcdefId;
    /**
     * camunda_procins_id
     * camunda流程实例ID
     */
    @ApiModelProperty(value="camunda流程实例ID")
    private String camundaProcinsId;
    /**
     * procdef_type_id
     * 流程分类ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="流程分类ID")
    private Long procdefTypeId;
    /**
     * procdef_type_name
     * 流程分类名称
     */
    @ApiModelProperty(value="流程分类名称")
    private String procdefTypeName;
    /**
     * procdef_version
     * 流程版本
     */
    @ApiModelProperty(value="流程版本")
    private Integer procdefVersion;
    /**
     * procdef_name
     * 流程定义名称
     */
    @ApiModelProperty(value="流程定义名称")
    private String procdefName;
    /**
     * process_name
     * 流程实例名称
     */
    @ApiModelProperty(value="流程实例名称")
    private String processName;
    /**
     * reason
     * 事由
     */
    @ApiModelProperty(value="事由")
    private String reason;
    /**
     * biz_key
     * 工作流—业务key
     */
    @ApiModelProperty(value="工作流—业务key")
    private String bizKey;
    /**
     * camunda_task_id
     * camunda流程任务ID
     */
    @ApiModelProperty(value="camunda流程任务ID")
    private String camundaTaskId;
    /**
     * biz_priority
     * 任务优先级
     */
    @ApiModelProperty(value="任务优先级")
    private String bizPriority;
    /**
     * audit_status
     * 审批状态
     */
    @ApiModelProperty(value="审批状态")
    private Integer auditStatus;
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
     * distribute_type
     * 派发类型
     */
    @ApiModelProperty(value="派发类型")
    private Integer distributeType;
    /**
     * source_id
     * 来源id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="来源id")
    private Long sourceId;
    /**
     * source_code
     * 来源code
     */
    @ApiModelProperty(value="来源code")
    private String sourceCode;
    /**
     * explanation
     * 说明
     */
    @ApiModelProperty(value="说明")
    private String explanation;

    /**
     * sum_total_number
     * 合计数量
     */
    @ApiModelProperty(value="合计数量")
    private BigDecimal sumTotalNumber;

    @ApiModelProperty(value="detailList")
    private List<MaterialSignDetailVo> detailList;

}
