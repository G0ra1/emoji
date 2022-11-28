package com.netwisd.base.portal.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 调整 文件下载类型内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-02 15:23:45
 */
@Data
@ApiModel(value = "调整 文件下载类型内容发布子表 Vo")
public class PortalContentAdjFilesSonVo extends IVo{

    /**
     * link_files_id
     * 主表ID
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="主表ID")
    private Long linkFilesId;
    /**
     * link_files_son_id
     * 子表对应ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="子表对应ID")
    private Long linkFilesSonId;
    /**
     * is_out_link
     * 是否外联文件0否1是
     */
    
    @ApiModelProperty(value="是否外联文件0否1是")
    private Integer isOutLink;
    /**
     * file_name
     * 文件名称
     */
    
    @ApiModelProperty(value="文件名称")
    private String fileName;
    /**
     * file_url
     * 文件地址
     */
    
    @ApiModelProperty(value="文件地址")
    private String fileUrl;
    /**
     * file_id
     * 文件id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="文件id")
    private Long fileId;
    /**
     * hits
     * 点击量
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="点击量")
    private Long hits;
}
