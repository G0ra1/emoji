package com.netwisd.base.model.entity;

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

@Data
@Table(value = "incloud_base_model_db_ds", comment = "数据源")
@TableName("incloud_base_model_db_ds")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "数据源 Entity")
public class DbDs extends IModel<DbDs> {

    /**
     * sysCode
     * 子系统Code
     */
    @ApiModelProperty(value = "子系统Code")
    @TableField(value = "sys_code")
    @Column(length = 100, comment = "子系统Code")
    private String sysCode;

    /**
     * sysName
     * 子系统名称
     */
    @ApiModelProperty(value = "子系统名称")
    @TableField(value = "sys_name")
    @Column(length = 100, comment = "子系统名称")
    private String sysName;

    /**
     * pool_name
     * 数据源
     */
    @ApiModelProperty(value = "数据源")
    @TableField(value = "pool_name")
    @Column(length = 50, isNull = false, comment = "数据源")
    private String poolName;

    /**
     * type
     * 类型1：mysql 2：oracle
     */
    @ApiModelProperty(value = "类型")
    @TableField(value = "type")
    @Column(type = DataType.VARCHAR, length = 30, isNull = false, comment = "数据源类型")
    private String type;

    /**
     * username
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    @TableField(value = "username")
    @Column(length = 50, isNull = false, comment = "用户名")
    private String username;

    /**
     * password
     * 密码
     */
    @ApiModelProperty(value = "密码")
    @TableField(value = "password")
    @Column(isNull = false, comment = "密码")
    private String password;

    /**
     * url
     * url
     */
    @ApiModelProperty(value = "url")
    @TableField(value = "url")
    @Column(isNull = false, comment = "url")
    private String url;

    /**
     * is_enable
     * 是否启用
     */
    @ApiModelProperty(value = "是否启用")
    @TableField(value = "is_enable")
    @Column(type = DataType.INT, length = 1, isNull = false, comment = "是否启用")
    private Integer isEnable;

    /**
     * description
     * 描述
     */
    @ApiModelProperty(value = "描述")
    @TableField(value = "description")
    @Column(length = 50, comment = "描述")
    private String description;
}
