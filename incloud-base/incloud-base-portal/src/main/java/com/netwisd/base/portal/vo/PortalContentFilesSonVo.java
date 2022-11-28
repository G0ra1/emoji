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
 * @Description 文件下载类型内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-02 10:20:31
 */
@Data
@ApiModel(value = "文件下载类型内容发布子表 Vo")
public class PortalContentFilesSonVo extends IVo{

    /**
     * portal_id
     * 所属门户ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="所属门户ID")
    private Long portalId;
    /**
     * portal_name
     * 门户名称
     */
    @ApiModelProperty(value="门户名称")
    private String portalName;
    /**
     * part_id
     * 所属栏目ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="所属栏目ID")
    private Long partId;
    /**
     * part_code
     * 所属栏目CODE
     */
    @ApiModelProperty(value="所属栏目CODE")
    private String partCode;
    /**
     * part_name
     * 所属栏目NAME
     */
    @ApiModelProperty(value="所属栏目NAME")
    private String partName;
    /**
     * part_type_id
     * 栏目类型ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="栏目类型ID")
    private Long partTypeId;
    /**
     * part_type_name
     * 栏目类型名称
     */
    @ApiModelProperty(value="栏目类型名称")
    private String partTypeName;
    /**
     * link_files_id
     * 主表ID
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="主表ID")
    private Long linkFilesId;
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
