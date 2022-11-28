package com.netwisd.biz.study.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.base.wf.starter.vo.WfVo;
import com.netwisd.biz.study.entity.StudyLessonMarterials;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 云数网讯 lhy@netwisd.com
 * @Description 课程表 功能描述...
 * @date 2022-04-19 19:15:31
 */
@Data
@ApiModel(value = "课程表 Vo")
public class StudyLessonVo extends WfVo {

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
    @JsonSerialize(using = IdTypeSerializer.class)
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
     * couSize
     * 课件数量
     */
    @ApiModelProperty(value = "课件数量")
    private Integer couSize;

    /**
     * isOpen
     * 是否全部公开
     */
    @ApiModelProperty(value = "是否全部公开")
    private Integer isOpen;

    /**
     * studyTime
     * 学时(秒)
     */
    @ApiModelProperty(value = "学时(秒)")
    private Integer studyTime;

    /**
     * studyTimeSize
     * 学时(时分秒)
     */
    @ApiModelProperty(value = "学时(时分秒)")
    private String studyTimeSize;

    /**
     * auditSuccessTime
     * 审批成功时间
     */
    @ApiModelProperty(value = "审批提交时间")
    private LocalDateTime auditSubmitTime;

    /**
     * auditSuccessTime
     * 审批成功时间
     */
    @ApiModelProperty(value = "审批成功时间")
    private LocalDateTime auditSuccessTime;

    /**
     * lessonCouList
     * 课程课件信息集合
     */
    @ApiModelProperty(value = "课程课件信息集合")
    private List<StudyLessonCouVo> couList;

    /**
     * lessonMarterialsList
     * 课程学习资料信息集合
     */
    @ApiModelProperty(value = "课程学习资料信息集合")
    private List<StudyLessonMarterialsVo> marterialsList;

}
