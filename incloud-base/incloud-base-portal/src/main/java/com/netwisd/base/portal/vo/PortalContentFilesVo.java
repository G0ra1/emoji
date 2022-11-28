package com.netwisd.base.portal.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.netwisd.base.wf.starter.vo.WfVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @Description 文件下载类型内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-18 14:46:53
 */
@Data
@ApiModel(value = "文件下载类型内容发布 Vo")
public class PortalContentFilesVo extends IVo {

    /**
     * portal_id
     * 所属门户ID
     */
    @ApiModelProperty(value="所属门户ID")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long portalId;
    /**
     * portal_name
     * 门户名称
     */
    @ApiModelProperty(value="门户名称")
    private String portalName;
    /**
     * part_id
     * 所属栏目ID
     */
    @ApiModelProperty(value="所属栏目ID")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long partId;
    /**
     * part_code
     * 所属栏目CODE
     */
    @ApiModelProperty(value="所属栏目CODE")
    private String partCode;
    /**
     * part_name
     * 所属栏目NAME
     */
    @ApiModelProperty(value="所属栏目NAME")
    private String partName;
    /**
     * part_type_id
     * 所属栏目ID
     */
    @ApiModelProperty(value="所属栏目ID")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long partTypeId;
    /**
     * part_type_name
     * 栏目类型名称
     */
    @ApiModelProperty(value="栏目类型名称")
    private String partTypeName;
//    /**
//     * audit_status
//     * 审批状态
//     */
//    @ApiModelProperty(value="审批状态")
//    private Integer auditStatus;
    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * filesSonVos
     * 子表集合
     */
    @ApiModelProperty(value="子表集合")
    private List<PortalContentFilesSonVo> filesSons;
}
