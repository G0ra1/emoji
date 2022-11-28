package com.netwisd.base.portal.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.data.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description  视频类型内容发布-调整表子表   功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-09-06 16:15:52
 */
@Data
@ApiModel(value = " 视频类型内容发布-调整表子表   Vo")
public class PortalContentAdjVideosSonVo extends IVo{

    /**
     * link_videos_id
     * 主表关联id
     */
    @ApiModelProperty(value="主表关联id")
    private Long linkVideosId;

    /**
     * link_videos_son_id
     * 子表对应ID
     */
    @ApiModelProperty(value="子表对应ID")
    private Long linkVideosSonId;

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
    @JsonSerialize(using = IdTypeSerializer.class)
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
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="点击量")
    private Long hits;
}
