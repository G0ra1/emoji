package com.netwisd.base.portal.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
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
 * @Description $任务集成类内容-待办 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-10-27 17:07:47
 */
@Data
@Table(value = "incloud_base_portal_content_todo_tasks",comment = "任务集成类内容-待办")
@TableName("incloud_base_portal_content_todo_tasks")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "任务集成类内容-待办 Entity")
public class PortalContentTodoTasks extends IModel<PortalContentTodoTasks> {

    /**
     * starter_id
     * 起草人 身份证号
     */
    @ApiModelProperty(value="起草人 身份证号")
    @TableField(value="starter_id_card")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "起草人 身份证号")
    private String starterIdCard;
    /**
     * starter_name
     * 起草人名称
     */
    @ApiModelProperty(value="起草人名称")
    @TableField(value="starter_name")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "起草人名称")
    private String starterName;
    /**
     * starter_dept_id
     * 起草人部门ID
     */
    @ApiModelProperty(value="起草人部门ID")
    @TableField(value="starter_dept_id")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "起草人部门ID")
    private String starterDeptId;
    /**
     * starter_dept_name
     * 起草人部门名称
     */
    @ApiModelProperty(value="起草人部门名称")
    @TableField(value="starter_dept_name")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "起草人部门名称")
    private String starterDeptName;
    /**
     * starter_org_id
     * 起草人机构ID
     */
    @ApiModelProperty(value="起草人机构ID")
    @TableField(value="starter_org_id")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "起草人机构ID")
    private String starterOrgId;
    /**
     * starter_org_name
     * 起草人机构名称
     */
    @ApiModelProperty(value="起草人机构名称")
    @TableField(value="starter_org_name")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "起草人机构名称")
    private String starterOrgName;
    /**
     * apply_time
     * 申请时间
     */
    @ApiModelProperty(value="申请时间")
    @TableField(value="apply_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "申请时间")
    private LocalDateTime applyTime;
    /**
     * reason
     * 事由
     */
    @ApiModelProperty(value="事由")
    @TableField(value="reason")
    @Column(type = DataType.VARCHAR, length = 500,  isNull = true, comment = "事由")
    private String reason;
    /**
     * procins_id
     * 流程实例ID
     */
    @ApiModelProperty(value="流程实例ID")
    @TableField(value="procins_id")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "流程实例ID")
    private String procinsId;
    /**
     * procins_name
     * 流程实例名称
     */
    @ApiModelProperty(value="流程实例名称")
    @TableField(value="procins_name")
    @Column(type = DataType.VARCHAR, length = 1024,  isNull = false, comment = "流程实例名称")
    private String procinsName;
    /**
     * current_node_name
     * 当前节点名称
     */
    @ApiModelProperty(value="当前节点名称")
    @TableField(value="current_node_name")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "当前节点名称")
    private String currentNodeName;
    /**
     * biz_key
     * 业务单据号/流水号
     */
    @ApiModelProperty(value="业务单据号/流水号")
    @TableField(value="biz_key")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "业务单据号/流水号")
    private String bizKey;
    /**
     * cliam_time
     * 签收时间
     */
    @ApiModelProperty(value="签收时间")
    @TableField(value="cliam_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "签收时间")
    private LocalDateTime cliamTime;
    /**
     * task_state
     * 任务状态1.运行2.挂起/中止
     */
    @ApiModelProperty(value="任务状态1.运行2.挂起/中止")
    @TableField(value="task_state")
    @Column(type = DataType.INT, length = 2,  isNull = true, comment = "任务状态1.运行2.挂起/中止")
    private Integer taskState;
    /**
     * handle_start_time
     * 办理开始时间
     */
    @ApiModelProperty(value="办理开始时间")
    @TableField(value="handle_start_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "办理开始时间")
    private LocalDateTime handleStartTime;
    /**
     * ownner_id_card
     * 任务拥有人/实际委办人 身份证号
     */
    @ApiModelProperty(value="任务拥有人/实际委办人 身份证号")
    @TableField(value="ownner_id_card")
    @Column(type = DataType.VARCHAR, length = 20,  isNull = true, comment = "任务拥有人/实际委办人 身份证号")
    private String ownnerIdCard;
    /**
     * ownner_name
     * 任务拥有人/实际委办人名称
     */
    @ApiModelProperty(value="任务拥有人/实际委办人名称")
    @TableField(value="ownner_name")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "任务拥有人/实际委办人名称")
    private String ownnerName;
    /**
     * assignee_id
     * 任务办理人 身份证号
     */
    @ApiModelProperty(value="任务办理人 身份证号")
    @TableField(value="assignee_id_card")
    @Column(type = DataType.VARCHAR, length = 20,  isNull = false, comment = "任务办理人 身份证号")
    private String assigneeIdCard;
    /**
     * assignee_name
     * 任务办理人名称
     */
    @ApiModelProperty(value="任务办理人名称")
    @TableField(value="assignee_name")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = false, comment = "任务办理人名称")
    private String assigneeName;
    /**
     * is_draft
     * 是否草稿数据 0否 1是
     */
    @ApiModelProperty(value="是否草稿数据 0否 1是")
    @TableField(value="is_draft")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "是否草稿数据 0否 1是")
    private Integer isDraft;
    /**
     * sys_pc_biz_url
     * PC业务系统表单详情页面URL
     */
    @ApiModelProperty(value="PC业务系统表单详情页面URL")
    @TableField(value="sys_pc_biz_url")
    @Column(type = DataType.VARCHAR, length = 800,  isNull = false, comment = "PC业务系统表单详情页面URL")
    private String sysPcBizUrl;

    /**
     * sys_app_biz_url
     * APP 业务系统表单详情页面URL
     */
    @ApiModelProperty(value="APP 业务系统表单详情页面URL")
    @TableField(value="sys_app_biz_url")
    @Column(type = DataType.VARCHAR, length = 800,  isNull = false, comment = "APP 业务系统表单详情页面URL")
    private String sysAppBizUrl;

    /**
     * sys_biz_id
     * 所传数据的系统业务id-用来和业务系统做一对一关系使用
     */
    @ApiModelProperty(value="所传数据的系统业务id-用来和业务系统做一对一关系使用")
    @TableField(value="sys_biz_id")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = false, comment = "所传数据的系统业务id-用来和业务系统做一对一关系使用")
    private String sysBizId;
    /**
     * sys_biz_code
     * 业务系统code
     */
    @ApiModelProperty(value="业务系统code")
    @TableField(value="sys_biz_code")
    @Column(type = DataType.VARCHAR, length = 16,  isNull = false, comment = "业务系统code")
    private String sysBizCode;
    /**
     * sys_biz_classify
     * 所传数据的系统业务类型(中文,主数据系统无法获取业务系统类型)
     */
    @ApiModelProperty(value="所传数据的系统业务类型(中文)")
    @TableField(value="sys_biz_classify")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "所传数据的系统业务类型(中文,主数据系统无法获取业务系统类型)")
    private String sysBizClassify;

    /**
     * msg_id
     * 消息id
     */
    @ApiModelProperty(value = "消息id")
    @TableField(value="msg_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "消息id")
    private Long msgId;
}
