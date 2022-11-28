package com.netwisd.biz.mdm.dto;

import com.netwisd.common.core.constants.Args;
import com.netwisd.base.common.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * @Description 执行范围 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-02 14:38:36
 */
@Data
@ApiModel(value = "执行范围 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ContractPurchaseExecutionDto extends IDto{

    public ContractPurchaseExecutionDto(Args args){
        super(args);
    }

    /**
     * contract_id
     * 合同id
     */
    @ApiModelProperty(value="合同id")
    
    private String contractId;

    /**
     * contract_code
     * 合同编号
     */
    @ApiModelProperty(value="合同编号")
    
    private String contractCode;

    /**
     * execution_scope_id
     * 执行范围（机构id）
     */
    @ApiModelProperty(value="执行范围（机构id）")
    
    private String executionScopeId;

    /**
     * execution_scope_code
     * 执行范围（机构name）
     */
    @ApiModelProperty(value="执行范围（机构name）")
    
    private String executionScopeCode;

    /**
     * execution_scope_name
     * 执行范围（机构code）
     */
    @ApiModelProperty(value="执行范围（机构code）")
    
    private String executionScopeName;

}
