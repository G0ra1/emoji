package com.netwisd.base.portal.vo;

import com.netwisd.base.portal.entity.PortalContentPictures;
import com.netwisd.base.wf.starter.vo.WfVo;
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
 * @Description 图片轮播类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-03 08:55:04
 */
@Data
@ApiModel(value = "图片轮播类内容发布 Vo")
public class PortalContentPicturesVo extends IVo {

    /**
     * portal_id
     * 所属门户ID
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="所属门户ID")
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
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="所属栏目ID")
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
     * 栏目类型ID
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="栏目类型ID")
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
    private List<PortalContentPicturesSonVo> picturesSons;
}
