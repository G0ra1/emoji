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
 * @Description 调整 系统集成类内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-07 14:39:52
 */
@Data
@ApiModel(value = "调整 系统集成类内容发布子表 Vo")
public class PortalContentAdjSysjointsSonVo extends IVo{

    /**
     * link_sysjoints_id
     * 关联主表id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="关联主表id")
    private Long linkSysjointsId;
    /**
     * link_sysjoints_son_id
     * 关联主数据子表对应id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="关联主数据子表对应id")
    private Long linkSysjointsSonId;
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
}
