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


/**
 * @Description $岗位 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-25 15:15:37
 */
@Data
@Table(value = "incloud_base_mdm_post",comment = "岗位")
@TableName("incloud_base_mdm_post")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "岗位 Entity")
public class MdmPost extends IModel<MdmPost> {

    /**
     * parentId
     * 所属部门ID
     */
    @ApiModelProperty(value="所属部门ID")
    @TableField(value="parent_dept_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "所属部门ID")
    private Long parentDeptId;
    /**
     * parent_name
     * 所属部门名称
     */
    @ApiModelProperty(value="所属部门名称")
    @TableField(value="parent_dept_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "所属部门名称")
    private String parentDeptName;
    /**
     * parent_org_id
     * 父级ID
     */
    @ApiModelProperty(value="父级机构ID")
    @TableField(value="parent_org_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "所属机构ID")
    private Long parentOrgId;
    /**
     * parent_org_name
     * 父级名称
     */
    @ApiModelProperty(value="父级机构名称")
    @TableField(value="parent_org_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "所属机构名称")
    private String parentOrgName;
    /**
     * parent_org_name
     * 父级组织全路径名称
     */
    @ApiModelProperty(value="父级组织全路径名称")
    @TableField(value="parent_org_full_name")
    @Column(type = DataType.VARCHAR, length = 2000,  isNull = true, comment = "父级组织全路径名称")
    private String parentOrgFullName;
    /**
     * parent_dept_name
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
     * post_name
     * 岗位名称
     */
    @ApiModelProperty(value="岗位名称")
    @TableField(value="post_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "岗位名称")
    private String postName;
    /**
     * post_code
     * 岗位编号
     */
    @ApiModelProperty(value="岗位编号")
    @TableField(value="post_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "岗位编号")
    private String postCode;
    /**
     * post_up_id
     * 上级岗位id
     */
    @ApiModelProperty(value="上级岗位部门id")
    @TableField(value="post_up_parent_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "上级岗位部门id")
    private Long postUpParentId;

    /**
     * post_up_id
     * 上级岗位id
     */
    @ApiModelProperty(value="上级岗位部门名称")
    @TableField(value="post_up_parent_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "上级岗位部门名称")
    private String postUpParentName;
    /**
     * post_up_id
     * 上级岗位id
     */
    @ApiModelProperty(value="上级岗位id")
    @TableField(value="post_up_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "上级岗位id")
    private Long postUpId;
    /**
     * post_up_name
     * 上级岗位
     */
    @ApiModelProperty(value="上级岗位")
    @TableField(value="post_up_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "上级岗位")
    private String postUpName;
    /**
     * post_up_id
     * 上级岗位id
     */
    @ApiModelProperty(value="下级岗位部门id")
    @TableField(value="post_low_parent_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "下级岗位部门id")
    private Long postLowParentId;

    /**
     * post_up_id
     * 上级岗位id
     */
    @ApiModelProperty(value="下级岗位部门名称")
    @TableField(value="post_low_parent_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "下级岗位部门名称")
    private String postLowParentName;
    /**
     * post_low_id
     * 下级岗位id
     */
    @ApiModelProperty(value="下级岗位id")
    @TableField(value="post_low_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "下级岗位id")
    private Long postLowId;
    /**
     * post_low
     * 下级岗位
     */
    @ApiModelProperty(value="下级岗位")
    @TableField(value="post_low_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "下级岗位")
    private String postLowName;
    /**
     * post_sequ_id
     * 岗位序列id
     */
    @ApiModelProperty(value="岗位序列id")
    @TableField(value="post_sequ_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "岗位序列id")
    private Long postSequId;
    /**
     * post_sequ_name
     * 岗位序列
     */
    @ApiModelProperty(value="岗位序列")
    @TableField(value="post_sequ_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "岗位序列")
    private String postSequName;
    /**
     * post_grade_id
     * 岗位职等id
     */
    @ApiModelProperty(value="岗位职等id")
    @TableField(value="post_grade_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "岗位职等id")
    private Long postGradeId;
    /**
     * post_grade_name
     * 岗位职等
     */
    @ApiModelProperty(value="岗位职等")
    @TableField(value="post_grade_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "岗位职等")
    private String postGradeName;
    /**
     * post_tag_id
     * 岗位标识id
     */
    @ApiModelProperty(value="岗位标识id")
    @TableField(value="post_tag_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "岗位标识id")
    private Long postTagId;
    /**
     * post_tag_name
     * 岗位标识
     */
    @ApiModelProperty(value="岗位标识")
    @TableField(value="post_tag_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "岗位标识")
    private String postTagName;

    /**
     * post_duty
     * 岗位职责
     */
    @ApiModelProperty(value="岗位职责")
    @TableField(value="post_duty")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "岗位职责")
    private String postDuty;
    /**
     * post_ability
     * 岗位胜任能力
     */
    @ApiModelProperty(value="岗位胜任能力")
    @TableField(value="post_ability")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "岗位胜任能力")
    private String postAbility;

    /**
     * post_content
     * 岗位工作内容
     */
    @ApiModelProperty(value="岗位工作内容")
    @TableField(value="post_content")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "岗位工作内容")
    private String postContent;
    /**
     * post_check
     * 岗位考核标准
     */
    @ApiModelProperty(value="岗位考核标准")
    @TableField(value="post_check")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "岗位考核标准")
    private String postCheck;
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
    /**
     * valid_start_time
     * 岗位有效开始时间
     */
    @ApiModelProperty(value="岗位有效开始时间")
    @TableField(value="valid_start_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "岗位有效开始时间")
    private LocalDateTime validStartTime;
    /**
     * valid_end_time
     * 岗位有效结束时间
     */
    @ApiModelProperty(value="岗位有效结束时间")
    @TableField(value="valid_end_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "机构有效结束时间")
    private LocalDateTime validEndTime;
}
