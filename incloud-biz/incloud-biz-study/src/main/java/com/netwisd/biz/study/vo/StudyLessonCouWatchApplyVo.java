package com.netwisd.biz.study.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 课程课件观看申请表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-29 14:22:04
 */
@Data
@ApiModel(value = "课程课件观看申请表 Vo")
public class StudyLessonCouWatchApplyVo extends IVo{

    /**
     * user_id
     * 用户id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="用户id")
    private Long userId;

    /**
     * user_name
     * 用户名
     */
    @ApiModelProperty(value="用户名")
    private String userName;

    /**
     * user_name_ch
     * 用户中文名称
     */
    @ApiModelProperty(value="用户中文名称")
    private String userNameCh;

    /**
     * range_id
     * 对象id
     */
    @ApiModelProperty(value="对象id")
    private Long rangeId;

    /**
     * range_name
     * 对象名称
     */
    @ApiModelProperty(value="对象名称")
    private String rangeName;

    /**
     * range_type
     * 对象类型 1 课程 2 课件
     */
    @ApiModelProperty(value="对象类型 1 课程 2 课件")
    private Integer rangeType;

    /**
     * apply_status
     * 申请状态 0 未审核 1 审核通过 2审核失败
     */
    @ApiModelProperty(value="申请状态 0 未审核 1 审核通过 2审核失败")
    private Integer applyStatus;

    /**
     * apply_message
     * 申请描述
     */
    @ApiModelProperty(value="申请描述")
    private String applyMessage;

    /**
     * result_message
     * 结果描述
     */
    @ApiModelProperty(value="结果描述")
    private String resultMessage;
}
