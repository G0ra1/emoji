package com.netwisd.biz.mdm.entity;

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
 * @Description $推送消息 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-10-16 11:54:52
 */
@Data
@Table(value = "incloud_base_socket_push_msg",comment = "推送消息")
@TableName("incloud_base_socket_push_msg")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "推送消息 Entity")
public class PushMsg extends IModel<PushMsg> {

    /**
     * sys_code
     * 系统code
     */
    @ApiModelProperty(value="系统code")
    @TableField(value="sys_code")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = false, comment = "系统code")
    private String sysCode;
    /**
     * module_code
     * 模块code
     */
    @ApiModelProperty(value="模块code")
    @TableField(value="module_code")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = false, comment = "模块code")
    private String moduleCode;
    /**
     * access_token
     * token
     */
    @ApiModelProperty(value="token")
    @TableField(value="access_token")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "token")
    private String accessToken;
    /**
     * user_id
     * 用户id
     */
    @ApiModelProperty(value="用户id")
    @TableField(value="user_id")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = false, comment = "用户id")
    private String userId;
    /**
     * user_name
     * 用户名
     */
    @ApiModelProperty(value="用户名")
    @TableField(value="user_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "用户名")
    private String userName;
    /**
     * dept_id
     * 部门Id
     */
    @ApiModelProperty(value="部门Id")
    @TableField(value="dept_id")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "部门Id")
    private String deptId;
    /**
     * dept_name
     * 部门名称
     */
    @ApiModelProperty(value="部门名称")
    @TableField(value="dept_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "部门名称")
    private String deptName;
    /**
     * org_id
     * 组织id
     */
    @ApiModelProperty(value="组织id")
    @TableField(value="org_id")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "组织id")
    private String orgId;
    /**
     * org_name
     * 组织名称
     */
    @ApiModelProperty(value="组织名称")
    @TableField(value="org_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "组织名称")
    private String orgName;
    /**
     * msg
     * 消息体
     */
    @ApiModelProperty(value="消息体")
    @TableField(value="msg")
    @Column(type = DataType.VARCHAR, length = 4000,  isNull = false, comment = "消息体")
    private String msg;
    /**
     * priority
     * 优先级
     */
    @ApiModelProperty(value="优先级")
    @TableField(value="priority")
    @Column(type = DataType.INT, length = 1,  isNull = false, comment = "优先级")
    private Integer priority;
    /**
     * state
     * 状态
     */
    @ApiModelProperty(value="状态")
    @TableField(value="state")
    @Column(type = DataType.INT, length = 1,  isNull = false, comment = "状态")
    private Integer state;
}
