package com.netwisd.base.wf.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.anntation.Table;
import com.netwisd.common.db.data.DataType;
import com.netwisd.common.db.data.IModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
    import java.time.LocalDateTime;

/**
 * @Description $流程定义 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-07-01 16:18:04
 */
@Data
@Table(value = "incloud_base_wf_proc_def",comment = "流程定义")
@TableName("incloud_base_wf_proc_def")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "流程定义 Entity")
public class WfProcDef extends IModel<WfProcDef> {
    /**
     * camunda_procdef_id
     * 流程定义ID
     */
    @ApiModelProperty(value="流程定义ID")
    @TableField(value="camunda_procdef_id")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = false, comment = "流程定义ID")
    private String camundaProcdefId;
    /**
     * procdef_name
     * 流程定义名称
     */
    @ApiModelProperty(value="流程定义名称")
    @TableField(value="procdef_name")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = false, comment = "流程定义名称")
    private String procdefName;
    /**
     * procdef_name_abbr
     * 流程定义名称-简写
     */
    @ApiModelProperty(value="流程定义名称-简写")
    @TableField(value="procdef_name_abbr")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = false, comment = "流程定义名称-简写")
    private String procdefNameAbbr;
    /**
     * camunda_procdef_key
     * 流程定义key——全局唯一
     */
    @ApiModelProperty(value="流程定义key——全局唯一")
    @TableField(value="camunda_procdef_key")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = false, comment = "流程定义key——全局唯一")
    private String camundaProcdefKey;
    /**
     * procdef_version
     * 流程版本
     */
    @ApiModelProperty(value="流程版本")
    @TableField(value="procdef_version")
    @Column(type = DataType.INT, length = 2,  isNull = false, comment = "流程版本")
    private Integer procdefVersion;
    /**
     * deployment_id
     * 部署ID
     */
    @ApiModelProperty(value="部署ID")
    @TableField(value="deployment_id")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = false, comment = "部署ID")
    private String deploymentId;
    /**
     * resource_name
     * 资源名称
     */
    @ApiModelProperty(value="资源名称")
    @TableField(value="resource_name")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = false, comment = "资源名称")
    private String resourceName;
    /**
     * suspention_state
     * 挂起状态
     */
    @ApiModelProperty(value="挂起状态")
    @TableField(value="suspention_state")
    @Column(type = DataType.INT, length = 1,  isNull = false, comment = "挂起状态")
    private Integer suspentionState;
    /**
     * tenant_id
     * 租户ID
     */
    @ApiModelProperty(value="租户ID")
    @TableField(value="tenant_id")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "租户ID")
    private String tenantId;
    /**
     * version_tag
     * 版本标识
     */
    @ApiModelProperty(value="版本标识")
    @TableField(value="version_tag")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "版本标识")
    private String versionTag;
    /**
     * startable
     * 开启状态
     */
    @ApiModelProperty(value="开启状态")
    @TableField(value="startable")
    @Column(type = DataType.INT, length = 1,  isNull = false, comment = "开启状态")
    private Integer startable;
    /**
     * deploy_time
     * 部署时间
     */
    @ApiModelProperty(value="部署时间")
    @TableField(value="deploy_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = false, comment = "部署时间")
    private LocalDateTime deployTime;
    /**
     * data_source
     * 数据来源
     */
    @ApiModelProperty(value="数据来源")
    @TableField(value="data_source")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = true, comment = "数据来源")
    private String dataSource;
    /**
     * current_version
     * 当前版本
     */
    @ApiModelProperty(value="当前版本")
    @TableField(value="current_version")
    @Column(type = DataType.INT, length = 11,  isNull = false, comment = "当前版本")
    private Integer currentVersion;

    /**
     * procdef_type_id
     * 流程分类ID
     */
    @ApiModelProperty(value="流程分类ID")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    @TableField(value="procdef_type_id")
    @Column(type = DataType.BIGINT, length = 50,  isNull = false, comment = "流程分类ID")
    private Long procdefTypeId;
    /**
     * procdef_type_name
     * 流程分类名称
     */
    @ApiModelProperty(value="流程分类名称")
    @TableField(value="procdef_type_name")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = false, comment = "流程分类名称")
    private String procdefTypeName;


    /**
     * remind_sign
     * 消息提醒状态
     */
    @ApiModelProperty(value="消息提醒状态")
    @TableField(value="remind_sign")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "消息提醒状态")
    private String remindSign;

    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    @TableField(value="remark")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "备注")
    private String remark;

    /**
     * isClone
     * 是否clone
     */
    @ApiModelProperty(value="是否clone")
    @TableField(value="is_clone")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "是否clone")
    private Integer isClone = 0;

    /**
     * camunda_parent_proc_def_id
     * camunda 父流程定义ID
     */
    @ApiModelProperty(value="camunda 父流程定义ID")
    @TableField(value="camunda_parent_procdef_id")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "camunda 父流程定义ID")
    private String camundaParentProcdefId;

    /**
     * parent_proc_def_id
     * 父流程定义ID
     */
    @ApiModelProperty(value="父流程定义ID")
    @TableField(value="parent_procdef_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "父流程定义ID")
    private Long parentProcdefId;

    //2020.11.6新加字段
    /**
     * be_cloned_From_camunda_procdef_id
     * 如果被clone的话，记录被clone的流程定义ID
     */
    @ApiModelProperty(value="如果被clone的话，记录被clone的流程定义ID")
    @TableField(value="be_cloned_From_camunda_procdef_id")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "如果被clone的话，记录被clone的流程定义ID")
    private String beClonedFromCamundaProcdefId;

    /**
     * icon
     * 图标
     */
    @ApiModelProperty(value="图标")
    @TableField(value="icon")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = false, comment = "图标")
    private String icon;

    /**
     * is_biz_center
     * 是否加入业务中心
     */
    @ApiModelProperty(value="是否加入业务中心")
    @TableField(value="is_biz_center")
    @Column(type = DataType.INT, length = 1,  isNull = false, comment = "是否加入业务中心")
    private Integer isBizCenter;
}
