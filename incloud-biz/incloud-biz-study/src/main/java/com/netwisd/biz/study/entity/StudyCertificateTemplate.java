package com.netwisd.biz.study.entity;

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
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description $证书模板
 * @date 2022-04-19 19:31:09
 */
@Data
@Table(value = "incloud_biz_study_certificate_template", comment = "证书模板")
@TableName("incloud_biz_study_certificate_template")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "证书模板 Entity")
public class StudyCertificateTemplate extends IModel<StudyCertificateTemplate> {

    /**
     * type_code
     * 分类编码
     */
    @ApiModelProperty(value = "分类编码")
    @TableField(value = "type_code")
    @Column(type = DataType.VARCHAR, length = 100, isNull = true, comment = "分类编码")
    private String typeCode;

    /**
     * type_name
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称")
    @TableField(value = "type_name")
    @Column(type = DataType.VARCHAR, length = 100, isNull = true, comment = "分类名称")
    private String typeName;

    /**
     * certificate_name
     * 证书名称
     */
    @ApiModelProperty(value = "证书名称")
    @TableField(value = "certificate_name")
    @Column(type = DataType.VARCHAR, length = 100, isNull = true, comment = "证书名称")
    private String certificateName;


    /**
     * issuer
     * 发证机构
     */
    @ApiModelProperty(value = "发证机构")
    @TableField(value = "issuer")
    @Column(type = DataType.VARCHAR, length = 100, isNull = true, comment = "发证机构")
    private String issuer;


    /**
     * issuer
     * 证书描述
     */
    @ApiModelProperty(value = "证书描述")
    @TableField(value = "instructions")
    @Column(type = DataType.VARCHAR, length = 1000, isNull = true, comment = "证书描述")
    private String instructions;


    /**
     * validity
     * 证书有效期 0表示长期
     */
    @ApiModelProperty(value = "证书有效期 0表示长期")
    @TableField(value = "validity")
    @Column(type = DataType.INT, length = 3, isNull = true, comment = "证书有效期 0表示长期")
    private Integer validity;

    /**
     * file_id
     * 文件id
     */
    @ApiModelProperty(value = "文件id")
    @TableField(value = "file_id")
    @Column(type = DataType.BIGINT, length = 20, isNull = true, comment = "文件id")
    private Long fileId;

    /**
     * file_name
     * 文件名称
     */
    @ApiModelProperty(value = "文件名称")
    @TableField(value = "file_name")
    @Column(type = DataType.VARCHAR, length = 100, isNull = true, comment = "文件名称")
    private String fileName;

    /**
     * file_path
     * 文件存放路径
     */
    @ApiModelProperty(value = "文件存放路径")
    @TableField(value = "file_path")
    @Column(type = DataType.VARCHAR, length = 255, isNull = true, comment = "文件存放路径")
    private String filePath;

    /**
     * file_url
     * 文件访问路径
     */
    @ApiModelProperty(value = "文件访问路径")
    @TableField(value = "file_url")
    @Column(type = DataType.VARCHAR, length = 255, isNull = true, comment = "文件访问路径")
    private String fileUrl;
}
