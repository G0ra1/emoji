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
 * @Description 栏目管理数据源 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-13 20:07:37
 */
@Data
@ApiModel(value = "栏目管理数据源 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PortalPartDsDto extends IDto {

    public PortalPartDsDto(Args args){
        super(args);
    }

    /**
     * part_type_id
     * 栏目ID
     */
    @ApiModelProperty(value="栏目ID")
    @Valid
    private Long partId;

    /**
     * part_type_name
     * 栏目名称
     */
    @ApiModelProperty(value="栏目名称")
    @Valid
    private String partName;

    /**
     * ds_id
     * 数据源ID
     */
    @ApiModelProperty(value="数据源ID")
    @Valid
    private Long dsId;

    /**
     * ds_name
     * 数据源名称
     */
    @ApiModelProperty(value="数据源名称")
    @Valid
    private String dsName;

    /**
     * ds_type
     * 数据源类型 0rest-1websocket
     */
    @ApiModelProperty(value="数据源类型 0rest-1websocket")

    private Integer dsType;

    /**
     * ds_code
     * 数据源CODE
     */
    @ApiModelProperty(value="数据源CODE")

    private String dsCode;

    /**
     * ds_req
     * 数据源请求方式 0GET;1POST
     */
    @ApiModelProperty(value="数据源请求方式 0GET;1POST")

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

    private String dsUrl;

    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    
    private String remark;

}
