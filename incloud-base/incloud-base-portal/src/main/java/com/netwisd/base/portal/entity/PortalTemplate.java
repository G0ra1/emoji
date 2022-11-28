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
 * @Description $模板管理 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-11 00:09:45
 */
@Data
@Table(value = "incloud_base_portal_template",comment = "模板管理")
@TableName("incloud_base_portal_template")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "模板管理 Entity")
public class PortalTemplate extends IModel<PortalTemplate> {

    /**
     * portal_id
     * 所属门户
     */
    @ApiModelProperty(value="所属门户")
    @TableField(value="portal_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "所属门户")
    private Long portalId;
    /**
     * portal_name
     * 所属门户名称
     */
    @ApiModelProperty(value="所属门户名称")
    @TableField(value="portal_name")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "所属门户名称")
    private String portalName;
    /**
     * template_name
     * 模板名称
     */
    @ApiModelProperty(value="模板名称")
    @TableField(value="template_name")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "模板名称")
    private String templateName;
    /**
     * template_code
     * 模板CODE
     */
    @ApiModelProperty(value="模板CODE")
    @TableField(value="template_code")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "模板CODE")
    private String templateCode;
    /**
     * terminal
     * 所属终端 0PC 1移动；所属终端
     */
    @ApiModelProperty(value="所属终端 0PC 1移动；所属终端")
    @TableField(value="terminal")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "所属终端 0PC 1移动；所属终端")
    private Integer terminal;
    /**
     * template_data
     * 模板内容
     */
    @ApiModelProperty(value="模板内容")
    @TableField(value="template_data")
    @Column(type = DataType.TEXT, length = 0,  isNull = true, comment = "模板内容")
    private String templateData;
    /**
     * template_version
     * 模板版本
     */
    @ApiModelProperty(value="模板版本")
    @TableField(value="template_version")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "模板版本")
    private Integer templateVersion;
    /**
     * crr_version
     * 当前版本
     */
    @ApiModelProperty(value="当前版本")
    @TableField(value="crr_version")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "当前版本")
    private Integer crrVersion;
    /**
     * start_enable
     * 是否生效 1生效 0不生效
     */
    @ApiModelProperty(value="是否生效 1生效 0不生效")
    @TableField(value="start_enable")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "是否生效 1生效 0不生效")
    private Integer startEnable;
    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    @TableField(value="remark")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "备注")
    private String remark;

    /**
     * update_user_id
     * 修改人id
     */
    @ApiModelProperty(value="修改人id")
    @TableField(value="update_user_id")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "修改人id")
    private String updateUserId;
    /**
     * update_user_name
     * 修改人姓名
     */
    @ApiModelProperty(value="修改人姓名")
    @TableField(value="update_user_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "修改人姓名")
    private String updateUserName;
}
