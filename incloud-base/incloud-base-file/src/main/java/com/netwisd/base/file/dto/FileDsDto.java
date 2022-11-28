package com.netwisd.base.file.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.base.common.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @Description 文件数据源 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2021-12-29 11:04:48
 */
@Data
@ApiModel(value = "文件数据源 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FileDsDto extends IDto{

    public FileDsDto(Args args){
        super(args);
    }
    /**
     * pool_name
     * 数据源名称
     */
    @Valid(length = 50)
    @ApiModelProperty(value="数据源名称")
    private String poolName;

    /**
     * type
     * 文档存储类型
     */
    @Valid(length = 10)
    @ApiModelProperty(value="文档存储类型")
    private String type;

    /**
     * is_default
     * 是否默认实现
     */
    @ApiModelProperty(value="是否默认实现")
    private Integer isDefault = 0;

    /**
     * minio_url
     * minio地址
     */
    @ApiModelProperty(value="minio地址")
    private String minioUrl;

    /**
     * minio_access_key
     * minio账号
     */
    @ApiModelProperty(value="minio账号")
    private String minioAccessKey;

    /**
     * minio_secret_key
     * minio密码
     */
    @ApiModelProperty(value="minio密码")
    private String minioSecretKey;

    /**
     * minio_bucket_name
     * minio库名称
     */
    @ApiModelProperty(value="minio库名称")
    private String minioBucketName;

    /**
     * local_file_path
     * 本地存储路径
     */
    @ApiModelProperty(value="本地存储路径")
    private String localFilePath;

    /**
     * local_file_prefix
     * 本地存储前缀
     */
    @ApiModelProperty(value="本地存储前缀")
    private String localFilePrefix;

}
