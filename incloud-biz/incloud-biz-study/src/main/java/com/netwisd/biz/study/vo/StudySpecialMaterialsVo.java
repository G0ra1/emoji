package com.netwisd.biz.study.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
/**
 * @Description 专题与学习资料表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-13 10:59:05
 */
@Data
@ApiModel(value = "专题与学习资料表 Vo")
public class StudySpecialMaterialsVo extends IVo{

    /**
     * special_id
     * 计划id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="计划id")
    private Long specialId;
    /**
     * materials_id
     * 资料id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="资料id")
    private Long materialsId;
    /**
     * materials_name
     * 资料名称
     */
    @ApiModelProperty(value="资料名称")
    private String materialsName;
    /**
     * materials_type_code
     * 资料分类编码
     */
    @ApiModelProperty(value="资料分类编码")
    private String materialsTypeCode;
    /**
     * materials_type_name
     * 资料分类名称
     */
    @ApiModelProperty(value="资料分类名称")
    private String materialsTypeName;
    /**
     * is_download
     * 是否允许下载
     */
    @ApiModelProperty(value="是否允许下载")
    private Integer isDownload;

    /**
     * file_id
     * 文件id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="文件id")
    private Long fileId;

    /**
     * file_name
     * 文件名称
     */
    @ApiModelProperty(value="文件名称")
    private String fileName;

    /**
     * file_url
     * 文件路径
     */
    @ApiModelProperty(value="文件路径")
    private String fileUrl;

}
