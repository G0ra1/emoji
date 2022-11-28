package com.netwisd.biz.study.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.util.IdTypeDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 云数网讯 lhy@netwisd.com
 * @Description 课件表 功能描述...
 * @date 2022-04-19 18:23:02
 */
@Data
@ApiModel(value = "课件表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudyCouDto extends IDto {

    /**
     * label_code
     * 分类编码
     */
    @ApiModelProperty(value = "标签编码")
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
     * file_id
     * 文件id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
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
     * orgPermList
     * 课件机构部门授权
     */
    @ApiModelProperty(value = "课件机构部门授权")
    private List<StudyCouPermDto> orgPermList;

    /**
     * userPermList
     * 课件人员授权
     */
    @ApiModelProperty(value = "课件人员授权")
    private List<StudyCouPermDto> userPermList;
}
