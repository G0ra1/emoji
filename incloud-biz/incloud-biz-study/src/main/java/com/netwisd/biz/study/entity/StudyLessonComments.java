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

/**
 * @Description $课程评论表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-19 19:18:19
 */
@Data
@Table(value = "incloud_biz_study_lesson_comments",comment = "课程评论表")
@TableName("incloud_biz_study_lesson_comments")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "课程评论表 Entity")
public class StudyLessonComments extends IModel<StudyLessonComments> {

    /**
    * lesson_id
    * 课程id
    */
    @ApiModelProperty(value="课程id")
    @TableField(value="lesson_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "课程id")
    private Long lessonId;

    /**
    * lesson_name
    * 课程名称
    */
    @ApiModelProperty(value="课程名称")
    @TableField(value="lesson_name")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "课程名称")
    private String lessonName;

    /**
    * user_id
    * 用户
    */
    @ApiModelProperty(value="用户")
    @TableField(value="user_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "用户")
    private Long userId;

    /**
    * user_name
    * 用户名
    */
    @ApiModelProperty(value="用户名")
    @TableField(value="user_name")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "用户名")
    private String userName;

    /**
    * user_name_ch
    * 用户姓名
    */
    @ApiModelProperty(value="用户姓名")
    @TableField(value="user_name_ch")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "用户姓名")
    private String userNameCh;

    /**
    * comment
    * 评论内容
    */
    @ApiModelProperty(value="评论内容")
    @TableField(value="comment")
    @Column(type = DataType.VARCHAR, length = 300,  isNull = true, comment = "评论内容")
    private String comment;

    /**
    * level
    * 级别
    */
    @ApiModelProperty(value="级别")
    @TableField(value="level")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "级别")
    private Integer level;

    /**
    * parent_id
    * 父级id
    */
    @ApiModelProperty(value="父级id")
    @TableField(value="parent_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "父级id")
    private Long parentId;

    /**
     * parent_name
     * 父级名称
     */
    @ApiModelProperty(value="父级名称")
    @TableField(value="parent_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "父级名称")
    private String parentName;

}
