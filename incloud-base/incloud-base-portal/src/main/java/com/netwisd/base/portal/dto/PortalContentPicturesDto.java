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
 * @Description 图片轮播类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-03 08:55:04
 */
@Data
@ApiModel(value = "图片轮播类内容发布 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PortalContentPicturesDto extends IDto {

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
     * picturesSons
     * 子表集合
     */
    @ApiModelProperty(value="子表集合")
    private List<PortalContentPicturesSonDto> picturesSons;

    //查询使用
    /**
     * title
     * 图片标题
     */
    @ApiModelProperty(value="图片标题")
    private String title;

    /**
     * description
     * 摘要
     */
    private String description;

    /**
     * is_out_link
     * 是否外联文件0否1是
     */
    private Integer isOutLink;
}
