package com.netwisd.biz.study.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "课件详情表 Vo")
public class StudyCouForDetailVo {

    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="主键"  )
    public Long id;

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
     * file_url
     * 文件路径
     */
    @ApiModelProperty(value = "文件路径")
    private String fileUrl;

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

    /**
     * study_time
     * 音视频时长 （毫秒）
     */
    @ApiModelProperty(value = "累计学习时长（时分秒）")
    private String studyTime;

    /**
     * study_time
     * 音视频时长 （毫秒）
     */
    @ApiModelProperty(value = "累计学习时长（时分秒）")
    private String videoTime;

    /**
     * lastVideoTime
     * 音视频最后播放时间节点
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value = "音视频最后播放时间节点")
    private Long lastVideoTime;
}
