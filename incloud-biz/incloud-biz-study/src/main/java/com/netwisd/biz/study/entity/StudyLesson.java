package com.netwisd.biz.study.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.netwisd.base.wf.starter.entitiy.WfEntity;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.anntation.Table;
import com.netwisd.common.db.data.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author 云数网讯 lhy@netwisd.com
 * @Description $课程表 功能描述...
 * @date 2022-04-19 19:15:31
 */
@Data
@Table(value = "incloud_biz_study_lesson", comment = "课程表")
@TableName("incloud_biz_study_lesson")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "课程表 Entity")
public class StudyLesson extends WfEntity<StudyLesson> {

    /**
     * type_code
     * 分类编码
     */
    @ApiModelProperty(value = "分类编码")
    @TableField(value = "type_code")
    @Column(type = DataType.VARCHAR, length = 100, isNull = true, comment = "分类编码")
    private String typeCode;
    /**
     * type_name
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称")
    @TableField(value = "type_name")
    @Column(type = DataType.VARCHAR, length = 100, isNull = true, comment = "分类名称")
    private String typeName;
    /**
     * label_code
     * 标签编码
     */
    @ApiModelProperty(value = "标签编码")
    @TableField(value = "label_code")
    @Column(type = DataType.VARCHAR, length = 100, isNull = true, comment = "标签编码")
    private String labelCode;
    /**
     * label_name
     * 标签名称
     */
    @ApiModelProperty(value = "标签名称")
    @TableField(value = "label_name")
    @Column(type = DataType.VARCHAR, length = 100, isNull = true, comment = "标签名称")
    private String labelName;
    /**
     * lesson_name
     * 课程名称
     */
    @ApiModelProperty(value = "课程名称")
    @TableField(value = "lesson_name")
    @Column(type = DataType.VARCHAR, length = 100, isNull = true, comment = "课程名称")
    private String lessonName;
    /**
     * hits
     * 点击量
     */
    @ApiModelProperty(value = "点击量")
    @TableField(value = "hits")
    @Column(type = DataType.BIGINT, length = 19, isNull = true, comment = "点击量")
    private Long hits;
    /**
     * description
     * 描述
     */
    @ApiModelProperty(value = "描述")
    @TableField(value = "description")
    @Column(type = DataType.VARCHAR, length = 500, isNull = true, comment = "描述")
    private String description;
    /**
     * is_index
     * 是否首页展示
     */
    @ApiModelProperty(value = "是否首页展示")
    @TableField(value = "is_index")
    @Column(type = DataType.INT, length = 10, isNull = true, comment = "是否首页展示")
    private Integer isIndex;
    /**
     * img_id
     * 封面图id
     */
    @ApiModelProperty(value = "封面图id")
    @TableField(value = "img_id")
    @Column(type = DataType.BIGINT, length = 19, isNull = true, comment = "封面图id")
    private Long imgId;
    /**
     * img_url
     * 封面图路径
     */
    @ApiModelProperty(value = "封面图路径")
    @TableField(value = "img_url")
    @Column(type = DataType.VARCHAR, length = 255, isNull = true, comment = "封面图路径")
    private String imgUrl;
    /**
     * is_enable
     * 是否启用0:禁用1:启用
     */
    @ApiModelProperty(value = "是否启用0:禁用1:启用")
    @TableField(value = "is_enable")
    @Column(type = DataType.INT, length = 10, isNull = true, comment = "是否启用0:禁用1:启用")
    private Integer isEnable;
    /**
     * status
     * 状态0:未生效1:已生效2:调整中
     */
    @ApiModelProperty(value = "状态0:未生效1:已生效2:调整中")
    @TableField(value = "status")
    @Column(type = DataType.INT, length = 10, isNull = true, comment = "状态0:未生效1:已生效2:调整中")
    private Integer status;

    /**
     * audit_submit_time
     * 审批提交时间
     */
    @ApiModelProperty(value = "审批提交时间")
    @TableField(value = "audit_submit_time")
    @Column(type = DataType.DATETIME, length = 0, isNull = true, comment = "审批提交时间")
    private LocalDateTime auditSubmitTime;

    /**
     * audit_success_time
     * 审核通过时间
     */
    @ApiModelProperty(value = "审核通过时间")
    @TableField(value = "audit_success_time")
    @Column(type = DataType.DATETIME, length = 0, isNull = true, comment = "审核通过时间")
    private LocalDateTime auditSuccessTime;


}
