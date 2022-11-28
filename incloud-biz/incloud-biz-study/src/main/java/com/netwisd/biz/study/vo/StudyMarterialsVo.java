package com.netwisd.biz.study.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 学习资料表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-19 20:04:23
 */
@Data
@ApiModel(value = "学习资料表 Vo")
public class StudyMarterialsVo extends IVo{

    /**
     * label_code
     * 标签编码
     */
    @ApiModelProperty(value="标签编码")
    private String labelCode;

    /**
     * label_name
     * 标签名称
     */
    @ApiModelProperty(value="标签名称")
    private String labelName;

    /**
     * marterials_name
     * 学习资料名称
     */
    @ApiModelProperty(value="学习资料名称")
    private String marterialsName;

    /**
     * is_download
     * 是否允许下载 0否 1是
     */
    @ApiModelProperty(value="是否允许下载 0否 1是")
    private Integer isDownload;

    /**
     * file_id
     * 文件id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
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

    /**
     * description
     * 说明
     */
    @ApiModelProperty(value="说明")
    private String description;

}
