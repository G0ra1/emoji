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
    import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description $专题历史与结业设置（子表） 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-13 14:23:33
 */
@Data
@Table(value = "incloud_biz_study_special_his_jieye",comment = "专题历史与结业设置（子表）")
@TableName("incloud_biz_study_special_his_jieye")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "专题历史与结业设置（子表） Entity")
public class StudySpecialHisJieye extends IModel<StudySpecialHisJieye> {

    /**
     * special_id
     * 专题id
     */
    @ApiModelProperty(value="专题id")
    @TableField(value="special_id")
    @Column(type = DataType.BIGINT, length = 19, fkTableName = "incloud_biz_study_special_his" ,fkFieldName = "id" , isNull = true, comment = "专题id")
    private Long specialId;
    /**
     * certificate_id
     * 证书id
     */
    @ApiModelProperty(value="证书id")
    @TableField(value="certificate_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "证书id")
    private Long certificateId;
    /**
     * certificate_name
     * 证书名称
     */
    @ApiModelProperty(value="证书名称")
    @TableField(value="certificate_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "证书名称")
    private String certificateName;
    /**
     * exam_lowest_score
     * 考试最低分
     */
    @ApiModelProperty(value="考试最低分")
    @TableField(value="exam_lowest_score")
    @Column(type = DataType.DECIMAL, length = 4, precision = 1 , isNull = true, comment = "考试最低分")
    private BigDecimal examLowestScore;
    /**
     * exam_highest_score
     * 考试最高分
     */
    @ApiModelProperty(value="考试最高分")
    @TableField(value="exam_highest_score")
    @Column(type = DataType.DECIMAL, length = 4, precision = 1 , isNull = true, comment = "考试最高分")
    private BigDecimal examHighestScore;
    /**
     * exam_rank
     * 考试等级
     */
    @ApiModelProperty(value="考试等级")
    @TableField(value="exam_rank")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "考试等级")
    private String examRank;
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
