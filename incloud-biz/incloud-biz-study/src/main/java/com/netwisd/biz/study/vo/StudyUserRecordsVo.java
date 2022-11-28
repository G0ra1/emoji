package com.netwisd.biz.study.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.util.IdTypeSerializer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudyUserRecordsVo {
    /**
     * id
     * 学习记录主键
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long id;

    /**
     * userId
     * 用户主键
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long userId;

    /**
     * userNameCh
     * 用户姓名
     */
    private String userNameCh;

    /**
     * specialId
     * 专题id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long specialId;

    /**
     * specialName
     * 专题名称
     */
    private String specialName;

    /**
     * lessonId
     * 课程id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long lessonId;

    /**
     * lessonName
     * 课程名称
     */
    private String lessonName;

    /**
     * couId
     * 课件主键
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long couId;

    /**
     * couName
     * 课件名称
     */
    private String couName;

    /**
     * couType
     * 课件类型
     */
    private Integer couType;

    /**
     * couSize
     * 课件数量
     */
    private Integer couSize;

    /**
     * description
     * 简介
     */
    private String description;

    /**
     * imgUrl
     * 图片路径
     */
    private String imgUrl;

    /**
     * lastVideoTime
     * 最后播放音视频时间节点
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long lastVideoTime;

    /**
     * cumulativeStudyTime
     * 课件累计播放时长（秒）
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long cumulativeStudyTime;

    /**
     * cumulativeStudyTime
     * 课件累计播放时长（时分秒）
     */
    private String cumulativeStudyTimeSize;

    /**
     * lastStudyTime
     * 最后学习时间
     */
    private LocalDateTime lastStudyTime;

    /**
     * studyBestLessTime
     * 最低学习时长（秒）
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long studyBestLessTime;

    /**
     * studyCouRate
     * 课件学习比率
     */
    private Integer studyCouRate;

}
