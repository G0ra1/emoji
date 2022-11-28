package com.netwisd.biz.study.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netwisd.common.core.util.IdTypeDeserializer;
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
 * @Description $专题历史必考人员 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-06-21 14:50:51
 */
@Data
@Table(value = "incloud_biz_study_special_his_must_exam_user",comment = "专题历史必考人员")
@TableName("incloud_biz_study_special_his_must_exam_user")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "专题历史必考人员 Entity")
public class StudySpecialHisMustExamUser extends IModel<StudySpecialHisMustExamUser> {

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
     * 父级机构ID
     */
    @ApiModelProperty(value="父级机构ID")
    @TableField(value="create_user_parent_org_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "父级机构ID")
    private Long createUserParentOrgId;
    /**
     * create_user_parent_org_name
     * 父级机构名称
     */
    @ApiModelProperty(value="父级机构名称")
    @TableField(value="create_user_parent_org_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "父级机构名称")
    private String createUserParentOrgName;
    /**
     * create_user_parent_dept_id
     * 父级部门ID
     */
    @ApiModelProperty(value="父级部门ID")
    @TableField(value="create_user_parent_dept_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "父级部门ID")
    private Long createUserParentDeptId;
    /**
     * create_user_parent_dept_name
     * 父级部门名称
     */
    @ApiModelProperty(value="父级部门名称")
    @TableField(value="create_user_parent_dept_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "父级部门名称")
    private String createUserParentDeptName;
    /**
     * create_user_org_full_id
     * 父级组织全路径ID
     */
    @ApiModelProperty(value="父级组织全路径ID")
    @TableField(value="create_user_org_full_id")
    @Column(type = DataType.VARCHAR, length = 2000,  isNull = true, comment = "父级组织全路径ID")
    private String createUserOrgFullId;
    /**
     * special_id
     * 专题id
     */
    @ApiModelProperty(value="专题id")
    @TableField(value="special_id")
    @Column(type = DataType.BIGINT, length = 19, fkTableName = "incloud_biz_study_special_his" ,fkFieldName = "id" , isNull = true, comment = "专题id")
    private Long specialId;
    /**
     * special_paper_id
     * 专题试卷id
     */
    @ApiModelProperty(value="专题试卷id")
    @TableField(value="special_paper_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "专题试卷id")
    private Long specialPaperId;
    /**
     * special_must_exam_user_id
     * 专题必考人员id
     */
    @ApiModelProperty(value="专题必考人员id")
    @TableField(value="special_must_exam_user_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "专题必考人员id")
    private Long specialMustExamUserId;

    /**
     * special_must_exam_user_name
     * 专题必考人员名称
     */
    @ApiModelProperty(value="专题必考人员名称")
    @TableField(value="special_must_exam_user_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "专题必考人员名称")
    private String  specialMustExamUserName;
}
