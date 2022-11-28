package com.netwisd.base.file.dto;

import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.anntation.Valid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * @Description 文件存储 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-02-09 12:27:14
 */
@Data
@ApiModel(value = "文件存储 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FileInfoDto extends IDto{

    @ApiModelProperty(value="索引名称，可不传")
    private String index;

    @Valid
    @ApiModelProperty(value="搜索关键字")
    private String keyWords;

    /**
     * file_name
     * 原始文件名
     */
    @ApiModelProperty(value="原始文件名"  )
    private String fileName;

    /**
     * file_is_img
     * 是否是图片,0:是，1:不是
     */
    @ApiModelProperty(value="是否是图片,0:是，1:不是"  )
    private Integer fileIsImg;

    /**
     * file_content_type
     * 文件的真实类型
     */
    @ApiModelProperty(value="文件的真实类型"  )
    private String fileContentType;

    /**
     * file_source
     * 文件来源，业务来源
     */
    @ApiModelProperty(value="文件来源，业务来源"  )
    private String fileSource;

    /**
     * file_pool_name
     * 文件存储的库
     */
    @ApiModelProperty(value="文件存储的库"  )
    private String filePoolName;

    /**
     * file_store_type
     * 文件存储方式；1.本地；2aliyun；3.minio 其他方式为用户自定义添加的数据源名称
     */
    @ApiModelProperty(value="文件存储方式；1.本地；2aliyun；3.minio；其他方式为用户自定义添加的数据源名称"  )
    private String fileStoreType;

    /**
     * file_md5_code
     * 判断文件是否重复上传
     */
    @ApiModelProperty(value="判断文件是否重复上传"  )
    private String fileMd5Code;

    /**
     * file_bucket_name
     * minio和aliyun时会用到
     */
    @ApiModelProperty(value="minio和aliyun时会用到"  )
    private String fileBucketName;

    /**
     * file_size
     * 文件大小
     */
    @ApiModelProperty(value="文件大小"  )
    private Long fileSize;

    /**
     * file_size_view
     * 文件展示大小
     */
    @ApiModelProperty(value="文件展示大小"  )
    private String fileSizeView;

    /**
     * file_path
     * 本地存储时使用，真实路径
     */
    @ApiModelProperty(value="本地存储时使用，真实路径"  )
    private String filePath;

    /**
     * file_url
     * 本地存储时使用，访问路径
     */
    @ApiModelProperty(value="本地存储时使用，访问路径"  )
    private String fileUrl;

    /**
     * 创建日期
     */
    @ApiModelProperty( value="start_create_time" )
    public LocalDateTime startCreateTime;

    /**
     * 创建日期
     */
    @ApiModelProperty( value="end_create_time" )
    public LocalDateTime endCreateTime;
}
