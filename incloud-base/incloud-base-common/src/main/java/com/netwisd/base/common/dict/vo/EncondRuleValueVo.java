package com.netwisd.base.common.dict.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 编码规则值 功能描述...
 * @author 云数网讯 zhanghongbin@netwisd.com
 * @date 2022-03-07 17:41:29
 */
@Data
@ApiModel(value = "编码规则值 Vo")
public class EncondRuleValueVo extends IVo{

    /**
     * rule_id
     * 编码规则ID
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="编码规则ID")
    private Long ruleId;

    /**
     * form_name
     * 表单名称
     */
    @ApiModelProperty(value="表单名称")
    private String formName;

    /**
     * ruleType
     * 规则类型
     */
    @ApiModelProperty(value="规则类型")
    private String ruleType;
    /**
     * rule_name
     * 编码规则名称
     */
    
    @ApiModelProperty(value="编码规则名称")
    private String ruleName;
    /**
     * value
     * 编码值
     */
    
    @ApiModelProperty(value="编码值")
    private String value;
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
     * camunda_procdef_name
     * 流程实例名称
     */
    @ApiModelProperty(value="流程实例名称")
    private String camundaProcdefName;
}
