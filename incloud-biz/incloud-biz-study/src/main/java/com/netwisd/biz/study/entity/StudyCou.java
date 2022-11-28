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

/**
 * @author 云数网讯 lhy@netwisd.com
 * @Description $课件表 功能描述...
 * @date 2022-04-19 18:23:02
 */
@Data
@Table(value = "incloud_biz_study_cou", comment = "课件表")
@TableName("incloud_biz_study_cou")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "课件表 Entity")
public class StudyCou extends IModel<StudyCou> {

    /**
     * label_code
     * 标签编码
     */
    @ApiModelProperty(value = "分类编码")
    @TableField(value = "label_code")
    @Column(type = DataType.VARCHAR, length = 100, isNull = true, comment = "分类编码")
    private String labelCode;

    /**
     * label_name
     * 标签名称
     */
    @ApiModelProperty(value = "分类名称")
    @TableField(value = "label_name")
    @Column(type = DataType.VARCHAR, length = 100, isNull = true, comment = "分类名称")
    private String labelName;

    /**
     * cou_type
     * 课件分类 1 音频 2 视频 3 文档
     */
    @ApiModelProperty(value = "课件分类 1 音频 2 视频 3 文档")
    @TableField(value = "cou_type")
    @Column(type = DataType.INT, length = 1, isNull = true, comment = "课件分类 1 音频 2 视频 3 文档")
    private Integer couType;

    /**
     * cou_name
     * 课件名称
     */
    @ApiModelProperty(value = "课件名称")
    @TableField(value = "cou_name")
    @Column(type = DataType.VARCHAR, length = 100, isNull = true, comment = "课件名称")
    private String couName;

    /**
     * use_range
     * 使用权限 1 公开 2 私有
     */
    @ApiModelProperty(value = "使用权限 1 公开 2 私有")
    @TableField(value = "use_range")
    @Column(type = DataType.INT, length = 1, isNull = true, comment = "使用权限 1 公开 2 私有")
    private Integer useRange;

    /**
     * study_time
     * 音视频时长 （毫秒）
     */
    @ApiModelProperty(value = "音视频时长（毫秒）")
    @TableField(value = "study_time")
    @Column(type = DataType.BIGINT, length = 20, isNull = true, comment = "音视频时长 （毫秒）")
    private Long studyTime;

    /**
     * file_id
     * 文件id
     */
    @ApiModelProperty(value = "文件id")
    @TableField(value = "file_id")
    @Column(type = DataType.BIGINT, length = 20, isNull = true, comment = "文件id")
    private Long fileId;

    /**
     * file_name
     * 文件名称
     */
    @ApiModelProperty(value = "文件名称")
    @TableField(value = "file_name")
    @Column(type = DataType.VARCHAR, length = 255, isNull = true, comment = "文件名称")
    private String fileName;

    /**
     * file_url
     * 文件路径
     */
    @ApiModelProperty(value = "文件路径")
    @TableField(value = "file_url")
    @Column(type = DataType.VARCHAR, length = 255, isNull = true, comment = "文件路径")
    private String fileUrl;

    /**
     * file_size
     * 文件大小
     */
    @ApiModelProperty(value = "文件大小")
    @TableField(value = "file_size")
    @Column(type = DataType.BIGINT, length = 20, isNull = true, comment = "文件大小")
    private Long fileSize;
    /**
     * file_size_view
     * 文件展示大小
     */
    @ApiModelProperty(value = "文件展示大小")
    @TableField(value = "file_size_view")
    @Column(type = DataType.VARCHAR, length = 20, isNull = true, comment = "文件展示大小")
    private String fileSizeView;

}
