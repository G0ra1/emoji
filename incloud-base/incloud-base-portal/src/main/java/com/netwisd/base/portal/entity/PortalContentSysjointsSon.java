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
 * @Description $系统集成类内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-07 10:19:41
 */
@Data
@Table(value = "incloud_base_portal_content_sysjoints_son",comment = "系统集成类内容发布子表")
@TableName("incloud_base_portal_content_sysjoints_son")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "系统集成类内容发布子表 Entity")
public class PortalContentSysjointsSon extends IModel<PortalContentSysjointsSon> {

    /**
     * link_sysjoints_id
     * 关联主表id
     */
    @ApiModelProperty(value="关联主表id")
    @TableField(value="link_sysjoints_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "关联主表id")
    private Long linkSysjointsId;
    /**
     * sys_name
     * 系统名称
     */
    @ApiModelProperty(value="系统名称")
    @TableField(value="sys_name")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "系统名称")
    private String sysName;
    /**
     * sys_code
     * 系统标识
     */
    @ApiModelProperty(value="系统标识")
    @TableField(value="sys_code")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "系统标识")
    private String sysCode;
    /**
     * sys_url
     * 系统地址
     */
    @ApiModelProperty(value="系统地址")
    @TableField(value="sys_url")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "系统地址")
    private String sysUrl;
    /**
     * task_count_url
     * 获取待办数量url
     */
    @ApiModelProperty(value="获取待办数量url")
    @TableField(value="task_count_url")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "获取待办数量url")
    private String taskCountUrl;
    /**
     * mobile_sys_url
     * 移动端系统地址
     */
    @ApiModelProperty(value="移动端系统地址")
    @TableField(value="mobile_sys_url")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "移动端系统地址")
    private String mobileSysUrl;
    /**
     * sys_img_url
     * 显示图标
     */
    @ApiModelProperty(value="显示图标")
    @TableField(value="sys_img_url")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "显示图标")
    private String sysImgUrl;
    /**
     * headers
     * HEADERS
     */
    @ApiModelProperty(value="HEADERS")
    @TableField(value="headers")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "HEADERS")
    private String headers;
    /**
     * sys_req
     * 数据源请求方式 0GET;1POST
     */
    @ApiModelProperty(value="数据源请求方式 0GET;1POST")
    @TableField(value="sys_req")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "数据源请求方式 0GET;1POST")
    private Integer sysReq;
    /**
     * sys_params_type
     * 数据源PARAMS类型 0params1body
     */
    @ApiModelProperty(value="数据源PARAMS类型 0params1body")
    @TableField(value="sys_params_type")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "数据源PARAMS类型 0params1body")
    private Integer sysParamsType;
    /**
     * sys_params_val
     * 数据源PARAMS值
     */
    @ApiModelProperty(value="数据源PARAMS值")
    @TableField(value="sys_params_val")
    @Column(type = DataType.VARCHAR, length = 500,  isNull = true, comment = "数据源PARAMS值")
    private String sysParamsVal;
    /**
     * hits
     * 点击量
     */
    @ApiModelProperty(value="点击量")
    @TableField(value="hits")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "点击量")
    private Long hits;
    /**
     * apply_user_id
     * 申请人id
     */
    @ApiModelProperty(value="申请人id")
    @TableField(value="apply_user_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "申请人id")
    private Long applyUserId;
    /**
     * apply_user_name
     * 申请人name
     */
    @ApiModelProperty(value="申请人name")
    @TableField(value="apply_user_name")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "申请人name")
    private String applyUserName;
    /**
     * is_have_token
     * 是否携带token 0不带 1带
     */
    @ApiModelProperty(value="是否携带token 0不带 1带")
    @TableField(value="is_have_token")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "是否携带token 0不带 1带")
    private Integer isHaveToken;
}
