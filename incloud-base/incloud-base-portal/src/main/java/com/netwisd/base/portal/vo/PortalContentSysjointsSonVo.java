package com.netwisd.base.portal.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 系统集成类内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-07 10:19:41
 */
@Data
@ApiModel(value = "系统集成类内容发布子表 Vo")
public class PortalContentSysjointsSonVo extends IVo{

    /**
     * portal_id
     * 所属门户ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="所属门户ID")
    private Long portalId;
    /**
     * portal_name
     * 门户名称
     */
    @ApiModelProperty(value="门户名称")
    private String portalName;
    /**
     * part_id
     * 所属栏目ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="所属栏目ID")
    private Long partId;
    /**
     * part_code
     * 所属栏目CODE
     */
    @ApiModelProperty(value="所属栏目CODE")
    private String partCode;
    /**
     * part_name
     * 所属栏目NAME
     */
    @ApiModelProperty(value="所属栏目NAME")
    private String partName;
    /**
     * part_type_id
     * 栏目类型ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="栏目类型ID")
    private Long partTypeId;
    /**
     * part_type_name
     * 栏目类型名称
     */
    @ApiModelProperty(value="栏目类型名称")
    private String partTypeName;
    /**
     * link_sysjoints_id
     * 关联主表id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="关联主表id")
    private Long linkSysjointsId;
    /**
     * sys_name
     * 系统名称
     */
    @ApiModelProperty(value="系统名称")
    private String sysName;
    /**
     * sys_code
     * 系统标识
     */
    @ApiModelProperty(value="系统标识")
    private String sysCode;
    /**
     * sys_url
     * 系统地址
     */
    @ApiModelProperty(value="系统地址")
    private String sysUrl;
    /**
     * mobile_sys_url
     * 移动端系统地址
     */
    @ApiModelProperty(value="移动端系统地址")
    private String mobileSysUrl;
    /**
     * task_count_url
     * 获取待办数量url
     */
    @ApiModelProperty(value="获取待办数量url")
    private String taskCountUrl;
    /**
     * sys_img_url
     * 显示图标
     */
    @ApiModelProperty(value="显示图标")
    private String sysImgUrl;
    /**
     * headers
     * HEADERS
     */
    
    @ApiModelProperty(value="HEADERS")
    private String headers;
    /**
     * sys_req
     * 数据源请求方式 0GET;1POST
     */
    
    @ApiModelProperty(value="数据源请求方式 0GET;1POST")
    private Integer sysReq;
    /**
     * sys_params_type
     * 数据源PARAMS类型 0params1body
     */
    
    @ApiModelProperty(value="数据源PARAMS类型 0params1body")
    private Integer sysParamsType;
    /**
     * sys_params_val
     * 数据源PARAMS值
     */
    
    @ApiModelProperty(value="数据源PARAMS值")
    private String sysParamsVal;
    /**
     * hits
     * 点击量
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="点击量")
    private Long hits;
    /**
     * apply_user_id
     * 申请人id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="申请人id")
    private Long applyUserId;
    /**
     * apply_user_name
     * 申请人name
     */
    @ApiModelProperty(value="申请人name")
    private String applyUserName;
    /**
     * is_have_token
     * 是否携带token 0不带 1带
     */
    @ApiModelProperty(value="是否携带token 0不带 1带")
    private Integer isHaveToken;
}
