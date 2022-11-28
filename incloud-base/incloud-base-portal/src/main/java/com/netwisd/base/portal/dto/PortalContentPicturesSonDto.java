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
 * @Description 图片轮播类内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-03 08:55:53
 */
@Data
@ApiModel(value = "图片轮播类内容发布子表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PortalContentPicturesSonDto extends IDto {

    public PortalContentPicturesSonDto(Args args){
        super(args);
    }

    /**
     * link_pictures_id
     * 关联主表id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="关联主表id")
    private Long linkPicturesId;

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
    @ApiModelProperty(value="摘要")
    private String description;

    /**
     * is_out_link
     * 是否外联文件0否1是
     */
    @ApiModelProperty(value="是否外联文件0否1是")
    private Integer isOutLink;

    /**
     * file_id
     * 文件id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="文件id")
    private Long fileId;

    /**
     * file_url
     * 文件URL
     */
    @ApiModelProperty(value="文件URL")
    private String fileUrl;

    /**
     * content_url
     * 内容URL
     */
    @ApiModelProperty(value="内容URL")
    private String contentUrl;

    /**
     * ueditor_content
     * 富文本编辑器内容
     */
    @ApiModelProperty(value="富文本编辑器内容")
    private String ueditorContent;

    /**
     * hits
     * 点击量
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="点击量")
    private Long hits;

    /**
     * out_link_url
     * 外链URL
     */
    @ApiModelProperty(value="外链URL")
    private String outLinkUrl;
}
