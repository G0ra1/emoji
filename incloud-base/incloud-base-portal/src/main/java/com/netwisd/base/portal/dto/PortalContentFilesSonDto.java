package com.netwisd.base.portal.dto;

import com.netwisd.base.common.data.IDto;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @Description 文件下载类型内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-02 10:20:31
 */
@Data
@ApiModel(value = "文件下载类型内容发布子表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PortalContentFilesSonDto extends IDto {

    public PortalContentFilesSonDto(Args args){
        super(args);
    }

    /**
     * link_files_id
     * 主表ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Valid(length = 20)
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
    @JsonDeserialize(using = IdTypeDeserializer.class)
    
    @ApiModelProperty(value="文件id")
    private Long fileId;

    /**
     * hits
     * 点击量
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    
    @ApiModelProperty(value="点击量")
    private Long hits;

}
