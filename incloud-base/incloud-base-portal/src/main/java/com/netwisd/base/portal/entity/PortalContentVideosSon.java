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
 * @Description $ 视频类型内容发布子表 功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-09-05 21:48:10
 */
@Data
@Table(value = "incloud_base_portal_content_videos_son",comment = " 视频类型内容发布子表")
@TableName("incloud_base_portal_content_videos_son")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = " 视频类型内容发布子表 Entity")
public class PortalContentVideosSon extends IModel<PortalContentVideosSon> {

    /**
     * link_videos_id
     * 主表ID
     */
    @ApiModelProperty(value = "主表ID")
    @TableField(value = "link_videos_id")
    @Column(type = DataType.BIGINT, length = 20, isNull = true, comment = "主表ID")
    private Long linkVideosId;
    /**
     * title
     * 新闻标题
     */
    @ApiModelProperty(value = "新闻标题")
    @TableField(value = "title")
    @Column(type = DataType.VARCHAR, length = 64, isNull = true, comment = "新闻标题")
    private String title;
    /**
     * description
     * 摘要
     */
    @ApiModelProperty(value = "摘要")
    @TableField(value = "description")
    @Column(type = DataType.VARCHAR, length = 255, isNull = true, comment = "摘要")
    private String description;
    /**
     * file_id
     * 文件ID
     */
    @ApiModelProperty(value = "文件ID")
    @TableField(value = "file_id")
    @Column(type = DataType.BIGINT, length = 20, isNull = true, comment = "文件ID")
    private Long fileId;
    /**
     * img_url
     * 缩略图
     */
    @ApiModelProperty(value="缩略图")
    @TableField(value="img_url")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "缩略图")
    private String imgUrl;
    /**
     * hits
     * 点击量
     */
    @ApiModelProperty(value = "点击量")
    @TableField(value = "hits")
    @Column(type = DataType.BIGINT, length = 20, isNull = true, comment = "点击量")
    private Long hits;
    /**
     * content_url
     * 内容URL 新闻、通告类对应html路径;轮播图和图片新闻也对应html路径；
     */
    @ApiModelProperty(value = "内容URL 新闻、通告类对应html路径;轮播图和图片新闻也对应html路径；")
    @TableField(value = "content_url")
    @Column(type = DataType.VARCHAR, length = 255, isNull = true, comment = "内容URL 新闻、通告类对应html路径;轮播图和图片新闻也对应html路径；")
    private String contentUrl;

}
