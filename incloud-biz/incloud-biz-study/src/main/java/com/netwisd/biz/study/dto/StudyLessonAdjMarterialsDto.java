package com.netwisd.biz.study.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.db.anntation.Map;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description 课程调整学习资料 功能描述...
 * @date 2022-05-12 09:17:24
 */
@Data
@Map("incloud_biz_study_lesson_adj_marterials")
@ApiModel(value = "课程调整学习资料 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudyLessonAdjMarterialsDto extends IDto {

    /**
     * lesson_adj_id
     * 课程调整表id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Valid(length = 19)
    @ApiModelProperty(value = "课程调整表id")
    private Long lessonAdjId;
    /**
     * marterials_id
     * 学习资料id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Valid(length = 19)
    @ApiModelProperty(value = "学习资料id")
    private Long marterialsId;
    /**
     * marterials_name
     * 学习资料名称
     */
    @ApiModelProperty(value = "学习资料名称")
    private String marterialsName;
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
     * is_download
     * 是否允许下载 0否 1是
     */
    @ApiModelProperty(value = "是否允许下载 0否 1是")
    private Integer isDownload;

}
