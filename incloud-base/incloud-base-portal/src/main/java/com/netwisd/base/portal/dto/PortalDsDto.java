package com.netwisd.base.portal.dto;

import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * @Description 数据源管理 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-10 19:25:49
 */
@Data
@ApiModel(value = "数据源管理 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PortalDsDto extends IDto {

    public PortalDsDto(Args args){
        super(args);
    }

    /**
     * ds_name
     * 数据源名称
     */
    @ApiModelProperty(value="数据源名称")
    
    private String dsName;

    /**
     * ds_type
     * 数据源类型 0rest-1websocket
     */
    @ApiModelProperty(value="数据源类型 0rest-1websocket")
    @Valid(length = 32)
    private Integer dsType;

    /**
     * ds_code
     * 数据源CODE
     */
    @ApiModelProperty(value="数据源CODE")
    @Valid(length = 32)
    private String dsCode;

    /**
     * ds_req
     * 数据源请求方式 0GET;1POST
     */
    @ApiModelProperty(value="数据源请求方式 0GET;1POST")
    @Valid(length = 32)
    private Integer dsReq;

    /**
     * ds_headers
     * 数据源HEADERS
     */
    @ApiModelProperty(value="数据源HEADERS")

    private String dsHeaders;

    /**
     * ds_params_type
     * 数据源PARAMS类型 0params1body
     */
    @ApiModelProperty(value="数据源PARAMS类型 0params1body")

    private Integer dsParamsType;

    /**
     * ds_params_val
     * 数据源PARAMS值
     */
    @ApiModelProperty(value="数据源PARAMS值")
    
    private String dsParamsVal;

    /**
     * ds_url
     * 数据源URL
     */
    @ApiModelProperty(value="数据源URL")
    @Valid(length = 32)
    private String dsUrl;

    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    
    private String remark;

}
