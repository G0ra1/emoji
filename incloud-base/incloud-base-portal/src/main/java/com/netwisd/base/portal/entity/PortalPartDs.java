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
 * @Description 栏目管理数据源 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-13 20:07:37
 */
@Data
@Table(value = "incloud_base_portal_part_ds",comment = "栏目管理数据源")
@TableName("incloud_base_portal_part_ds")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "栏目管理 Entity")
public class PortalPartDs extends IModel<PortalPartDs> {

    /**
     * part_type_id
     * 栏目ID
     */
    @ApiModelProperty(value="栏目ID")
    @TableField(value="part_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "栏目ID")
    private Long partId;
    /**
     * part_type_name
     * 栏目名称
     */
    @ApiModelProperty(value="栏目名称")
    @TableField(value="part_name")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = false, comment = "栏目名称")
    private String partName;
    /**
     * ds_id
     * 数据源ID
     */
    @ApiModelProperty(value="数据源ID")
    @TableField(value="ds_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "数据源ID")
    private Long dsId;
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
    @Column(type = DataType.INT, length = 11,  isNull = true, comment = "数据源类型 0rest-1websocket")
    private Integer dsType;
    /**
     * ds_code
     * 数据源CODE
     */
    @ApiModelProperty(value="数据源CODE")
    @TableField(value="ds_code")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "数据源CODE")
    private String dsCode;
    /**
     * ds_req
     * 数据源请求方式 0GET;1POST
     */
    @ApiModelProperty(value="数据源请求方式 0GET;1POST")
    @TableField(value="ds_req")
    @Column(type = DataType.INT, length = 11,  isNull = true, comment = "数据源请求方式 0GET;1POST")
    private Integer dsReq;
    /**
     * ds_headers
     * 数据源HEADERS
     */
    @ApiModelProperty(value="数据源HEADERS")
    @TableField(value="ds_headers")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "数据源HEADERS")
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
    @Column(type = DataType.VARCHAR, length = 1024,  isNull = true, comment = "数据源PARAMS值")
    private String dsParamsVal;
    /**
     * ds_url
     * 数据源URL
     */
    @ApiModelProperty(value="数据源URL")
    @TableField(value="ds_url")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "数据源URL")
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
