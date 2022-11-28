package com.netwisd.base.portal.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.data.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 图片轮播类内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-03 08:55:53
 */
@Data
@ApiModel(value = "图片轮播类内容发布子表 Vo")
public class PortalContentPicturesSonVo extends IVo{

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
    /**
     * link_pictures_id
     * 关联主表id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
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
    @JsonSerialize(using = IdTypeSerializer.class) 
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
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="点击量")
    private Long hits;
    /**
     * out_link_url
     * 外链URL
     */
    @ApiModelProperty(value="外链URL")
    private String outLinkUrl;
}
