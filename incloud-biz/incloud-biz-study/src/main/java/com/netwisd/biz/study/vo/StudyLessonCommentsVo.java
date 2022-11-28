package com.netwisd.biz.study.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

/**
 * @Description 课程评论表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-19 19:18:19
 */
@Data
@ApiModel(value = "课程评论表 Vo")
public class StudyLessonCommentsVo extends IVo{

    /**
     * lesson_id
     * 课程id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="课程id")
    private Long lessonId;

    /**
     * lesson_name
     * 课程名称
     */
    @ApiModelProperty(value="课程名称")
    private String lessonName;

    /**
     * user_id
     * 用户
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="用户")
    private Long userId;

    /**
     * user_name
     * 用户名
     */
    @ApiModelProperty(value="用户名")
    private String userName;

    /**
     * user_name_ch
     * 用户姓名
     */
    @ApiModelProperty(value="用户姓名")
    private String userNameCh;

    /**
     * comment
     * 评论内容
     */
    @ApiModelProperty(value="评论内容")
    private String comment;

    /**
     * level
     * 级别
     */
    @ApiModelProperty(value="级别")
    private Integer level;

    /**
     * parent_id
     * 父级id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="父级id")
    private Long parentId;

    /**
     * parent_name
     * 父级名称
     */
    @ApiModelProperty(value="父级名称")
    private String parentName;

    /**
     * parent_full_id
     * 父级总id
     */
    @ApiModelProperty(value="父级总id")
    private Long parentFullId;

    /**
     * isDelete
     * 是否可以当前登陆人删除 0否 1是
     */
    @ApiModelProperty(value="是否可以当前登陆人删除 0否 1是")
    private Integer isDelete;

    /**
     * kids
     * 子集
     */
    @ApiModelProperty(value="子集")
    private List<StudyLessonCommentsVo> kids;
}
