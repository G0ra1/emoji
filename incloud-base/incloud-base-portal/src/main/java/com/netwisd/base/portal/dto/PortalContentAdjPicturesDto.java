package com.netwisd.base.portal.dto;

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
 * @Description 调整 图片轮播类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-03 14:15:30
 */
@Data
@ApiModel(value = "调整 图片轮播类内容发布 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PortalContentAdjPicturesDto extends WfDto{

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
     * part_name
     * 所属栏目NAME
     */
    @Valid(length = 32) 
    @ApiModelProperty(value="所属栏目NAME")
    private String partName;

    /**
     * part_type_id
     * 栏目类型ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class) 
    @Valid(length = 20) 
    @ApiModelProperty(value="栏目类型ID")
    private Long partTypeId;

    /**
     * part_type_name
     * 栏目类型名称
     */
    
    @Valid(length = 32) 
    @ApiModelProperty(value="栏目类型名称")
    private String partTypeName;

    /**
     * audit_status
     * 审批状态
     */
    @ApiModelProperty(value="审批状态")
    private Integer auditStatus;

    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * pass_time
     * 审批通过时间
     */
    @ApiModelProperty(value="审批通过时间")
    private LocalDateTime passTime;

    /**
     * apply_user_id
     * 申请人id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="申请人id")
    private Long applyUserId;

    /**
     * apply_user_name
     * 申请人name
     */
    @ApiModelProperty(value="申请人name")
    private String applyUserName;

    /**
     * picturesSons
     * 子表集合
     */
    @ApiModelProperty(value="子表集合")
    private List<PortalContentAdjPicturesSonDto> picturesSons;
}
