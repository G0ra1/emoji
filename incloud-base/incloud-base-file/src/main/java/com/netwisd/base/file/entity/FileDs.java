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
 * @Description $文件数据源 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2021-12-29 11:04:48
 */
@Data
@Table(value = "incloud_base_file_ds",comment = "文件数据源")
@TableName("incloud_base_file_ds")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "文件数据源 Entity")
public class FileDs extends IModel<FileDs> {

    /**
    * pool_name
    * 数据源名称
    */
    @ApiModelProperty(value="数据源名称")
    @TableField(value="pool_name")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = false, comment = "数据源名称")
    private String poolName;

    /**
    * type
    * 文档存储类型
    */
    @ApiModelProperty(value="文档存储类型")
    @TableField(value="type")
    @Column(type = DataType.VARCHAR, length = 10,  isNull = false, comment = "文档存储类型")
    private String type;

    /**
    * is_default
    * 是否默认实现
    */
    @ApiModelProperty(value="是否默认实现")
    @TableField(value="is_default")
    @Column(type = DataType.INT, isNull = false, comment = "是否默认实现")
    private Integer isDefault = 0;

    /**
    * minio_url
    * minio地址
    */
    @ApiModelProperty(value="minio地址")
    @TableField(value="minio_url")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "minio地址")
    private String minioUrl;

    /**
    * minio_access_key
    * minio账号
    */
    @ApiModelProperty(value="minio账号")
    @TableField(value="minio_access_key")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "minio账号")
    private String minioAccessKey;

    /**
    * minio_secret_key
    * minio密码
    */
    @ApiModelProperty(value="minio密码")
    @TableField(value="minio_secret_key")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "minio密码")
    private String minioSecretKey;

    /**
    * minio_bucket_name
    * minio库名称
    */
    @ApiModelProperty(value="minio库名称")
    @TableField(value="minio_bucket_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "minio库名称")
    private String minioBucketName;

    /**
    * local_file_path
    * 本地存储路径
    */
    @ApiModelProperty(value="本地存储路径")
    @TableField(value="local_file_path")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "本地存储路径")
    private String localFilePath;

    /**
    * local_file_prefix
    * 本地存储前缀
    */
    @ApiModelProperty(value="本地存储前缀")
    @TableField(value="local_file_prefix")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "本地存储前缀")
    private String localFilePrefix;

}
