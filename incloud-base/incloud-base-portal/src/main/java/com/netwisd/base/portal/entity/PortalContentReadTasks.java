package com.netwisd.base.portal.entity;

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
 * @Description $任务集成类-已阅 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-10-28 17:54:38
 */
@Data
@Table(value = "incloud_base_portal_content_read_tasks",comment = "任务集成类-已阅")
@TableName("incloud_base_portal_content_read_tasks")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "任务集成类-已阅 Entity")
public class PortalContentReadTasks extends IModel<PortalContentReadTasks> {

    /**
     * starter_id_card
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
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "起草人机构名称")
    private String starterOrgName;
    /**
     * apply_time
     * 申请时间/发起时间
     */
    @ApiModelProperty(value="申请时间/发起时间")
    @TableField(value="apply_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "申请时间/发起时间")
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
     * accept_time
     * 接受时间
     */
    @ApiModelProperty(value="接受时间")
    @TableField(value="accept_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "接受时间")
    private LocalDateTime acceptTime;
    /**
     * ownner_id_card
     * 发起传阅人身份证号
     */
    @ApiModelProperty(value="发起传阅人身份证号")
    @TableField(value="ownner_id_card")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "发起传阅人身份证号")
    private String ownnerIdCard;
    /**
     * ownner_name
     * 发起传阅人名称
     */
    @ApiModelProperty(value="发起传阅人名称")
    @TableField(value="ownner_name")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "发起传阅人名称")
    private String ownnerName;
    /**
     * assignee_id_card
     * 被传阅人身份证号
     */
    @ApiModelProperty(value="被传阅人身份证号")
    @TableField(value="assignee_id_card")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = false, comment = "被传阅人身份证号")
    private String assigneeIdCard;
    /**
     * assignee_name
     * 被传阅人名称
     */
    @ApiModelProperty(value="被传阅人名称")
    @TableField(value="assignee_name")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = false, comment = "被传阅人名称")
    private String assigneeName;
    /**
     * handle_assignee_id_card
     * 当前活动办理人 身份证
     */
    @ApiModelProperty(value="当前活动办理人 身份证")
    @TableField(value="handle_assignee_id_card")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "当前活动办理人 身份证")
    private String handleAssigneeIdCard;
    /**
     * handle_assignee_name
     * 当前活动办理人名称
     */
    @ApiModelProperty(value="当前活动办理人名称")
    @TableField(value="handle_assignee_name")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "当前活动办理人名称")
    private String handleAssigneeName;
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
     * sys_biz_code
     * 业务系统系统code
     */
    @ApiModelProperty(value="业务系统系统code")
    @TableField(value="sys_biz_code")
    @Column(type = DataType.VARCHAR, length = 16,  isNull = false, comment = "业务系统系统code")
    private String sysBizCode;
    /**
     * sys_biz_id
     * 所传数据的系统业务id
     */
    @ApiModelProperty(value="所传数据的系统业务id")
    @TableField(value="sys_biz_id")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = false, comment = "所传数据的系统业务id")
    private String sysBizId;
    /**
     * sys_biz_classify
     * 所传数据的系统业务类型(中文)
     */
    @ApiModelProperty(value="所传数据的系统业务类型(中文)")
    @TableField(value="sys_biz_classify")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "所传数据的系统业务类型(中文)")
    private String sysBizClassify;
}
