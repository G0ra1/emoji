package com.netwisd.biz.study.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StudySpecialLessonCouDetailVo {
    /**
     * id
     * 主键
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value = "主键")
    private Long id;

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
     * finishSize
     * 完成度
     */
    @ApiModelProperty(value = "完成度")
    private String finishSize;

    /**
     * studyBestLessTime
     * 最低学习时间
     */
    @ApiModelProperty(value = "最低学习时间")
    private String studyBestLessTime;

    /**
     * couIsCompulsory
     * 是否必修
     */
    @ApiModelProperty(value = "是否必修 0否1是")
    private Integer couIsCompulsory;

    /**
     * lastVideoTime
     * 最后播放音视频时间节点
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="最后播放音视频时间节点")
    private Long lastVideoTime;

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
}
