package com.netwisd.biz.mdm.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 推送消息 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-10-16 11:54:52
 */
@Data
@ApiModel(value = "推送消息 Vo")
public class PushMsgVo extends IVo {

    /**
     * sys_code
     * 系统code
     */
    @ApiModelProperty(value="系统code")
    private String sysCode;
    /**
     * module_code
     * 模块code
     */
    @ApiModelProperty(value="模块code")
    private String moduleCode;
    /**
     * access_token
     * token
     */
    @ApiModelProperty(value="token")
    private String accessToken;
    /**
     * user_id
     * 用户id
     */
    @ApiModelProperty(value="用户id")
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
    private String msg;
    /**
     * priority
     * 优先级
     */
    @ApiModelProperty(value="优先级")
    private Integer priority;
    /**
     * state
     * 状态
     */
    @ApiModelProperty(value="状态")
    private Integer state;
}
