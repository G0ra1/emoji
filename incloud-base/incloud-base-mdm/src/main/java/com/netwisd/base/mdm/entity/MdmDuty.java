package com.netwisd.base.mdm.entity;

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
 * @Description $职务 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-09-27 10:45:35
 */
@Data
@Table(value = "incloud_base_mdm_duty",comment = "职务")
@TableName("incloud_base_mdm_duty")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "职务 Entity")
public class MdmDuty extends IModel<MdmDuty> {


    /**
     * parent_dept_id
     * 所属部门ID
     */
    @ApiModelProperty(value="所属部门ID")
    @TableField(value="parent_dept_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "所属部门ID")
    private Long parentDeptId;
    /**
     * parent_dept_name
     * 所属部门名称
     */
    @ApiModelProperty(value="所属部门名称")
    @TableField(value="parent_dept_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "所属部门名称")
    private String parentDeptName;
    /**
     * parent_org_id
     * 所属机构ID
     */
    @ApiModelProperty(value="所属机构ID")
    @TableField(value="parent_org_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "所属机构ID")
    private Long parentOrgId;
    /**
     * parent_org_name
     * 所属机构名称
     */
    @ApiModelProperty(value="所属机构名称")
    @TableField(value="parent_org_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "所属机构名称")
    private String parentOrgName;
    /**
     * parent_org_full_name
     * 父级组织全路径名称
     */
    @ApiModelProperty(value="父级组织全路径名称")
    @TableField(value="parent_org_full_name")
    @Column(type = DataType.VARCHAR, length = 2000,  isNull = true, comment = "父级组织全路径名称")
    private String parentOrgFullName;
    /**
     * parent_dept_full_name
     * 父级部门全路径名称
     */
    @ApiModelProperty(value="父级部门全路径名称")
    @TableField(value="parent_dept_full_name")
    @Column(type = DataType.VARCHAR, length = 2000,  isNull = true, comment = "父级部门全路径名称")
    private String parentDeptFullName;
    /**
     * org_full_id
     * 父级组织全路径ID
     */
    @ApiModelProperty(value="父级组织全路径ID")
    @TableField(value="org_full_id")
    @Column(type = DataType.VARCHAR, length = 4000,  isNull = false, comment = "父级组织全路径ID")
    private String orgFullId;
    /**
     * org_full_name
     * 父级组织全路径名称
     */
    @ApiModelProperty(value="父级组织全路径名称")
    @TableField(value="org_full_name")
    @Column(type = DataType.VARCHAR, length = 4000,  isNull = false, comment = "父级组织全路径名称")
    private String orgFullName;
    /**
     * duty_name
     * 职务名称
     */
    @ApiModelProperty(value="职务名称")
    @TableField(value="duty_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "职务名称")
    private String dutyName;
    /**
     * duty_code
     * 职务编号
     */
    @ApiModelProperty(value="职务编码")
    @TableField(value="duty_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "职务编码")
    private String dutyCode;
    /**
     * duty_up_parent_id
     * 上级职务部门id
     */
    @ApiModelProperty(value="上级职务部门id")
    @TableField(value="duty_up_parent_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "上级职务部门id")
    private Long dutyUpParentId;
    /**
     * duty_up_parent_name
     * 上级职务部门名称
     */
    @ApiModelProperty(value="上级职务部门名称")
    @TableField(value="duty_up_parent_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "上级职务部门名称")
    private String dutyUpParentName;
    /**
     * duty_up_id
     * 上级职务id
     */
    @ApiModelProperty(value="上级职务id")
    @TableField(value="duty_up_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "上级职务id")
    private Long dutyUpId;
    /**
     * duty_up_name
     * 上级职务
     */
    @ApiModelProperty(value="上级职务")
    @TableField(value="duty_up_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "上级职务")
    private String dutyUpName;
    /**
     * duty_low_parent_id
     * 下级职务部门id
     */
    @ApiModelProperty(value="下级职务部门id")
    @TableField(value="duty_low_parent_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "下级职务部门id")
    private Long dutyLowParentId;
    /**
     * duty_low_parent_name
     * 下级职务部门名称
     */
    @ApiModelProperty(value="下级职务部门名称")
    @TableField(value="duty_low_parent_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "下级职务部门名称")
    private String dutyLowParentName;
    /**
     * duty_low_id
     * 下级职务id
     */
    @ApiModelProperty(value="下级职务id")
    @TableField(value="duty_low_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "下级职务id")
    private Long dutyLowId;
    /**
     * duty_low_name
     * 下级职务
     */
    @ApiModelProperty(value="下级职务")
    @TableField(value="duty_low_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "下级职务")
    private String dutyLowName;
    /**
     * duty_sequ_id
     * 职务序列id
     */
    @ApiModelProperty(value="职务序列id")
    @TableField(value="duty_sequ_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "职务序列id")
    private Long dutySequId;
    /**
     * duty_sequ_name
     * 职务序列
     */
    @ApiModelProperty(value="职务序列")
    @TableField(value="duty_sequ_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "职务序列")
    private String dutySequName;
    /**
     * duty_grade_id
     * 职务职等id
     */
    @ApiModelProperty(value="职务职等id")
    @TableField(value="duty_grade_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "职务职等id")
    private Long dutyGradeId;
    /**
     * duty_grade_name
     * 职务职等
     */
    @ApiModelProperty(value="职务职等")
    @TableField(value="duty_grade_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "职务职等")
    private String dutyGradeName;
    /**
     * duty_tag_id
     * 职务标识id
     */
    @ApiModelProperty(value="职务标识id")
    @TableField(value="duty_tag_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "职务标识id")
    private Long dutyTagId;
    /**
     * duty_tag_name
     * 职务标识
     */
    @ApiModelProperty(value="职务标识")
    @TableField(value="duty_tag_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "职务标识")
    private String dutyTagName;

    /**
     * duty_duty
     * 职务职责
     */
    @ApiModelProperty(value="职务职责")
    @TableField(value="duty_duty")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "职务职责")
    private String dutyDuty;
    /**
     * duty_ability
     * 职务胜任能力
     */
    @ApiModelProperty(value="职务胜任能力")
    @TableField(value="duty_ability")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "职务胜任能力")
    private String dutyAbility;
    /**
     * duty_content
     * 职务工作内容
     */
    @ApiModelProperty(value="职务工作内容")
    @TableField(value="duty_content")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "职务工作内容")
    private String dutyContent;
    /**
     * duty_check
     * 职务考核标准
     */
    @ApiModelProperty(value="职务考核标准")
    @TableField(value="duty_check")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "职务考核标准")
    private String dutyCheck;
    /**
     * sort
     * 排序字段
     */
    @ApiModelProperty(value="排序字段")
    @TableField(value="sort")
    @Column(type = DataType.INT, length = 11,  isNull = false, comment = "排序字段")
    private Integer sort;
    /**
     * is_ref
     * 是否被引用
     */
    @ApiModelProperty(value="是否被引用")
    @TableField(value="is_ref")
    @Column(type = DataType.INT, length = 1,  isNull = false, comment = "是否被引用")
    private Integer isRef;
    /**
     * status
     * 状态标识
     */
    @ApiModelProperty(value="状态标识")
    @TableField(value="status")
    @Column(type = DataType.INT, length = 2,  isNull = false, comment = "状态标识")
    private Integer status;

}
