package com.netwisd.biz.study.entity;

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
    import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description $在线学习通知公告表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-03-15 16:37:49
 */
@Data
@Table(value = "incloud_biz_study_news",comment = "在线学习通知公告表")
@TableName("incloud_biz_study_news")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "在线学习通知公告表 Entity")
public class StudyNews extends IModel<StudyNews> {

    /**
    * news_title
    * 新闻标题
    */
    @ApiModelProperty(value="新闻标题")
    @TableField(value="news_title")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "新闻标题")
    private String newsTitle;

    /**
    * news_browse
    * 浏览量
    */
    @ApiModelProperty(value="浏览量")
    @TableField(value="news_browse")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "浏览量")
    private Long newsBrowse;

    /**
    * news_type
    * 新闻类别
     * 0.要闻 1.集团动态2.党校动态3.党史百年4.疫情防控5.师资队伍6.党校介绍7.专题简介
    */
    @ApiModelProperty(value="新闻类别")
    @TableField(value="news_type")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "新闻类别")
    private String newsType;

    /**
     * news_type_name
     * 新闻类别名称
    */
    @ApiModelProperty(value="新闻类别名称")
    @TableField(value="news_type_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "新闻类别名称")
    private String newsTypeName;



    /**
    * news_detail
    * 新闻内容
    */
    @ApiModelProperty(value="新闻内容")
    @TableField(value="news_detail")
    @Column(type = DataType.TEXT, length = 0,  isNull = true, comment = "新闻内容")
    private String newsDetail;

    /**
    * news_photo_url
    * 图片路径
    */
    @ApiModelProperty(value="图片路径")
    @TableField(value="news_photo_url")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "图片路径")
    private String newsPhotoUrl;

}
