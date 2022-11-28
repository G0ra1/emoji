package com.netwisd.base.portal.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.base.common.data.IDto;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.data.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * @Description 任务集成类-已阅 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-10-28 17:54:38
 */
@Data
@ApiModel(value = "任务集成类-已阅 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PortalContentReadTasksDto extends IDto{

    public PortalContentReadTasksDto(Args args){
        super(args);
    }

    /**
     * starter_id_card
     * 起草人 身份证号
     */
    @ApiModelProperty(value="起草人 身份证号")
    private String starterIdCard;

    /**
     * starter_name
     * 起草人名称
     */
    
    @Valid(length = 50)
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
    
    @Valid(length = 50)
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
    
    @Valid(length = 50)
    @ApiModelProperty(value="起草人机构名称")
    private String starterOrgName;

    /**
     * apply_time
     * 申请时间/发起时间
     */
    
    @Valid(length = 0)
    @ApiModelProperty(value="申请时间/发起时间")
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
    
    @Valid(length = 32)
    @ApiModelProperty(value="流程实例名称")
    private String procinsName;

    /**
     * current_node_name
     * 当前节点名称
     */
    
    @Valid(length = 32)
    @ApiModelProperty(value="当前节点名称")
    private String currentNodeName;

    /**
     * biz_key
     * 业务单据号/流水号
     */
    
    @Valid(length = 50)
    @ApiModelProperty(value="业务单据号/流水号")
    private String bizKey;

    /**
     * accept_time
     * 接受时间
     */
    
    
    @ApiModelProperty(value="接受时间")
    private LocalDateTime acceptTime;

    /**
     * ownner_id_card
     * 发起传阅人身份证号
     */
    @ApiModelProperty(value="发起传阅人身份证号")
    private String ownnerIdCard;

    /**
     * ownner_name
     * 发起传阅人名称
     */
    
    
    @ApiModelProperty(value="发起传阅人名称")
    private String ownnerName;

    /**
     * assignee_id_card
     * 被传阅人身份证号
     */
    @ApiModelProperty(value="被传阅人身份证号")
    private String assigneeIdCard;

    /**
     * assignee_name
     * 被传阅人名称
     */
    
    @Valid(length = 50)
    @ApiModelProperty(value="被传阅人名称")
    private String assigneeName;

    /**
     * handle_assignee_id_card
     * 当前活动办理人 身份证
     */
    @ApiModelProperty(value="当前活动办理人 身份证")
    private String handleAssigneeIdCard;

    /**
     * handle_assignee_name
     * 当前活动办理人名称
     */
    
    
    @ApiModelProperty(value="当前活动办理人名称")
    private String handleAssigneeName;

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
    
    @Valid(length = 16)
    @ApiModelProperty(value="业务系统系统code")
    private String sysBizCode;

    /**
     * sys_biz_id
     * 所传数据的系统业务id
     */
    
    @Valid(length = 64)
    @ApiModelProperty(value="所传数据的系统业务id")
    private String sysBizId;

    /**
     * sys_biz_classify
     * 所传数据的系统业务类型(中文)
     */
    
    @Valid(length = 64)
    @ApiModelProperty(value="所传数据的系统业务类型(中文)")
    private String sysBizClassify;

    @ApiModelProperty(value="接受时间范围开始")
    private LocalDateTime sAcceptTime;

    @ApiModelProperty(value="接受时间范围结束")
    private LocalDateTime eAcceptTime;

}
