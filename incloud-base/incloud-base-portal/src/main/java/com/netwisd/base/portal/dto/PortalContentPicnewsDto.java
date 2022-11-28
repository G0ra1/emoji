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

/**
 * @Description 图片新闻类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-20 10:09:51
 */
@Data
@ApiModel(value = "图片新闻类内容发布 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PortalContentPicnewsDto extends IDto {

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
     * 栏目类型ID
     */
    @ApiModelProperty(value="栏目类型ID")
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
     * title
     * 标题
     */
    @ApiModelProperty(value="标题")
    @Valid
    private String title;

    /**
     * description
     * 摘要
     */
    @ApiModelProperty(value="摘要")
    @Valid
    private String description;

    /**
     * img_url
     * 缩略图
     */
    @ApiModelProperty(value="缩略图")
    
    private String imgUrl;

    /**
     * file_id
     * 文件ID
     */
    @ApiModelProperty(value="文件ID")

    private Long fileId;

    /**
     * content_url
     * 内容URL 新闻、通告类对应html路径;
     */
    @ApiModelProperty(value="内容URL 新闻、通告类对应html路径;")

    private String contentUrl;

    /**
     * ueditor_content
     * 富文本编辑器内容
     */
    @ApiModelProperty(value="富文本编辑器内容")

    private String ueditorContent;

//    /**
//     * audit_status
//     * 审批状态
//     */
//    @ApiModelProperty(value="审批状态")
//
//    private Integer auditStatus;

    /**
     * hits
     * 点击量
     */
    @ApiModelProperty(value="点击量")
    
    private Long hits;

    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    
    private String remark;

}
