package com.netwisd.biz.study.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netwisd.base.wf.starter.dto.WfDto;
import com.netwisd.common.core.util.IdTypeDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 云数网讯 lhy@netwisd.com
 * @Description 课程表 功能描述...
 * @date 2022-04-19 19:15:31
 */
@Data
@ApiModel(value = "课程表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudyLessonDto extends WfDto {

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
     * hits
     * 点击量
     */
    @ApiModelProperty(value = "点击量")
    private Long hits;
    /**
     * description
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;
    /**
     * is_index
     * 是否首页展示
     */
    @ApiModelProperty(value = "是否首页展示")
    private Integer isIndex;
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
    /**
     * is_enable
     * 是否启用0:禁用1:启用
     */
    @ApiModelProperty(value = "是否启用0:禁用1:启用")
    private Integer isEnable;
    /**
     * status
     * 状态0:未生效1:已生效2:调整中
     */
    @ApiModelProperty(value = "状态0:未生效1:已生效2:调整中")
    private Integer status;
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
     * couList
     * 课件集合
     */
    @ApiModelProperty(value = "课件集合")
    private List<StudyLessonCouDto> couList;
    /**
     * marterialsList
     * 学习资料集合
     */
    @ApiModelProperty(value = "学习资料集合")
    private List<StudyLessonMarterialsDto> marterialsList;

    ////////////////////课程模块列表查询用////////////////////////

    /**
     * labelCodes
     * 标签编码集合
     */
    @ApiModelProperty(value = "标签编码集合")
    private List<String> labelCodes;

    /**
     * isHightHits
     * 是否热门
     */
    @ApiModelProperty(value = "是否热门")
    private Integer isHightHits;

    /**
     * isNew
     * 是否最新
     */
    @ApiModelProperty(value = "是否最新")
    private Integer isNew;

}
