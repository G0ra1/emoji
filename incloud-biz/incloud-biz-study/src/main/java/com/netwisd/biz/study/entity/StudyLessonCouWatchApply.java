package com.netwisd.biz.study.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.anntation.Table;
import com.netwisd.common.db.data.DataType;
import com.netwisd.common.db.data.IModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description $课程课件观看申请表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-29 14:22:04
 */
@Data
@Table(value = "incloud_biz_study_lesson_cou_watch_apply",comment = "课程课件观看申请表")
@TableName("incloud_biz_study_lesson_cou_watch_apply")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "课程课件观看申请表 Entity")
public class StudyLessonCouWatchApply extends IModel<StudyLessonCouWatchApply> {

    /**
    * user_id
    * 用户id
    */
    @ApiModelProperty(value="用户id")
    @TableField(value="user_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "用户id")
    private Long userId;

    /**
    * user_name
    * 用户名
    */
    @ApiModelProperty(value="用户名")
    @TableField(value="user_name")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "用户名")
    private String userName;

    /**
    * user_name_ch
    * 用户中文名称
    */
    @ApiModelProperty(value="用户中文名称")
    @TableField(value="user_name_ch")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "用户中文名称")
    private String userNameCh;

    /**
    * range_id
    * 对象id
    */
    @ApiModelProperty(value="对象id")
    @TableField(value="range_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "对象id")
    private Long rangeId;

    /**
    * range_name
    * 对象名称
    */
    @ApiModelProperty(value="对象名称")
    @TableField(value="range_name")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "对象名称")
    private String rangeName;

    /**
     * range_type
     * 对象类型 1 课程 2 课件
     */
    @ApiModelProperty(value="对象类型 1 课程 2 课件")
    @TableField(value="range_type")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "对象类型 1 课程 2 课件")
    private Integer rangeType;

    /**
     * apply_status
     * 申请状态 0 未审核 1 审核通过 2审核失败
     */
    @ApiModelProperty(value="申请状态 0 未审核 1 审核通过 2审核失败")
    @TableField(value="apply_status")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "申请状态 0 未审核 1 审核通过 2审核失败")
    private Integer applyStatus;

    /**
     * apply_message
     * 申请描述
     */
    @ApiModelProperty(value="申请描述")
    @TableField(value="apply_message")
    @Column(type = DataType.VARCHAR, length = 300,  isNull = true, comment = "申请描述")
    private String applyMessage;

    /**
     * result_message
     * 结果描述
     */
    @ApiModelProperty(value="结果描述")
    @TableField(value="result_message")
    @Column(type = DataType.VARCHAR, length = 300,  isNull = true, comment = "结果描述")
    private String resultMessage;
}
