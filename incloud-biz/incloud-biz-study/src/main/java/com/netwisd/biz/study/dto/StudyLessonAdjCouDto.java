package com.netwisd.biz.study.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.db.anntation.Map;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description 课程调整课件 功能描述...
 * @date 2022-05-12 09:17:24
 */
@Data
@Map("incloud_biz_study_lesson_adj_cou")
@ApiModel(value = "课程调整课件 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudyLessonAdjCouDto extends IDto {

    /**
     * lesson_adj_id
     * 课程id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value = "课程调整表id")
    private Long lessonAdjId;
    /**
     * cou_id
     * 课件id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
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
    private List<StudyLessonAdjCouPermDto> orgPermList;
    /**
     * userPermList
     * 课程课件人员授权
     */
    @ApiModelProperty(value = "课程课件人员授权")
    private List<StudyLessonAdjCouPermDto> userPermList;
}
