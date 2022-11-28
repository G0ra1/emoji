package com.netwisd.base.common.dict.dto;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netwisd.common.core.util.IdTypeDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


/**
 * @Description 编码规则 功能描述...
 * @author 云数网讯 zhanghongbin@netwisd.com
 * @date 2022-03-07 17:41:47
 */
@Data
@ApiModel(value = "编码规则 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EncondRuleDto {

    /**
     * 主机和进程的机器码
     */
    private static IdentifierGenerator IDENTIFIER_GENERATOR = new DefaultIdentifierGenerator();

    /**
     * id
     * 主键
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="主键"  )
    public Long id;

    /**
     * page对象，用于页面传参，给个默认值
     * 一般传current（默认1），size（默认10）
     */
    @ApiModelProperty( value="page" )
    public Page page;

    /**
     * 修改日期
     */
    @ApiModelProperty( value="update_time" )
    public LocalDateTime updateTime;

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
     * encondField
     * 规则值字段
     */
    @ApiModelProperty(value="规则值字段")
    private String encondField;
    /**
     * create_user_id
     * 创建人ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
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
    @JsonDeserialize(using = IdTypeDeserializer.class)
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
    @JsonDeserialize(using = IdTypeDeserializer.class)
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
    private List<EncondRuleDetailDto> encondRuleDetailList;

    /**
     *
     * 表单字段
     */
    @ApiModelProperty(value="表单字段")
    private Map<String,Object> formMap;


    /**
     * camunda_procdef_id
     * 流程实例ID
     */
    @ApiModelProperty(value="流程实例ID")
    private String camundaProcdefId;

    /**
     * camunda_procdef_name
     * 流程实例名称
     */
    @ApiModelProperty(value="流程实例名称")
    private String camundaProcdefName;

    /**
     * wf_type
     * 流程版本
     */
    @ApiModelProperty(value="流程版本")
    private String wfType;

}
