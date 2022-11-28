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

/**
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description 课程历史 功能描述...
 * @date 2022-05-11 18:50:02
 */
@Data
@Map("incloud_biz_study_lesson_his")
@ApiModel(value = "课程历史 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudyLessonHisDto extends IDto {

    /**
     * link_id
     * 关联原纪录ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value = "关联原纪录ID")
    private Long linkId;
    /**
     * type_code
     * 分类编码
     */
    @ApiModelProperty(value = "分类编码")
    private String typeCode;
    /**
     * type_name
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称")
    private String typeName;
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
     * lesson_name
     * 课程名称
     */
    @ApiModelProperty(value = "课程名称")
    private String lessonName;
    /**
     * description
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;
    /**
     * img_id
     * 封面图id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value = "封面图id")
    private Long imgId;
    /**
     * img_url
     * 封面图路径
     */
    @ApiModelProperty(value = "封面图路径")
    private String imgUrl;

}
