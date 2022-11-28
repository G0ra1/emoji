package com.netwisd.base.dict.entity;

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
    import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description $编码规则 功能描述...
 * @author 云数网讯 zhanghongbin@netwisd.com
 * @date 2022-03-07 17:41:47
 */
@Data
@Table(value = "incloud_base_dict_encond_rule",comment = "编码规则")
@TableName("incloud_base_dict_encond_rule")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "编码规则 Entity")
public class EncondRule extends IModel<EncondRule> {

    /**
     * rule_name
     * 编码规则名称
     */
    @ApiModelProperty(value="编码规则名称")
    @TableField(value="rule_name")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "编码规则名称")
    private String ruleName;
    /**
     * preview
     * 规则预览
     */
    @ApiModelProperty(value="规则预览")
    @TableField(value="preview")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "规则预览")
    private String preview;

    /**
     * encondField
     * 规则值字段
     */
    @ApiModelProperty(value="规则值字段")
    @TableField(value="encond_field")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "规则值字段")
    private String encondField;

    /**
     * formId
     * 表单ID
     */
    @ApiModelProperty(value="表单ID")
    @TableField(value="form_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "表单ID")
    private Long formId;


    /**
     * ruleType
     * 规则类型
     */
    @ApiModelProperty(value="规则类型")
    @TableField(value="rule_type")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "规则类型")
    private String ruleType;

    /**
     * formName
     * 表单名称
     */
    @ApiModelProperty(value="表单名称")
    @TableField(value="form_name")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "表单名称")
    private String formName;

    /**
     * form_name_ch
     * 表单名称
     */
    @ApiModelProperty(value = "表单名称")
    @TableField(value = "form_name_ch")
    @Column(type = DataType.VARCHAR, length = 100, isNull = true, comment = "表单名称")
    private String formNameCh;

    /**
     * createOpportunity
     * 生成时机
     */
    @ApiModelProperty(value="生成时机")
    @TableField(value="create_opportunity")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "生成时机")
    private String createOpportunity;

    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    @TableField(value="remark")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "备注")
    private String remark;
    /**
     * create_user_id
     * 创建人ID
     */
    @ApiModelProperty(value="创建人ID")
    @TableField(value="create_user_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "创建人ID")
    private Long createUserId;
    /**
     * create_user_name
     * 创建人名称
     */
    @ApiModelProperty(value="创建人名称")
    @TableField(value="create_user_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "创建人名称")
    private String createUserName;
    /**
     * create_user_parent_org_id
     * 创建人父级机构ID
     */
    @ApiModelProperty(value="创建人父级机构ID")
    @TableField(value="create_user_parent_org_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "创建人父级机构ID")
    private Long createUserParentOrgId;
    /**
     * create_user_parent_org_name
     * 创建人父级机构名称
     */
    @ApiModelProperty(value="创建人父级机构名称")
    @TableField(value="create_user_parent_org_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "创建人父级机构名称")
    private String createUserParentOrgName;
    /**
     * create_user_parent_dept_id
     * 创建人父级部门ID
     */
    @ApiModelProperty(value="创建人父级部门ID")
    @TableField(value="create_user_parent_dept_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "创建人父级部门ID")
    private Long createUserParentDeptId;
    /**
     * create_user_parent_dept_name
     * 创建人父级部门名称
     */
    @ApiModelProperty(value="创建人父级部门名称")
    @TableField(value="create_user_parent_dept_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "创建人父级部门名称")
    private String createUserParentDeptName;
    /**
     * create_user_org_full_id
     * 创建人父级组织全路径ID
     */
    @ApiModelProperty(value="创建人父级组织全路径ID")
    @TableField(value="create_user_org_full_id")
    @Column(type = DataType.VARCHAR, length = 2000,  isNull = true, comment = "创建人父级组织全路径ID")
    private String createUserOrgFullId;

    /**
     * camunda_procdef_id
     * 流程实例ID
     */
    @ApiModelProperty(value="流程实例ID")
    @TableField(value="camunda_procdef_id")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "流程实例ID")
    private String camundaProcdefId;

    /**
     * camunda_procdef_name
     * 流程实例名称
     */
    @ApiModelProperty(value="流程实例名称")
    @TableField(value="camunda_procdef_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "流程实例名称")
    private String camundaProcdefName;

    /**
     * wf_type
     * 流程版本
     */
    @ApiModelProperty(value="流程版本")
    @TableField(value="wf_type")
    @Column(type = DataType.VARCHAR, length = 10,  isNull = true, comment = "流程版本")
    private String wfType;

}
