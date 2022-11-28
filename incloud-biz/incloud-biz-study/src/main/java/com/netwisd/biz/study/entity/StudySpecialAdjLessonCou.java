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
 * @Description $专题调整课程与课件表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-13 11:27:37
 */
@Data
@Table(value = "incloud_biz_study_special_adj_lesson_cou",comment = "专题调整课程与课件表")
@TableName("incloud_biz_study_special_adj_lesson_cou")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "专题调整课程与课件表 Entity")
public class StudySpecialAdjLessonCou extends IModel<StudySpecialAdjLessonCou> {

    /**
     * special_lesson_id
     * 专题课程表id
     */
    @ApiModelProperty(value="专题课程表id")
    @TableField(value="special_lesson_id")
    @Column(type = DataType.BIGINT, length = 19, fkTableName = "incloud_biz_study_special_adj_lesson" ,fkFieldName = "id" , isNull = true, comment = "专题课程表id")
    private Long specialLessonId;
    /**
     * lesson_id
     * 课程id
     */
    @ApiModelProperty(value="课程id")
    @TableField(value="lesson_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "课程id")
    private Long lessonId;
    /**
     * special_id
     * 专题id
     */
    @ApiModelProperty(value="专题id")
    @TableField(value="special_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "专题id")
    private Long specialId;
    /**
     * cou_id
     * 课件id
     */
    @ApiModelProperty(value="课件id")
    @TableField(value="cou_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "课件id")
    private Long couId;
    /**
     * cou_name
     * 课件名称
     */
    @ApiModelProperty(value="课件名称")
    @TableField(value="cou_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "课件名称")
    private String couName;
    /**
     * cou_duration
     * 课件时长;;单位：分钟)
     */
    @ApiModelProperty(value="课件时长;;单位：秒《格式化后的》)")
    @TableField(value="cou_duration")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "课件时长;;单位：秒《格式化后的》)")
    private String couDuration;
    /**
     * cou_use_range
     * 课件使用权限;0公开;1私有
     */
    @ApiModelProperty(value="课件使用权限;0公开;1私有")
    @TableField(value="cou_use_range")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "课件使用权限;0公开;1私有")
    private Integer couUseRange;
    /**
     * cou_code
     * 课件类型;0文档;1图文类型课件，2图片，3音频，4视频，5链接
     */
    @ApiModelProperty(value="课件类型;0文档;1图文类型课件，2图片，3音频，4视频，5链接")
    @TableField(value="cou_code")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "课件类型;0文档;1图文类型课件，2图片，3音频，4视频，5链接")
    private Integer couCode;
    /**
     * cou_is_compulsory
     * 课件是否必修;0:否1:是)
     */
    @ApiModelProperty(value="课件是否必修;0:否1:是)")
    @TableField(value="cou_is_compulsory")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "课件是否必修;0:否1:是)")
    private Integer couIsCompulsory;
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
