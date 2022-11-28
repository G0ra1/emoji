package com.netwisd.biz.asset.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.netwisd.base.wf.starter.entitiy.WfEntity;
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
 * @Description $物资购置验收 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-27 18:45:29
 */
@Data
@Table(value = "incloud_biz_asset_purchase_accept",comment = "物资购置验收")
@TableName("incloud_biz_asset_purchase_accept")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "物资购置验收 Entity")
public class PurchaseAccept extends WfEntity<PurchaseAccept> {

    /**
     * code
     * 编号
     */
    @ApiModelProperty(value="编号")
    @TableField(value="code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "编号")
    private String code;
    /**
     * acceptance_type
     * 验收类型
     */
    @ApiModelProperty(value="验收类型")
    @TableField(value="acceptance_type")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "验收类型")
    private Integer acceptanceType;
//    /**
//     * exterior_check
//     * 外观检查
//     */
//    @ApiModelProperty(value="外观检查")
//    @TableField(value="exterior_check")
//    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "外观检查")
//    private String exteriorCheck;
//    /**
//     * quality_acceptance
//     * 质量验收
//     */
//    @ApiModelProperty(value="质量验收")
//    @TableField(value="quality_acceptance")
//    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "质量验收")
//    private String qualityAcceptance;
//    /**
//     * document
//     * 文件资料
//     */
//    @ApiModelProperty(value="文件资料")
//    @TableField(value="document")
//    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "文件资料")
//    private String document;
    /**
     * explanation
     * 说明
     */
    @ApiModelProperty(value="说明")
    @TableField(value="explanation")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "说明")
    private String explanation;
    /**
     * sum_total_amount
     * 合计金额
     */
    @ApiModelProperty(value="合计金额")
    @TableField(value="sum_total_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "合计金额")
    private BigDecimal sumTotalAmount;
    /**
     * sum_total_untaxed_amount
     * 合计金额-未税
     */
    @ApiModelProperty(value="合计金额-未税")
    @TableField(value="sum_total_untaxed_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "合计金额-未税")
    private BigDecimal sumTotalUntaxedAmount;
    /**
     * sum_total_tax_amount
     * 合计金额-税额
     */
    @ApiModelProperty(value="合计金额-税额")
    @TableField(value="sum_total_tax_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "合计金额-税额")
    private BigDecimal sumTotalTaxAmount;
    /**
     * sum_total_number
     * 合计申请数量
     */
    @ApiModelProperty(value="合计申请数量")
    @TableField(value="sum_total_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "合计申请数量")
    private BigDecimal sumTotalNumber;
    /**
     * register_code
     * 采购登记编号
     */
    @ApiModelProperty(value="采购登记编号")
    @TableField(value="register_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "采购登记编号")
    private String registerCode;
    /**
     * register_id
     * 采购登记Id
     */
    @ApiModelProperty(value="采购登记Id")
    @TableField(value="register_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "采购登记Id")
    private Long registerId;
    /**
     * audit_success_tiem
     * 审批通过时间
     */
    @ApiModelProperty(value="审批通过时间")
    @TableField(value="audit_success_tiem")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "审批通过时间")
    private LocalDateTime auditSuccessTiem;
    /**
     * camunda_task_id
     * camunda流程任务ID
     */
    @ApiModelProperty(value="camunda流程任务ID")
    @TableField(value="camunda_task_id")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "camunda流程任务ID")
    private String camundaTaskId;
    /**
     * purchase_type
     * 物资购置类型
     */
    @ApiModelProperty(value="物资购置类型")
    @TableField(value="purchase_type")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "物资购置类型")
    private Integer purchaseType;
    /**
     * asset_source
     * 物资来源
     */
    @ApiModelProperty(value="物资来源")
    @TableField(value="asset_source")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "物资来源")
    private Integer assetSource;
    /**
     * apply_time
     * 申请时间
     */
    @ApiModelProperty(value="申请时间")
    @TableField(value="apply_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "申请时间")
    private LocalDateTime applyTime;
    /**
     * apply_user_id
     * 申请人ID
     */
    @ApiModelProperty(value="申请人ID")
    @TableField(value="apply_user_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "申请人ID")
    private Long applyUserId;
    /**
     * apply_user_name
     * 申请人名称
     */
    @ApiModelProperty(value="申请人名称")
    @TableField(value="apply_user_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "申请人名称")
    private String applyUserName;
    /**
     * apply_user_org_id
     * 申请人机构ID
     */
    @ApiModelProperty(value="申请人机构ID")
    @TableField(value="apply_user_org_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "申请人机构ID")
    private Long applyUserOrgId;
    /**
     * apply_user_org_name
     * 申请人机构名称
     */
    @ApiModelProperty(value="申请人机构名称")
    @TableField(value="apply_user_org_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "申请人机构名称")
    private String applyUserOrgName;
    /**
     * apply_user_dept_id
     * 申请人部门ID
     */
    @ApiModelProperty(value="申请人部门ID")
    @TableField(value="apply_user_dept_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "申请人部门ID")
    private Long applyUserDeptId;
    /**
     * apply_user_dept_name
     * 申请人部门名称
     */
    @ApiModelProperty(value="申请人部门名称")
    @TableField(value="apply_user_dept_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "申请人部门名称")
    private String applyUserDeptName;

    /**
     * file_ids
     * 附件ids
     */
    @ApiModelProperty(value="附件ids")
    @TableField(value="file_ids")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "附件ids")
    private String fileIds;

    /**
     * contract_id
     * 采购合同id
     */
    @ApiModelProperty(value="采购合同id")
    @TableField(value="contract_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "采购合同id")
    private Long contractId;
    /**
     * contract_code
     * 采购合同code
     */
    @ApiModelProperty(value="采购合同code")
    @TableField(value="contract_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "采购合同code")
    private String contractCode;
    /**
     * contract_time
     * 采购合同签订时间
     */
    @ApiModelProperty(value="采购合同签订时间")
    @TableField(value="contract_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "采购合同签订时间")
    private LocalDateTime contractTime;
    /**
     * contract_file_ids
     * 采购合同附件ids
     */
    @ApiModelProperty(value="采购合同附件ids")
    @TableField(value="contract_file_ids")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "采购合同附件ids")
    private String contractFileIds;
    /**
     * status
     * 入库状态
     */
    @ApiModelProperty(value="入库状态")
    @TableField(value="status")
    @Column(type = DataType.INT, length = 1, isNull = true, comment = "验收状态")
    private Integer status;
    /**
     * storage_number
     * 入库数量
     */
    @ApiModelProperty(value="入库数量")
    @TableField(value="storage_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "入库数量")
    private BigDecimal storageNumber;
    /**
     * not_storage_number
     * 未入库数量
     */
    @ApiModelProperty(value="未入库数量")
    @TableField(value="not_storage_number")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2 , isNull = true, comment = "未入库数量")
    private BigDecimal notStorageNumber;
    /**
     * not_storage_amount
     * 未入库金额
     */
    @ApiModelProperty(value="未入库金额")
    @TableField(value="not_storage_amount")
    @Column(type = DataType.DECIMAL, length = 32, precision = 8 , isNull = true, comment = "未入库金额")
    private BigDecimal notStorageAmount;
}
