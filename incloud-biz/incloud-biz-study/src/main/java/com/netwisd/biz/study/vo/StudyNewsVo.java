package com.netwisd.biz.study.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 在线学习通知公告表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-03-15 16:37:49
 */
@Data
@ApiModel(value = "在线学习通知公告表 Vo")
public class StudyNewsVo extends IVo{

    /**
     * news_title
     * 新闻标题
     */
    
    @ApiModelProperty(value="新闻标题")
    private String newsTitle;
    /**
     * news_browse
     * 浏览量
     */
    
    @ApiModelProperty(value="浏览量")
    private Long newsBrowse;
    /**
     * news_type
     * 新闻类别
     */
    
    @ApiModelProperty(value="新闻类别")
    private String newsType;

    /**
     * news_type_name
     * 新闻类别
     */

    @ApiModelProperty(value="新闻类别名称")
    private String newsTypeName;
    /**
     * news_detail
     * 新闻内容
     */
    
    @ApiModelProperty(value="新闻内容")
    private String newsDetail;
    /**
     * news_photo_url
     * 图片路径
     */
    
    @ApiModelProperty(value="图片路径")
    private String newsPhotoUrl;
}
