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
 * @Description $数据源管理 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-10 19:25:49
 */
@Data
@Table(value = "incloud_base_portal_ds",comment = "数据源管理")
@TableName("incloud_base_portal_ds")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "数据源管理 Entity")
public class PortalDs extends IModel<PortalDs> {

    /**
     * ds_name
     * 数据源名称
     */
    @ApiModelProperty(value="数据源名称")
    @TableField(value="ds_name")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = false, comment = "数据源名称")
    private String dsName;
    /**
     * ds_type
     * 数据源类型 0rest-1websocket
     */
    @ApiModelProperty(value="数据源类型 0rest-1websocket")
    @TableField(value="ds_type")
    @Column(type = DataType.INT, length = 11,  isNull = false, comment = "数据源类型 0rest-1websocket")
    private Integer dsType;
    /**
     * ds_code
     * 数据源CODE
     */
    @ApiModelProperty(value="数据源CODE")
    @TableField(value="ds_code")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = false, comment = "数据源CODE")
    private String dsCode;
    /**
     * ds_req
     * 数据源请求方式 0GET;1POST
     */
    @ApiModelProperty(value="数据源请求方式 0GET;1POST")
    @TableField(value="ds_req")
    @Column(type = DataType.INT, length = 11,  isNull = false, comment = "数据源请求方式 0GET;1POST")
    private Integer dsReq;
    /**
     * ds_headers
     * 数据源HEADERS
     */
    @ApiModelProperty(value="数据源HEADERS")
    @TableField(value="ds_headers")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "数据源HEADERS")
    private String dsHeaders;
    /**
     * ds_params_type
     * 数据源PARAMS类型 0params1body
     */
    @ApiModelProperty(value="数据源PARAMS类型 0params1body")
    @TableField(value="ds_params_type")
    @Column(type = DataType.INT, length = 11,  isNull = true, comment = "数据源PARAMS类型 0params1body")
    private Integer dsParamsType;
    /**
     * ds_params_val
     * 数据源PARAMS值
     */
    @ApiModelProperty(value="数据源PARAMS值")
    @TableField(value="ds_params_val")
    @Column(type = DataType.VARCHAR, length = 2000,  isNull = true, comment = "数据源PARAMS值")
    private String dsParamsVal;
    /**
     * ds_url
     * 数据源URL
     */
    @ApiModelProperty(value="数据源URL")
    @TableField(value="ds_url")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = false, comment = "数据源URL")
    private String dsUrl;
    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    @TableField(value="remark")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "备注")
    private String remark;
}
