package com.netwisd.biz.study.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description 人员学习申请
 * @date 2022-04-25 09:39:13
 */
@Data
@ApiModel(value = "人员学习申请Vo")
public class StudyUserLearnApplyVo extends IVo {

    /**
     * user_id
     * 用户主键
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value = "用户主键")
    private Long userId;

    /**
     * userName
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String userName;

    /**
     * targetType
     * 目标类型
     */
    @ApiModelProperty(value = "目标类型")
    private Integer targetType;

    /**
     * target_id
     * 目标ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value = "目标ID")
    private Long targetId;

    /**
     * targetName
     * 目标名称
     */
    @ApiModelProperty(value = "目标名称")
    private String targetName;

    /**
     * targetManagerId
     * 目标管理者ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value = "目标管理者ID")
    private Long targetManagerId;

    /**
     * targetManagerName
     * 目标管理者名称
     */
    @ApiModelProperty(value = "目标管理者名称")
    private String targetManagerName;

    /**
     * lesson_id
     * 课程ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value = "课程ID")
    private Long lessonId;

    /**
     * lessonName
     * 课程名称
     */
    @ApiModelProperty(value = "课程名称")
    private String lessonName;

    /**
     * applyMessage
     * 申请描述
     */
    @ApiModelProperty(value = "申请描述")
    private String applyMessage;

    /**
     * 申请时间
     */
    @ApiModelProperty(value = "申请时间")
    public LocalDateTime applyTime;

    /**
     * applyStatus
     * 申请状态
     */
    @ApiModelProperty(value = "申请状态")
    private Integer applyStatus;

    /**
     * 处理时间
     */
    @ApiModelProperty(value = "处理时间")
    public LocalDateTime dealTime;

    /**
     * dealMessage
     * 处理描述
     */
    @ApiModelProperty(value = "处理描述")
    private String dealMessage;
}
