package com.netwisd.base.file.entity;

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
 * 对于简单应用参数传递和展示使用实体即可，对于不想在数据库中对应的字段，使用@TableField标识进行注解，也可结合dozer使用进行双向映射
 *
 * @author 云数网讯 zouliming@netwisd.com
 * @Description $文件存储 功能描述...
 * @date 2020-02-09 12:27:14
 */
@Data
@Table(value = "incloud_base_file_info", comment = "文件存储")
@TableName("incloud_base_file_info")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "文件存储 Entity")
public class FileInfo extends IModel<FileInfo> {

    /**
     * file_name
     * 原始文件名
     */
    @ApiModelProperty(value = "原始文件名")
    @TableField(value = "file_name")
    @Column(type = DataType.VARCHAR, length = 100, isNull = false, comment = "原始文件名")
    private String fileName;
    /**
     * file_is_img
     * 是否是图片,0:是，1:不是
     */
    @ApiModelProperty(value = "是否是图片,0:是，1:不是")
    @TableField(value = "file_is_img")
    @Column(type = DataType.INT, length = 1, isNull = true, comment = "是否是图片,0:是，1:不是")
    private Integer fileIsImg;
    /**
     * file_content_type
     * 文件的真实类型
     */
    @ApiModelProperty(value = "文件的真实类型")
    @TableField(value = "file_content_type")
    @Column(type = DataType.VARCHAR, length = 255, isNull = true, comment = "文件的真实类型")
    private String fileContentType;
    /**
     * file_source
     * 文件来源，业务来源
     */
    @ApiModelProperty(value = "文件来源，业务来源")
    @TableField(value = "file_source")
    @Column(type = DataType.VARCHAR, length = 50, isNull = false, comment = "文件来源，业务来源")
    private String fileSource;
    /**
     * file_pool_name
     * 文件存储的库
     */
    @ApiModelProperty(value = "文件存储的库")
    @TableField(value = "file_pool_name")
    @Column(type = DataType.VARCHAR, length = 50, isNull = false, comment = "文件存储的库")
    private String filePoolName;
    /**
     * file_store_type
     * 文件存储方式；本地/minio
     */
    @ApiModelProperty(value = "文件存储方式；本地/minio")
    @TableField(value = "file_store_type")
    @Column(type = DataType.VARCHAR, length = 10, isNull = false, comment = "文件存储方式；本地/minio")
    private String fileStoreType;
    /**
     * file_md5_code
     * 判断文件是否重复上传
     */
    @ApiModelProperty(value = "md5文件唯一标识")
    @TableField(value = "file_md5_code")
    @Column(type = DataType.VARCHAR, length = 32, isNull = false, comment = "md5文件唯一标识")
    private String fileMd5Code;
    /**
     * file_bucket_name
     * minio和aliyun时会用到
     */
    @ApiModelProperty(value = "minio和aliyun时会用到")
    @TableField(value = "file_bucket_name")
    @Column(type = DataType.VARCHAR, length = 50, isNull = true, comment = "minio和aliyun时会用到")
    private String fileBucketName;
    /**
     * file_size
     * 文件大小
     */
    @ApiModelProperty(value = "文件大小")
    @TableField(value = "file_size")
    @Column(type = DataType.BIGINT, length = 20, isNull = false, comment = "文件大小")
    private Long fileSize;
    /**
     * file_size_view
     * 文件展示大小
     */
    @ApiModelProperty(value = "文件展示大小")
    @TableField(value = "file_size_view")
    @Column(type = DataType.VARCHAR, length = 20, isNull = false, comment = "文件展示大小")
    private String fileSizeView;

    /**
     * file_duration
     * 媒体文件时长毫秒
     */
    @ApiModelProperty(value = "媒体文件时长毫秒")
    @TableField(value = "file_duration")
    @Column(type = DataType.BIGINT, length = 20, isNull = true, comment = "媒体文件时长毫秒")
    private Long fileDuration;
    /**
     * file_path
     * 本地存储时使用，真实路径
     */
    @ApiModelProperty(value = "本地存储时使用，真实路径")
    @TableField(value = "file_path")
    @Column(type = DataType.VARCHAR, length = 255, isNull = true, comment = "本地存储时使用，真实路径")
    private String filePath;
    /**
     * file_url
     * 本地存储时使用，访问路径
     */
    @ApiModelProperty(value = "本地存储时使用，访问路径")
    @TableField(value = "file_url")
    @Column(type = DataType.VARCHAR, length = 255, isNull = true, comment = "本地存储时使用，访问路径")
    private String fileUrl;
}
