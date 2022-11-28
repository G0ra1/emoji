package com.netwisd.base.portal.dto;

import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.constants.Args;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @Description  视频类型内容发布-历史表子表  功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-09-06 14:08:44
 */
@Data
@ApiModel(value = " 视频类型内容发布-历史表子表  Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PortalContentHisVideosSonDto extends IDto {

    public PortalContentHisVideosSonDto(Args args){
        super(args);
    }

    /**
     * link_videos_id
     * 主表ID
     */
    @ApiModelProperty(value = "主表ID")
    private Long linkVideosId;

    /**
     * title
     * 新闻标题
     */
    
    
    @ApiModelProperty(value="新闻标题")
    private String title;

    /**
     * description
     * 摘要
     */
    
    
    @ApiModelProperty(value="摘要")
    private String description;

    /**
     * file_id
     * 文件ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    
    @ApiModelProperty(value="文件ID")
    private Long fileId;

    /**
     * content_url
     * 内容URL 新闻、通告类对应html路径;轮播图和图片新闻也对应html路径；
     */
    
    
    @ApiModelProperty(value="内容URL 新闻、通告类对应html路径;轮播图和图片新闻也对应html路径；")
    private String contentUrl;

    /**
     * hits
     * 点击量
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    
    @ApiModelProperty(value="点击量")
    private Long hits;

}
