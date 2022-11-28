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
 * @Description $专题与课程中间表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-13 10:59:05
 */
@Data
@Table(value = "incloud_biz_study_special_lesson",comment = "专题与课程中间表")
@TableName("incloud_biz_study_special_lesson")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "专题与课程中间表 Entity")
public class StudySpecialLesson extends IModel<StudySpecialLesson> {

    /**
     * special_id
     * 培训计划id
     */
    @ApiModelProperty(value="培训计划id")
    @TableField(value="special_id")
    @Column(type = DataType.BIGINT, length = 19, fkTableName = "incloud_biz_study_special" ,fkFieldName = "id" , isNull = true, comment = "培训计划id")
    private Long specialId;
    /**
     * lesson_id
     * 课程id
     */
    @ApiModelProperty(value="课程id")
    @TableField(value="lesson_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "课程id")
    private Long lessonId;
    /**
     * lesson_name
     * 课程名称
     */
    @ApiModelProperty(value="课程名称")
    @TableField(value="lesson_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "课程名称")
    private String lessonName;
    /**
     * lesson_type
     * 课程分类
     */
    @ApiModelProperty(value="课程分类")
    @TableField(value="lesson_type")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "课程分类")
    private String lessonType;
    /**
     * practise_paper_id
     * 练习试卷id
     */
    @ApiModelProperty(value="练习试卷id")
    @TableField(value="practise_paper_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "练习试卷id")
    private Long practisePaperId;
    /**
     * practise_paper_name
     * 练习试卷名称
     */
    @ApiModelProperty(value="练习试卷名称")
    @TableField(value="practise_paper_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "练习试卷名称")
    private String practisePaperName;
    /**
     * practise_paper_total_score
     * 练习试卷总分
     */
    @ApiModelProperty(value="练习试卷总分")
    @TableField(value="practise_paper_total_score")
    @Column(type = DataType.DECIMAL, length = 4, precision = 1 , isNull = true, comment = "练习试卷总分")
    private BigDecimal practisePaperTotalScore;
    /**
     * practise_paper_is_retest
     * 联系试卷是否重新考试;;复考）
     */
    @ApiModelProperty(value="联系试卷是否重新考试;;复考）")
    @TableField(value="practise_paper_is_retest")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "联系试卷是否重新考试;;复考）")
    private Integer practisePaperIsRetest;
    /**
     * file_id
     * 课程文件id
     */
    @ApiModelProperty(value="课程文件id")
    @TableField(value="file_id")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "课程文件id")
    private String fileId;
    /**
     * file_url
     * 文件路径
     */
    @ApiModelProperty(value="文件路径")
    @TableField(value="file_url")
    @Column(type = DataType.VARCHAR, length = 3000,  isNull = true, comment = "文件路径")
    private String fileUrl;
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
