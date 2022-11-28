package com.netwisd.base.portal.dto;

import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.anntation.Valid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author 云数网讯 XHL@netwisd.com
 * @Description 文件存储  功能描述...
 * @date 2021-01-14 09:51:13
 */
@Data
@ApiModel(value = "文件存储  Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FileInfoDto extends IDto {


    /**
     * file_name
     * 原始文件名
     */
    @ApiModelProperty(value = "原始文件名")
    @Valid(length = 100)
    private String fileName;

    /**
     * file_is_img
     * 是否是图片,0:是，1:不是
     */
    @ApiModelProperty(value = "是否是图片,0:是，1:不是")

    private Integer fileIsImg;

    /**
     * file_content_type
     * 文件的真实类型
     */
    @ApiModelProperty(value = "文件的真实类型")

    private String fileContentType;

    /**
     * file_source
     * 文件来源，业务来源
     */
    @ApiModelProperty(value = "文件来源，业务来源")
    @Valid(length = 50)
    private String fileSource;

    /**
     * file_store_type
     * 文件存储方式；1.本地；2aliyun；3.minio
     */
    @ApiModelProperty(value = "文件存储方式；1.本地；2aliyun；3.minio")
    @Valid(length = 10)
    private String fileStoreType;

    /**
     * file_md5_code
     * md5文件唯一标识
     */
    @ApiModelProperty(value = "md5文件唯一标识")
    @Valid(length = 32)
    private String fileMd5Code;

    /**
     * file_bucket_name
     * minio和aliyun时会用到
     */
    @ApiModelProperty(value = "minio和aliyun时会用到")

    private String fileBucketName;

    /**
     * file_size
     * 文件大小
     */
    @ApiModelProperty(value = "文件大小")
    @Valid(length = 20)
    private Long fileSize;

    /**
     * file_path
     * 本地存储时使用，真实路径
     */
    @ApiModelProperty(value = "本地存储时使用，真实路径")

    private String filePath;

    /**
     * file_url
     * 本地存储时使用，访问路径
     */
    @ApiModelProperty(value = "本地存储时使用，访问路径")

    private String fileUrl;

    /**
     * biz_id
     * 业务id
     */
    @ApiModelProperty(value = "业务id")
    @Valid(length = 20)
    private Long bizId;

    /**
     * base64Content
     * 文件内容
     */
    @ApiModelProperty(value = "文件内容")
    private String base64Content;

}
