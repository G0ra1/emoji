package com.netwisd.biz.study.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.base.wf.starter.vo.WfVo;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description 课程调整申请 功能描述...
 * @date 2022-05-12 09:17:24
 */
@Data
@ApiModel(value = "课程调整申请 Vo")
public class StudyLessonAdjVo extends WfVo {

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
     * audit_submit_time
     * 审批提交时间
     */
    @ApiModelProperty(value = "审批提交时间")
    private LocalDateTime auditSubmitTime;
    /**
     * audit_success_time
     * 审核通过时间
     */
    @ApiModelProperty(value = "审核通过时间")
    private LocalDateTime auditSuccessTime;

    /**
     * lessonAdjCouList
     * 课程课件信息集合
     */
    @ApiModelProperty(value = "课程课件信息集合")
    private List<StudyLessonAdjCouVo> couList;

    /**
     * lessonAdjMarterialsList
     * 课程学习资料信息集合
     */
    @ApiModelProperty(value = "课程学习资料信息集合")
    private List<StudyLessonAdjMarterialsVo> marterialsList;

}
