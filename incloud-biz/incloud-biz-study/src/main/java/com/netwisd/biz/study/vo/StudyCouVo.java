package com.netwisd.biz.study.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 云数网讯 lhy@netwisd.com
 * @Description 课件表 功能描述...
 * @date 2022-04-19 18:23:02
 */
@Data
@ApiModel(value = "课件表 Vo")
public class StudyCouVo extends IVo {

    /**
     * label_code
     * 分类编码
     */
    @ApiModelProperty(value = "分类编码")
    private String labelCode;

    /**
     * label_name
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称")
    private String labelName;

    /**
     * cou_type
     * 课件分类 1 音频 2 视频 3 文档
     */
    @ApiModelProperty(value = "课件分类 1 音频 2 视频 3 文档")
    private Integer couType;

    /**
     * cou_name
     * 课件名称
     */
    @ApiModelProperty(value = "课件名称")
    private String couName;

    /**
     * use_range
     * 使用权限 1 公开 2 私有
     */
    @ApiModelProperty(value = "使用权限 1 公开 2 私有")
    private Integer useRange;

    /**
     * study_time
     * 音视频时长 （毫秒）
     */
    @ApiModelProperty(value = "音视频时长 （毫秒）")
    private Long studyTime;

    /**
     * studyTimeSize
     * 音视频时长 （时分秒）
     */
    @ApiModelProperty(value = "音视频时长 （时分秒）")
    private String studyTimeSize;

    /**
     * file_id
     * 文件id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value = "文件id")
    private Long fileId;

    /**
     * file_name
     * 文件名称
     */
    @ApiModelProperty(value = "文件名称")
    private String fileName;

    /**
     * file_url
     * 文件路径
     */
    @ApiModelProperty(value = "文件路径")
    private String fileUrl;

    /**
     * file_size
     * 文件大小
     */
    @ApiModelProperty(value = "文件大小")
    private Long fileSize;
    /**
     * file_size_view
     * 文件展示大小
     */
    @ApiModelProperty(value = "文件展示大小")
    private String fileSizeView;

    /**
     * isOpen
     * 是否公开 1 全部公开 0 部分公开
     */
    @ApiModelProperty(value = "是否公开 1 全部公开 0 部分公开 2 审核中")
    private Integer isOpen;

    /**
     * orgPermList
     * 课件机构部门授权
     */
    @ApiModelProperty(value = "课件机构部门授权")
    private List<StudyCouPermVo> orgPermList;

    /**
     * userPermList
     * 课件人员授权
     */
    @ApiModelProperty(value = "课件人员授权")
    private List<StudyCouPermVo> userPermList;

    ///////////////////////课程回显使用////////////////////////////
    /**
     * cumulativeStudyTime
     * 累计学习时间
     */
    @ApiModelProperty(value = "累计学习时间")
    private Long cumulativeStudyTime;

    /**
     * nowStudyTime
     * 课程详情和专题课程详情中的累计学习时间（秒）
     */
    @ApiModelProperty(value="课程详情和专题课程详情中的累计学习时间（秒）")
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long nowStudyTime;
    /**
     * lastVideoTime
     * 音视频最后播放时间节点
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value = "音视频最后播放时间节点")
    private Long lastVideoTime;

}
