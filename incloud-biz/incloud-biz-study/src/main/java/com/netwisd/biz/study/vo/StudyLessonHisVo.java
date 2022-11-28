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
 * @Description 课程历史 功能描述...
 * @date 2022-05-11 18:50:02
 */
@Data
@ApiModel(value = "课程历史 Vo")
public class StudyLessonHisVo extends IVo {

    /**
     * link_id
     * 关联原纪录ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
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
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value = "封面图id")
    private Long imgId;
    /**
     * img_url
     * 封面图路径
     */
    @ApiModelProperty(value = "封面图路径")
    private String imgUrl;

    /**
     * couList
     * 课件信息集合
     */
    @ApiModelProperty(value = "课件信息集合")
    private List<StudyLessonHisCouVo> couList;

    /**
     * marterialsList
     * 学习资料信息集合
     */
    @ApiModelProperty(value = "学习资料信息集合")
    private List<StudyLessonHisMarterialsVo> marterialsList;

}
