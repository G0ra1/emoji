package com.netwisd.biz.study.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description 课程历史课件 功能描述...
 * @date 2022-05-11 18:50:02
 */
@Data
@ApiModel(value = "课程历史课件 Vo")
public class StudyLessonHisCouVo extends IVo {

    /**
     * lesson_id
     * 课程id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value = "课程id")
    private Long lessonId;
    /**
     * cou_id
     * 课件id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value = "课件id")
    private Long couId;
    /**
     * cou_name
     * 课件名称
     */
    @ApiModelProperty(value = "课件名称")
    private String couName;
    /**
     * label_code
     * 标签编码
     */
    @ApiModelProperty(value = "标签编码")
    private String labelCode;
    /**
     * label_name
     * 标签名称
     */
    @ApiModelProperty(value = "标签名称")
    private String labelName;
    /**
     * cou_type
     * 课件分类 1 音频 2 视频 3文档
     */
    @ApiModelProperty(value = "课件分类 1 音频 2 视频 3文档")
    private Integer couType;
    /**
     * study_best_less_time
     * 最低学习时长（分钟）
     */
    @ApiModelProperty(value="最低学习时长（分钟）")
    private Integer studyBestLessTime;
    /**
     * use_range
     * 使用权限 1 公开 2 私有
     */
    @ApiModelProperty(value = "使用权限 1 公开 2 私有")
    private Integer useRange;
    /**
     * orgPermList
     * 课程课件机构部门授权
     */
    @ApiModelProperty(value = "课程课件机构部门授权")
    private List<StudyLessonHisCouPermVo> orgPermList;
    /**
     * userPermList
     * 课程课件人员授权
     */
    @ApiModelProperty(value = "课程课件人员授权")
    private List<StudyLessonHisCouPermVo> userPermList;
}
