package com.netwisd.base.portal.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.anntation.Table;
import com.netwisd.common.db.data.DataType;
import com.netwisd.common.db.data.IModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description $图片轮播类内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-03 08:55:53
 */
@Data
@Table(value = "incloud_base_portal_content_pictures_son",comment = "图片轮播类内容发布子表")
@TableName("incloud_base_portal_content_pictures_son")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "图片轮播类内容发布子表 Entity")
public class PortalContentPicturesSon extends IModel<PortalContentPicturesSon> {

    /**
     * link_pictures_id
     * 关联主表id
     */
    @ApiModelProperty(value="关联主表id")
    @TableField(value="link_pictures_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "关联主表id")
    private Long linkPicturesId;
    /**
     * title
     * 图片标题
     */
    @ApiModelProperty(value="图片标题")
    @TableField(value="title")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "图片标题")
    private String title;
    /**
     * description
     * 摘要
     */
    @ApiModelProperty(value="摘要")
    @TableField(value="description")
    @Column(type = DataType.VARCHAR, length = 266,  isNull = true, comment = "摘要")
    private String description;
    /**
     * is_out_link
     * 是否外联文件0否1是
     */
    @ApiModelProperty(value="是否外联文件0否1是")
    @TableField(value="is_out_link")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "是否外联文件0否1是")
    private Integer isOutLink;
    /**
     * file_id
     * 文件id
     */
    @ApiModelProperty(value="文件id")
    @TableField(value="file_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "文件id")
    private Long fileId;
    /**
     * content_url
     * 内容URL
     */
    @ApiModelProperty(value="文件URL")
    @TableField(value="file_url")
    @Column(type = DataType.VARCHAR, length = 500,  isNull = true, comment = "文件URL")
    private String fileUrl;
    /**
     * content_url
     * 内容URL
     */
    @ApiModelProperty(value="内容URL")
    @TableField(value="content_url")
    @Column(type = DataType.VARCHAR, length = 500,  isNull = true, comment = "内容URL")
    private String contentUrl;
    /**
     * ueditor_content
     * 富文本编辑器内容
     */
    @ApiModelProperty(value="富文本编辑器内容")
    @TableField(value="ueditor_content")
    @Column(type = DataType.TEXT, length = 5000,  isNull = true, comment = "内容URL")
    private String ueditorContent;
    /**
     * hits
     * 点击量
     */
    @ApiModelProperty(value="点击量")
    @TableField(value="hits")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "点击量")
    private Long hits;
    /**
     * out_link_url
     * 外链URL
     */
    @ApiModelProperty(value="外链URL")
    @TableField(value="out_link_url")
    @Column(type = DataType.TEXT, length = 0,  isNull = true, comment = "外链URL")
    private String outLinkUrl;
}
