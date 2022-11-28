package com.netwisd.biz.study.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description 课程历史学习资料 功能描述...
 * @date 2022-05-11 18:50:02
 */
@Data
@ApiModel(value = "课程历史学习资料 Vo")
public class StudyLessonHisMarterialsVo extends IVo {

    /**
     * lesson_id
     * 课程主键
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value = "课程主键")
    private Long lessonId;
    /**
     * marterials_id
     * 学习资料id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
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
