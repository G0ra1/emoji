package com.netwisd.base.portal.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.base.wf.starter.dto.WfDto;
import com.netwisd.common.core.util.IdTypeDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import java.util.List;

/**
 * @Description 文件下载类型内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-18 14:46:53
 */
@Data
@ApiModel(value = "文件下载类型内容发布 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PortalContentFilesDto extends IDto {

    /**
     * portal_id
     * 所属门户ID
     */
    @ApiModelProperty(value="所属门户ID")
    @Valid
    private Long portalId;

    /**
     * portal_name
     * 门户名称
     */
    @ApiModelProperty(value="门户名称")
    @Valid
    private String portalName;

    /**
     * part_id
     * 所属栏目ID
     */
    @ApiModelProperty(value="所属栏目ID")
    @Valid
    private Long partId;

    /**
     * part_code
     * 所属栏目CODE
     */
    @ApiModelProperty(value="所属栏目CODE")
    @Valid(length = 32)
    private String partCode;

    /**
     * part_name
     * 所属栏目NAME
     */
    @ApiModelProperty(value="所属栏目NAME")
    @Valid
    private String partName;

    /**
     * part_type_id
     * 所属栏目ID
     */
    @ApiModelProperty(value="所属栏目ID")
    @Valid
    private Long partTypeId;

    /**
     * part_type_name
     * 栏目类型名称
     */
    @ApiModelProperty(value="栏目类型名称")
    @Valid
    private String partTypeName;

    /**
//     * audit_status
//     * 审批状态
//     */
//    @ApiModelProperty(value="审批状态")
//
//    private Integer auditStatus;

    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    
    private String remark;

    /**
     * file_name
     * 文件名称
     */
    @ApiModelProperty(value="文件名称")
    private String fileName;

    /**
     * filesSonDtos
     * 子表集合
     */
    @ApiModelProperty(value="子表集合")
    private List<PortalContentFilesSonDto> filesSons;

}
