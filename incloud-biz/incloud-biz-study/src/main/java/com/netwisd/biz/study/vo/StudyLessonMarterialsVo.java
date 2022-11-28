package com.netwisd.biz.study.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 云数网讯 lhy@netwisd.com
 * @Description 课程学习资料表 功能描述...
 * @date 2022-04-19 19:21:47
 */
@Data
@ApiModel(value = "课程学习资料表 Vo")
public class StudyLessonMarterialsVo extends IVo {

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
     * marterials_name
     * 学习资料名称
     */
    @ApiModelProperty(value = "学习资料名称")
    private String marterialsName;

    /**
     * is_download
     * 是否允许下载 0否 1是
     */
    @ApiModelProperty(value = "是否允许下载 0否 1是")
    private Integer isDownload;

}
