package com.netwisd.biz.study.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 云数网讯 lhy@netwisd.com
 * @Description 证书表 功能描述...
 * @date 2022-04-19 19:31:09
 */
@Data
@ApiModel(value = "证书表 Vo")
public class StudyCertificateTemplateVo extends IVo {

    /**
     * type_code
     * 分类编码
     */
    @ApiModelProperty(value = "分类编码")
    private String typeCode;

    /**
     * type_name
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称")
    private String typeName;

    /**
     * certificate_name
     * 证书名称
     */
    @ApiModelProperty(value = "证书名称")
    private String certificateName;

    /**
     * issuer
     * 发证机构
     */
    @ApiModelProperty(value = "发证机构")
    private String issuer;


    /**
     * issuer
     * 证书描述
     */
    @ApiModelProperty(value = "证书描述")
    private String instructions;


    /**
     * validity
     * 证书有效期 0表示长期
     */
    @ApiModelProperty(value = "证书有效期 0表示长期")
    private Integer validity;

    /**
     * file_id
     * 文件id
     */
    @ApiModelProperty(value = "文件id")
    private Long fileId;

    /**
     * file_name
     * 文件名称
     */
    @ApiModelProperty(value = "文件名称")
    private String fileName;

    /**
     * file_path
     * 文件存放路径
     */
    @ApiModelProperty(value = "文件存放路径")
    private String filePath;

    /**
     * file_url
     * 文件访问路径
     */
    @ApiModelProperty(value = "文件访问路径")
    private String fileUrl;

    /**
     * isQuote
     * 是否被引用
     */
    /*@ApiModelProperty(value = "是否被引用")
    private Integer isQuote;*/
}
