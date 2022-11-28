package com.netwisd.biz.study.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.netwisd.base.common.data.IDto;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.data.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @Description 课程课件观看申请表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-29 14:22:04
 */
@Data
@ApiModel(value = "课程课件观看申请表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudyLessonCouWatchApplyDto extends IDto{

    /**
     * user_id
     * 用户id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
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
