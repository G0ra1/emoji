package com.netwisd.base.portal.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description 栏目管理数据源 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-13 20:07:37
 */
@Data
@ApiModel(value = "栏目管理数据源 Vo")
public class PortalPartDsVo extends IVo{

    /**
     * part_type_id
     * 栏目ID
     */
    @ApiModelProperty(value="栏目ID")
    private Long partId;
    /**
     * part_type_name
     * 栏目名称
     */
    @ApiModelProperty(value="栏目名称")
    private String partName;
    /**
     * ds_id
     * 数据源ID
     */
    @ApiModelProperty(value="数据源ID")
    private Long dsId;
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
