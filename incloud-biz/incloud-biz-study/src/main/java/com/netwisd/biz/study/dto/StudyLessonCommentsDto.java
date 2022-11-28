package com.netwisd.biz.study.dto;

import com.netwisd.base.common.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @Description 课程评论表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-19 19:18:19
 */
@Data
@ApiModel(value = "课程评论表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudyLessonCommentsDto extends IDto{

    /**
     * lesson_id
     * 课程id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
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
    @JsonDeserialize(using = IdTypeDeserializer.class)
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
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="父级id")
    private Long parentId;

    /**
     * parent_name
     * 父级名称
     */
    @ApiModelProperty(value="父级名称")
    private String parentName;

}
