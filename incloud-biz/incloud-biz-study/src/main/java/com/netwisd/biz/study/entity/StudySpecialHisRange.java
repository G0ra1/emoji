package com.netwisd.biz.study.entity;

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
 * @Description $专题历史与对象表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-13 14:23:33
 */
@Data
@Table(value = "incloud_biz_study_special_his_range",comment = "专题历史与对象表")
@TableName("incloud_biz_study_special_his_range")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "专题历史与对象表 Entity")
public class StudySpecialHisRange extends IModel<StudySpecialHisRange> {

    /**
     * special_id
     * 培训计划id
     */
    @ApiModelProperty(value="培训计划id")
    @TableField(value="special_id")
    @Column(type = DataType.BIGINT, length = 19, fkTableName = "incloud_biz_study_special_his" ,fkFieldName = "id" , isNull = true, comment = "培训计划id")
    private Long specialId;
    /**
     * range_type
     * 对象类型
     */
    @ApiModelProperty(value="对象类型")
    @TableField(value="range_type")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "对象类型")
    private String rangeType;
    /**
     * range_id
     * 对象id
     */
    @ApiModelProperty(value="对象id")
    @TableField(value="range_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "对象id")
    private Long rangeId;
    /**
     * range_name
     * 对象名称
     */
    @ApiModelProperty(value="对象名称")
    @TableField(value="range_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "对象名称")
    private String rangeName;
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

}
