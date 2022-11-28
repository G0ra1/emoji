package com.netwisd.base.portal.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.constants.Args;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.data.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @Description 系统集成类内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-07 10:19:41
 */
@Data
@ApiModel(value = "系统集成类内容发布子表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PortalContentSysjointsSonDto extends IDto {

    public PortalContentSysjointsSonDto(Args args){
        super(args);
    }

    /**
     * link_sysjoints_id
     * 关联主表id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    
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
    @JsonDeserialize(using = IdTypeDeserializer.class)
    
    @ApiModelProperty(value="点击量")
    private Long hits;

    /**
     * apply_user_id
     * 申请人id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    
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