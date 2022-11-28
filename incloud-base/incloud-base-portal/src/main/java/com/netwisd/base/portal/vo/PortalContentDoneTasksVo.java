package com.netwisd.base.portal.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * @Description 任务集成类内容-已办 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-10-27 18:17:26
 */
@Data
@ApiModel(value = "任务集成类内容-已办 Vo")
public class PortalContentDoneTasksVo extends IVo{

    /**
     * starter_id
     * 起草人ID
     */
    @ApiModelProperty(value="起草人ID")
    private String starterIdCard;
    /**
     * starter_name
     * 起草人名称
     */
    
    @ApiModelProperty(value="起草人名称")
    private String starterName;
    /**
     * starter_dept_id
     * 起草人部门ID
     */
    @ApiModelProperty(value="起草人部门ID")
    private String starterDeptId;
    /**
     * starter_dept_name
     * 起草人部门名称
     */
    
    @ApiModelProperty(value="起草人部门名称")
    private String starterDeptName;
    /**
     * starter_org_id
     * 起草人机构ID
     */
    @ApiModelProperty(value="起草人机构ID")
    private String starterOrgId;
    /**
     * starter_org_name
     * 起草人机构名称
     */
    
    @ApiModelProperty(value="起草人机构名称")
    private String starterOrgName;
    /**
     * apply_time
     * 申请时间
     */
    
    @ApiModelProperty(value="申请时间")
    private LocalDateTime applyTime;
    /**
     * reason
     * 事由
     */
    
    @ApiModelProperty(value="事由")
    private String reason;
    /**
     * procins_id
     * 流程实例ID
     */
    
    @ApiModelProperty(value="流程实例ID")
    private String procinsId;
    /**
     * procins_name
     * 流程实例名称
     */
    
    @ApiModelProperty(value="流程实例名称")
    private String procinsName;
    /**
     * current_node_name
     * 当前节点名称
     */
    
    @ApiModelProperty(value="当前节点名称")
    private String currentNodeName;
    /**
     * biz_key
     * 业务单据号/流水号
     */
    
    @ApiModelProperty(value="业务单据号/流水号")
    private String bizKey;
    /**
     * cliam_time
     * 签收时间
     */
    
    @ApiModelProperty(value="签收时间")
    private LocalDateTime cliamTime;
    /**
     * task_state
     * 任务状态1.运行2.挂起/中止3.完成
     */
    
    @ApiModelProperty(value="任务状态1.运行2.挂起/中止3.完成")
    private Integer taskState;
    /**
     * handle_start_time
     * 办理开始时间
     */
    
    @ApiModelProperty(value="办理开始时间")
    private LocalDateTime handleStartTime;
    /**
     * handle_end_time
     * 办理结束时间
     */
    
    @ApiModelProperty(value="办理结束时间")
    private LocalDateTime handleEndTime;
    /**
     * ownner_id_card
     * 任务拥有人/实际委办人 身份证
     */
    @ApiModelProperty(value="任务拥有人/实际委办人身份证")
    private String ownnerIdCard;
    /**
     * ownner_name
     * 任务拥有人/实际委办人名称
     */
    
    @ApiModelProperty(value="任务拥有人/实际委办人名称")
    private String ownnerName;
    /**
     * assignee_id_card
     * 任务办理人ID
     */
    @ApiModelProperty(value="任务办理人ID")
    private String assigneeIdCard;
    /**
     * assignee_name
     * 任务办理人名称
     */
    
    @ApiModelProperty(value="任务办理人名称")
    private String assigneeName;
    /**
     * next_node_name
     * 下一节点名称
     */
    
    @ApiModelProperty(value="下一节点名称")
    private String nextNodeName;
    /**
     * next_assignee_id
     * 下一节点任务办理人 身份证号
     */
    @ApiModelProperty(value="下一节点任务办理人 身份证号")
    private String nextAssigneeId;
    /**
     * next_assignee_name
     * 下一节点任务办理人名称
     */
    
    @ApiModelProperty(value="下一节点任务办理人名称")
    private String nextAssigneeName;
    /**
     * sys_pc_biz_url
     * PC业务系统表单详情页面URL
     */
    @ApiModelProperty(value="PC业务系统表单详情页面URL")
    private String sysPcBizUrl;

    /**
     * sys_app_biz_url
     * APP 业务系统表单详情页面URL
     */
    @ApiModelProperty(value="APP 业务系统表单详情页面URL")
    private String sysAppBizUrl;
    /**
     * sys_biz_code
     * 业务系统系统code
     */
    
    @ApiModelProperty(value="业务系统系统code")
    private String sysBizCode;
    /**
     * sys_biz_id
     * 所传数据的系统业务id
     */
    
    @ApiModelProperty(value="所传数据的系统业务id")
    private String sysBizId;
    /**
     * sys_biz_classify
     * 所传数据的系统业务类型(中文,主数据系统无法获取业务系统类型)
     */
    @ApiModelProperty(value="所传数据的系统业务类型(中文)")
    private String sysBizClassify;
}
