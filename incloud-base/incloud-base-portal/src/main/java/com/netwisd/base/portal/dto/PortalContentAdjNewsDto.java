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
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @Description 调整 新闻通告类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-27 15:26:39
 */
@Data
@ApiModel(value = "调整 新闻通告类内容发布 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PortalContentAdjNewsDto extends WfDto{

    /**
     * link_news_id
     * 主表id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class) 
    @Valid(length = 20) 
    @ApiModelProperty(value="主表id")
    private Long linkNewsId;

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
     * title
     * 标题
     */
    
    @Valid(length = 64) 
    @ApiModelProperty(value="标题")
    private String title;

    /**
     * description
     * 摘要
     */
    
    @Valid(length = 255) 
    @ApiModelProperty(value="摘要")
    private String description;

    /**
     * content_url
     * 内容URL 新闻、通告类对应html路径;
     */
    
    @Valid(length = 255) 
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
     * ueditor_content
     * 富文本编辑器内容
     */
    
    
    @ApiModelProperty(value="富文本编辑器内容")
    private String ueditorContent;

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
     * 申请人名称
     */
    
    
    @ApiModelProperty(value="申请人名称")
    private String applyUserName;

}
