package com.netwisd.base.wf.entity;

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
import java.util.Date;

/**
 * @Description 传阅回复 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-09-18 22:12:53
 */
@Data
@Table(value = "incloud_base_wf_duplicate_response",comment = "传阅回复")
@TableName("incloud_base_wf_duplicate_response")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "传阅回复 Entity")
public class WfDuplicateResponse extends IModel<WfDuplicateResponse> {

    /**
     * in_duplicate_task_id
     * 发起的任务id
     */
    @ApiModelProperty(value="发起的任务id")
    @TableField(value="in_duplicate_task_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "发起的任务id")
    private Long inDuplicateTaskId;
    /**
     * ownner
     * 发起人id
     */
    @ApiModelProperty(value="发起人id")
    @TableField(value="ownner")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "发起人id")
    private String ownner;
    /**
     * ownner_name
     * 发起人名称
     */
    @ApiModelProperty(value="发起人名称")
    @TableField(value="ownner_name")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "发起人名称")
    private String ownnerName;
    /**
     * out_duplicate_task_id
     * 接收人任务id
     */
    @ApiModelProperty(value="接收人任务id")
    @TableField(value="out_duplicate_task_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "接收人任务id")
    private Long outDuplicateTaskId;
    /**
     * assignee
     * 接收人id
     */
    @ApiModelProperty(value="接收人id")
    @TableField(value="assignee")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "接收人id")
    private String assignee;
    /**
     * assignee_name
     * 接收人名称
     */
    @ApiModelProperty(value="接收人名称")
    @TableField(value="assignee_name")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "接收人名称")
    private String assigneeName;
    /**
     * content
     * 内容
     */
    @ApiModelProperty(value="内容")
    @TableField(value="content")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "内容")
    private String content;
}
