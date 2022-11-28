package com.netwisd.base.common.dict.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description 编码规则 功能描述...
 * @author 云数网讯 zhanghongbin@netwisd.com
 * @date 2022-03-07 17:41:47
 */
@Data
@ApiModel(value = "编码规则 Vo")
public class EncondRuleVo extends IVo{

    /**
     * rule_name
     * 编码规则名称
     */
    @ApiModelProperty(value="编码规则名称")
    private String ruleName;
    /**
     * preview
     * 规则预览
     */
    @ApiModelProperty(value="规则预览")
    private String preview;

    /**
     * ruleType
     * 规则类型
     */
    @ApiModelProperty(value="规则类型")
    private String ruleType;

    /**
     * formName
     * 表单名称
     */
    @ApiModelProperty(value="表单名称")
    private String formName;

    /**
     * encondField
     * 规则值字段
     */
    @ApiModelProperty(value="规则值字段")
    private String encondField;

    /**
     * form_name_ch
     * 表单名称
     */
    @ApiModelProperty(value = "表单名称")
    private String formNameCh;

    /**
     * form_id
     * 表单ID
     */
    @ApiModelProperty(value = "表单ID")
    private Long formId;

    /**
     * createOpportunity
     * 生成时机
     */
    @ApiModelProperty(value="生成时机")
    private String createOpportunity;
    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
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

    @ApiModelProperty(value="encondRuleDetailList")
    private List<EncondRuleDetailVo> encondRuleDetailList;

    /**
     * camunda_procdef_id
     * 流程实例ID
     */
    @ApiModelProperty(value="流程实例ID")
    private String camundaProcdefId;

    /**
     * camunda_procdef_name
     * 创建人父级部门名称
     */
    @ApiModelProperty(value="流程实例ID")
    private String camundaProcdefName;

    /**
     * wf_type
     * 流程版本
     */
    @ApiModelProperty(value="流程版本")
    private String wfType;

}
