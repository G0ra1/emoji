package com.netwisd.biz.study.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.base.common.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import com.netwisd.common.db.anntation.Fk;
import com.netwisd.common.db.anntation.Map;
/**
 * @Description 专题历史与课程表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-13 14:23:33
 */
@Data
@Map("incloud_biz_study_special_his_lesson")
@ApiModel(value = "专题历史与课程表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudySpecialHisLessonDto extends IDto{

    public StudySpecialHisLessonDto(Args args){
        super(args);
    }
    /**
     * special_id
     * 培训计划id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Fk(table = "incloud_biz_study_special_his" ,field = "id")
    @ApiModelProperty(value="培训计划id")
    private Long specialId;
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
     * lesson_type
     * 课程分类
     */
    @ApiModelProperty(value="课程分类")
    private String lessonType;
    /**
     * file_id
     * 课程文件id
     */
    @ApiModelProperty(value="课程文件id")
    private String fileId;
    /**
     * file_url
     * 文件路径
     */
    @ApiModelProperty(value="文件路径")
    private String fileUrl;
    /**
     * practise_paper_id
     * 练习试卷id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="练习试卷id")
    private Long practisePaperId;
    /**
     * practise_paper_name
     * 练习试卷名称
     */
    @ApiModelProperty(value="练习试卷名称")
    private String practisePaperName;
    /**
     * practise_paper_total_score
     * 练习试卷总分
     */
    @ApiModelProperty(value="练习试卷总分")
    private BigDecimal practisePaperTotalScore;
    /**
     * practise_paper_is_retest
     * 联系试卷是否重新考试;;复考）
     */
    @ApiModelProperty(value="联系试卷是否重新考试;;复考）")
    private Integer practisePaperIsRetest;


    @ApiModelProperty(value="专题课程课件集合")
    private List<StudySpecialHisLessonCouDto> studySpecialLessonCouList;
}
