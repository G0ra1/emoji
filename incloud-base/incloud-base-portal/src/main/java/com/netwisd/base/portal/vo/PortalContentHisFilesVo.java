package com.netwisd.base.portal.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 历史 文件下载类型内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-02 15:24:56
 */
@Data
@ApiModel(value = "历史 文件下载类型内容发布 Vo")
public class PortalContentHisFilesVo extends IVo {

    /**
     * portal_id
     * 所属门户ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value = "所属门户ID")
    private Long portalId;
    /**
     * portal_name
     * 门户名称
     */

    @ApiModelProperty(value = "门户名称")
    private String portalName;
    /**
     * part_id
     * 所属栏目ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value = "所属栏目ID")
    private Long partId;
    /**
     * part_name
     * 所属栏目NAME
     */

    @ApiModelProperty(value = "所属栏目NAME")
    private String partName;
    /**
     * part_type_id
     * 所属栏目ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value = "所属栏目ID")
    private Long partTypeId;
    /**
     * part_type_name
     * 栏目类型名称
     */

    @ApiModelProperty(value = "栏目类型名称")
    private String partTypeName;
    /**
     * audit_status
     * 审批状态
     */

    @ApiModelProperty(value = "审批状态")
    private Integer auditStatus;
    /**
     * remark
     * 备注
     */

    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * camunda_procdef_key
     * camunda流程定义key
     */

    @ApiModelProperty(value = "camunda流程定义key")
    private String camundaProcdefKey;
    /**
     * procdef_name
     * 流程定义名称
     */

    @ApiModelProperty(value = "流程定义名称")
    private String procdefName;
    /**
     * camunda_procdef_id
     * camunda流程定义ID
     */

    @ApiModelProperty(value = "camunda流程定义ID")
    private String camundaProcdefId;
    /**
     * procdef_version
     * 流程版本
     */

    @ApiModelProperty(value = "流程版本")
    private Integer procdefVersion;
    /**
     * camunda_procins_id
     * camunda流程实例ID
     */

    @ApiModelProperty(value = "camunda流程实例ID")
    private String camundaProcinsId;
    /**
     * process_name
     * 流程实例名称
     */

    @ApiModelProperty(value = "流程实例名称")
    private String processName;
    /**
     * reason
     * 事由
     */

    @ApiModelProperty(value = "事由")
    private String reason;
    /**
     * biz_key
     * bizKey
     */

    @ApiModelProperty(value = "bizKey")
    private String bizKey;
    /**
     * procdef_type_id
     * 流程分类ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value = "流程分类ID")
    private Long procdefTypeId;
    /**
     * procdef_type_name
     * 流程分类名称
     */

    @ApiModelProperty(value = "流程分类名称")
    private String procdefTypeName;
    /**
     * pass_time
     * 审批通过时间
     */

    @ApiModelProperty(value = "审批通过时间")
    private LocalDateTime passTime;
    /**
     * apply_user_id
     * 申请人id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value = "申请人id")
    private Long applyUserId;
    /**
     * apply_user_name
     * 申请人name
     */
    @ApiModelProperty(value = "申请人name")
    private String applyUserName;
    /**
     * filesSons
     * 子表集合
     */
    @ApiModelProperty(value = "子表集合")
    private List<PortalContentHisFilesSonVo> filesSons;
}