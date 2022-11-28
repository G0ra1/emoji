package com.netwisd.base.portal.dto;

import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.base.wf.starter.dto.WfDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import java.util.List;

import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @Description 系统集成类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-07 10:18:54
 */
@Data
@ApiModel(value = "系统集成类内容发布 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PortalContentSysjointsDto extends IDto {

    /**
     * portal_id
     * 所属门户ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class) 
    @Valid(length = 20) 
    @ApiModelProperty(value="所属门户ID")
    private Long portalId;

    /**
     * portal_name
     * 门户名称
     */
    @Valid(length = 32) 
    @ApiModelProperty(value="门户名称")
    private String portalName;

    /**
     * part_id
     * 所属栏目ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class) 
    @Valid(length = 20) 
    @ApiModelProperty(value="所属栏目ID")
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
     * 所属栏目名称
     */
    @Valid(length = 255) 
    @ApiModelProperty(value="所属栏目名称")
    private String partName;

    /**
     * part_type_id
     * 所属栏目类型ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class) 
    @Valid(length = 20) 
    @ApiModelProperty(value="所属栏目类型ID")
    private Long partTypeId;

    /**
     * part_type_name
     * 栏目类型名称
     */
    @Valid(length = 32) 
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

//    /**
//     * pass_time
//     * 审批通过时间
//     */
//    @ApiModelProperty(value="审批通过时间")
//    private LocalDateTime passTime;

    /**
     * sysjointsSons
     * 子表集合
     */
    @ApiModelProperty(value="子表集合")
    private List<PortalContentSysjointsSonDto> sysjointsSons;

    /**
     * is_mobile
     * 是否移动端
     */
    @ApiModelProperty(value="是否移动端")
    private Integer isMobile;
}
