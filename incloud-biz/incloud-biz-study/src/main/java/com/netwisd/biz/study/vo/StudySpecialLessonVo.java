package com.netwisd.biz.study.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 云数网讯 cr@netwisd.com
 * @Description 专题与课程中间表 功能描述...
 * @date 2022-05-13 10:59:05
 */
@Data
@ApiModel(value = "专题与课程中间表 Vo")
public class StudySpecialLessonVo extends IVo {

    /**
     * special_id
     * 培训计划id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value = "培训计划id")
    private Long specialId;
    /**
     * lesson_id
     * 课程id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value = "课程id")
    private Long lessonId;
    /**
     * lesson_name
     * 课程名称
     */
    @ApiModelProperty(value = "课程名称")
    private String lessonName;
    /**
     * lesson_type
     * 课程分类
     */
    @ApiModelProperty(value = "课程分类")
    private String lessonType;
    /**
     * practise_paper_id
     * 练习试卷id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value = "练习试卷id")
    private Long practisePaperId;
    /**
     * practise_paper_name
     * 练习试卷名称
     */
    @ApiModelProperty(value = "练习试卷名称")
    private String practisePaperName;
    /**
     * practise_paper_total_score
     * 练习试卷总分
     */
    @ApiModelProperty(value = "练习试卷总分")
    private BigDecimal practisePaperTotalScore;
    /**
     * practise_paper_is_retest
     * 联系试卷是否重新考试;;复考）
     */
    @ApiModelProperty(value = "联系试卷是否重新考试;;复考）")
    private Integer practisePaperIsRetest;
    /**
     * file_id
     * 课程文件id
     */
    @ApiModelProperty(value = "课程文件id")
    private String fileId;
    /**
     * file_url
     * 文件路径
     */
    @ApiModelProperty(value = "文件路径")
    private String fileUrl;

    @ApiModelProperty(value = "专题课程课件集合")
    private List<StudySpecialLessonCouVo> studySpecialLessonCouList;

    //课程描述
    @ApiModelProperty(value = "课程描述")
    private String description;

    //学员端使用字段
    @ApiModelProperty(value = "课程对应课件的总时长")
    private String totalCouDuration;

    //课件数量
    @ApiModelProperty(value = "课件数量")
    private int couNum;

    //必须课件数量
    @ApiModelProperty(value = "必须课件数量")
    private int bxCouNum;

    //学习状态
    @ApiModelProperty(value = "学习状态")
    private int learnStatus;

    ////////////////////////////////////我的专题页面使用//////////////////////////////////////

    /**
     * specialName
     * 专题名称
     */
    @ApiModelProperty(value = "专题名称")
    private String specialName;

    /**
     * couIsCompulsory
     * 是否必修 0否1是
     */
    @ApiModelProperty(value = "是否必修 0否1是")
    private Integer couIsCompulsory;

}
