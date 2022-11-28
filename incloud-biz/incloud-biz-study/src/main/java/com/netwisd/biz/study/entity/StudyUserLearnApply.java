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

import java.time.LocalDateTime;

/**
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description 人员学习申请
 * @date 2022-04-25 09:39:13
 */
@Data
@Table(value = "incloud_biz_study_user_learn_apply", comment = "人员学习申请")
@TableName("incloud_biz_study_user_learn_apply")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "人员学习申请")
public class StudyUserLearnApply extends IModel<StudyUserLearnApply> {

    /**
     * user_id
     * 用户主键
     */
    @ApiModelProperty(value = "用户主键")
    @TableField(value = "user_id")
    @Column(type = DataType.BIGINT, length = 20, isNull = true, comment = "用户主键")
    private Long userId;

    /**
     * userName
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    @TableField(value = "user_name")
    @Column(type = DataType.VARCHAR, length = 64, isNull = true, comment = "用户名")
    private String userName;

    /**
     * targetType
     * 目标类型
     */
    @ApiModelProperty(value = "目标类型")
    @TableField(value = "target_type")
    @Column(type = DataType.INT, length = 3, isNull = true, comment = "目标类型")
    private Integer targetType;

    /**
     * target_id
     * 目标ID
     */
    @ApiModelProperty(value = "目标ID")
    @TableField(value = "target_id")
    @Column(type = DataType.BIGINT, length = 20, isNull = true, comment = "目标ID")
    private Long targetId;

    /**
     * targetName
     * 目标名称
     */
    @ApiModelProperty(value = "目标名称")
    @TableField(value = "target_name")
    @Column(type = DataType.VARCHAR, length = 100, isNull = true, comment = "目标名称")
    private String targetName;

    /**
     * targetManagerId
     * 目标管理者ID
     */
    @ApiModelProperty(value = "目标管理者ID")
    @TableField(value = "target_manager_id")
    @Column(type = DataType.BIGINT, length = 20, isNull = true, comment = "目标管理者ID")
    private Long targetManagerId;

    /**
     * targetManagerName
     * 目标管理者名称
     */
    @ApiModelProperty(value = "目标管理者名称")
    @TableField(value = "target_manager_name")
    @Column(type = DataType.VARCHAR, length = 100, isNull = true, comment = "目标管理者名称")
    private String targetManagerName;

    /**
     * lesson_id
     * 课程ID
     */
    @ApiModelProperty(value = "课程ID")
    @TableField(value = "lesson_id")
    @Column(type = DataType.BIGINT, length = 20, isNull = true, comment = "课程ID")
    private Long lessonId;

    /**
     * lessonName
     * 课程名称
     */
    @ApiModelProperty(value = "课程名称")
    @TableField(value = "lesson_name")
    @Column(type = DataType.VARCHAR, length = 100, isNull = true, comment = "课程名称")
    private String lessonName;

    /**
     * applyMessage
     * 申请描述
     */
    @ApiModelProperty(value = "申请描述")
    @TableField(value = "apply_message")
    @Column(type = DataType.VARCHAR, length = 1000, isNull = true, comment = "申请描述")
    private String applyMessage;

    /**
     * 申请时间
     */
    @ApiModelProperty(value = "申请时间")
    @TableField(value = "apply_time")
    @Column(type = DataType.DATETIME, length = 0, isNull = true, comment = "申请时间")
    public LocalDateTime applyTime;

    /**
     * applyStatus
     * 申请状态
     */
    @ApiModelProperty(value = "申请状态")
    @TableField(value = "apply_status")
    @Column(type = DataType.INT, length = 3, isNull = true, comment = "申请状态")
    private Integer applyStatus;

    /**
     * 处理时间
     */
    @ApiModelProperty(value = "处理时间")
    @TableField(value = "deal_time")
    @Column(type = DataType.DATETIME, length = 0, isNull = true, comment = "处理时间")
    public LocalDateTime dealTime;


    /**
     * dealMessage
     * 处理描述
     */
    @ApiModelProperty(value = "处理描述")
    @TableField(value = "deal_message")
    @Column(type = DataType.VARCHAR, length = 1000, isNull = true, comment = "处理描述")
    private String dealMessage;

}
