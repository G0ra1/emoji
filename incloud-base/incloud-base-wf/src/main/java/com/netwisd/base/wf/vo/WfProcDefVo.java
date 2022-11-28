package com.netwisd.base.wf.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 流程定义 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-07-01 16:18:04
 */
@Data
@ApiModel(value = "流程定义 Vo")
public class WfProcDefVo extends IVo{
    /**
     * camunda_procdef_id
     * 流程定义ID
     */
    @ApiModelProperty(value="流程定义ID")
    private String camundaProcdefId;
    /**
     * procdef_name
     * 流程定义名称
     */
    @ApiModelProperty(value="流程定义名称")
    private String procdefName;
    /**
     * procdef_name_abbr
     * 流程定义名称-简写
     */
    @ApiModelProperty(value="流程定义名称-简写")
    private String procdefNameAbbr;
    /**
     * camunda_procdef_key
     * 流程定义key——全局唯一
     */
    @ApiModelProperty(value="流程定义key——全局唯一")
    private String camundaProcdefKey;
    /**
     * procdef_version
     * 流程版本
     */
    @ApiModelProperty(value="流程版本")
    private Integer procdefVersion;
    /**
     * deployment_id
     * 部署ID
     */
    @ApiModelProperty(value="部署ID")
    private String deploymentId;
    /**
     * resource_name
     * 资源名称
     */
    @ApiModelProperty(value="资源名称")
    private String resourceName;
    /**
     * suspention_state
     * 挂起状态
     */
    @ApiModelProperty(value="挂起状态")
    private Integer suspentionState;
    /**
     * tenant_id
     * 租户ID
     */
    @ApiModelProperty(value="租户ID")
    private String tenantId;
    /**
     * version_tag
     * 版本标识
     */
    @ApiModelProperty(value="版本标识")
    private String versionTag;
    /**
     * startable
     * 开启状态
     */
    @ApiModelProperty(value="开启状态")
    private Integer startable;
    /**
     * deploy_time
     * 部署时间
     */
    @ApiModelProperty(value="部署时间")
    private LocalDateTime deployTime;
    /**
     * data_source
     * 数据来源
     */
    @ApiModelProperty(value="数据来源")
    private String dataSource;
    /**
     * current_version
     * 当前版本
     */
    @ApiModelProperty(value="当前版本")
    private Integer currentVersion;
    /**
     * procdef_type_id
     * 流程分类ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="流程分类ID")
    private Long procdefTypeId;
    /**
     * procdef_type_name
     * 流程分类名称
     */
    @ApiModelProperty(value="流程分类名称")
    private String procdefTypeName;

    /**
     * remind_sign
     * 消息提醒状态
     */
    @ApiModelProperty(value="消息提醒状态")
    private String remindSign;

    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * isClone
     * 是否clone
     */
    @ApiModelProperty(value="是否clone")
    private Integer isClone = 0;

    /**
     * camunda_parent_proc_def_id
     * camunda 父流程定义ID
     */
    @ApiModelProperty(value="camunda 父流程定义ID")
    private String camundaParentProcdefId;

    /**
     * parent_proc_def_id
     * 父流程定义ID
     */
    @ApiModelProperty(value="父流程定义ID")
    private Long parentProcdefId;

    //2020.11.6新加字段
    /**
     * is_cloned_by_procdef_id
     * 如果被clone的话，记录被clone的流程定义ID
     */
    @ApiModelProperty(value="如果被clone的话，记录被clone的流程定义ID")
    private String beClonedFromCamundaProcdefId;

    /**
     * icon
     * 图标
     */
    @ApiModelProperty(value="图标")
    private String icon;

    /**
     * 节点表单信息以及权限信息
     */
    @ApiModelProperty(value="节点表单信息以及权限信息")
    private List<WfFormDefVo> wfFormDefs;

    /**
     * 按钮信息
     */
    @ApiModelProperty(value="按钮信息")
    List<WfButtonDefVo> wfButtonDefs;

    /**
     * 流程提交信息
     */
    private WfEngineDto.RespFormDto respFormDto;

    /**
     * is_biz_center
     * 是否加入业务中心
     */
    @ApiModelProperty(value="是否加入业务中心")
    private Integer isBizCenter;


    /**
     * is_biz_center
     * 节点
     */
    @ApiModelProperty(value="是否加入业务中心")
    private WfNodeDefVo wfNodeDef;
}
