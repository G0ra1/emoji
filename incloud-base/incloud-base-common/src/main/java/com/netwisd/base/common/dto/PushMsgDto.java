package com.netwisd.base.common.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description 推送消息 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-10-16 11:54:52
 */
@Data
@ApiModel(value = "推送消息 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PushMsgDto extends IDto{


    /**
     * sys_code
     * 系统code
     */
    @ApiModelProperty(value="系统code")
    @Valid(length = 50) 
    private String sysCode;

    /**
     * module_code
     * 模块code
     */
    @ApiModelProperty(value="模块code")
    @Valid(length = 50) 
    private String moduleCode;

    /**
     * access_token
     * token
     */
    @ApiModelProperty(value="token")
    @Valid(length = 255) 
    private String accessToken;

    /**
     * user_id
     * 用户id
     */
    @ApiModelProperty(value="用户id")
    @Valid(length = 32) 
    private String userId;

    /**
     * user_name
     * 用户名
     */
    @ApiModelProperty(value="用户名")
    
    private String userName;

    /**
     * dept_id
     * 部门Id
     */
    @ApiModelProperty(value="部门Id")
    
    private String deptId;

    /**
     * dept_name
     * 部门名称
     */
    @ApiModelProperty(value="部门名称")
    
    private String deptName;

    /**
     * org_id
     * 组织id
     */
    @ApiModelProperty(value="组织id")
    
    private String orgId;

    /**
     * org_name
     * 组织名称
     */
    @ApiModelProperty(value="组织名称")
    
    private String orgName;

    /**
     * msg
     * 消息体
     */
    @ApiModelProperty(value="消息体")
    @Valid(length = 4000) 
    private String msg;

    /**
     * priority
     * 优先级
     */
    @ApiModelProperty(value="优先级 1-5")
    @Valid(length = 1) 
    private Integer priority = 1;

    /**
     * state
     * 状态
     */
    @ApiModelProperty(value="状态——0未读，1已读")
    @Valid(length = 1) 
    private Integer state = 0;

}
