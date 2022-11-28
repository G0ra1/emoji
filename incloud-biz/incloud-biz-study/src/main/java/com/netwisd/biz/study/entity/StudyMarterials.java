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
 * @Description $学习资料表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-19 20:04:23
 */
@Data
@Table(value = "incloud_biz_study_marterials",comment = "学习资料表")
@TableName("incloud_biz_study_marterials")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "学习资料表 Entity")
public class StudyMarterials extends IModel<StudyMarterials> {

    /**
    * label_code
    * 标签编码
    */
    @ApiModelProperty(value="标签编码")
    @TableField(value="label_code")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "标签编码")
    private String labelCode;

    /**
    * label_name
    * 标签名称
    */
    @ApiModelProperty(value="标签名称")
    @TableField(value="label_name")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "标签名称")
    private String labelName;

    /**
    * marterials_name
    * 学习资料名称
    */
    @ApiModelProperty(value="学习资料名称")
    @TableField(value="marterials_name")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "学习资料名称")
    private String marterialsName;

    /**
    * is_download
    * 是否允许下载 0否 1是
    */
    @ApiModelProperty(value="是否允许下载 0否 1是")
    @TableField(value="is_download")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "是否允许下载 0否 1是")
    private Integer isDownload;

    /**
    * file_id
    * 文件id
    */
    @ApiModelProperty(value="文件id")
    @TableField(value="file_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "文件id")
    private Long fileId;

    /**
    * file_name
    * 文件名称
    */
    @ApiModelProperty(value="文件名称")
    @TableField(value="file_name")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "文件名称")
    private String fileName;

    /**
    * file_url
    * 文件路径
    */
    @ApiModelProperty(value="文件路径")
    @TableField(value="file_url")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "文件路径")
    private String fileUrl;

    /**
     * description
     * 说明
     */
    @ApiModelProperty(value="说明")
    @TableField(value="description")
    @Column(type = DataType.VARCHAR, length = 300,  isNull = true, comment = "说明")
    private String description;
}
