package com.netwisd.base.portal.dto;

import com.netwisd.base.wf.starter.dto.WfDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @Description 调整 图片新闻类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-31 04:25:00
 */
@Data
@ApiModel(value = "调整 图片新闻类内容发布 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PortalContentAdjPicnewsDto extends WfDto{

    /**
     * link_picnews_id
     * 所属主表ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)

    @ApiModelProperty(value="所属主表ID")
    private Long linkPicnewsId;

    /**
     * portal_id
     * 所属门户ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class) 
    
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
    @JsonDeserialize(using = IdTypeDeserializer.class) 
    
    @ApiModelProperty(value="所属栏目ID")
    private Long partId;

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
    @JsonDeserialize(using = IdTypeDeserializer.class) 
    
    @ApiModelProperty(value="栏目类型ID")
    private Long partTypeId;

    /**
     * part_type_name
     * 栏目类型名称
     */
    
    
    @ApiModelProperty(value="栏目类型名称")
    private String partTypeName;

    /**
     * title
     * 标题
     */
    
    
    @ApiModelProperty(value="标题")
    private String title;

    /**
     * description
     * 摘要
     */
    
    
    @ApiModelProperty(value="摘要")
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
    @JsonDeserialize(using = IdTypeDeserializer.class)
    private Long fileId;

    /**
     * content_url
     * 内容URL 新闻、通告类对应html路径;
     */
    
    
    @ApiModelProperty(value="内容URL 新闻、通告类对应html路径;")
    private String contentUrl;

    /**
     * audit_status
     * 审批状态
     */
    
    
    @ApiModelProperty(value="审批状态")
    private Integer auditStatus;

    /**
     * hits
     * 点击量
     */
    @JsonDeserialize(using = IdTypeDeserializer.class) 
    
    @ApiModelProperty(value="点击量")
    private Long hits;

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
     * ueditor_content
     * 富文本编辑器内容
     */
    
    
    @ApiModelProperty(value="富文本编辑器内容")
    private String ueditorContent;

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

}
