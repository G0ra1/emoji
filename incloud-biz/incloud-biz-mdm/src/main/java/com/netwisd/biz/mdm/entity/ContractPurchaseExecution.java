package com.netwisd.biz.mdm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.anntation.Table;
import com.netwisd.common.db.data.DataType;
import com.netwisd.common.db.data.IModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description $执行范围 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-02 14:38:36
 */
@Data
@Table(value = "incloud_biz_mdm_contract_purchase_execution",comment = "执行范围")
@TableName("incloud_biz_mdm_contract_purchase_execution")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "执行范围 Entity")
public class ContractPurchaseExecution extends IModel<ContractPurchaseExecution> {

    /**
     * contract_id
     * 合同id
     */
    @ApiModelProperty(value="合同id")
    @TableField(value="contract_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "合同id")
    private String contractId;
    /**
     * contract_code
     * 合同编号
     */
    @ApiModelProperty(value="合同编号")
    @TableField(value="contract_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "合同编号")
    private String contractCode;
    /**
     * execution_scope_id
     * 执行范围（机构id）
     */
    @ApiModelProperty(value="执行范围（机构id）")
    @TableField(value="execution_scope_id")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "执行范围（机构id）")
    private String executionScopeId;
    /**
     * execution_scope_code
     * 执行范围（机构name）
     */
    @ApiModelProperty(value="执行范围（机构name）")
    @TableField(value="execution_scope_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "执行范围（机构name）")
    private String executionScopeCode;
    /**
     * execution_scope_name
     * 执行范围（机构code）
     */
    @ApiModelProperty(value="执行范围（机构code）")
    @TableField(value="execution_scope_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "执行范围（机构code）")
    private String executionScopeName;
}
