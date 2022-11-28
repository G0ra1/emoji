package com.netwisd.base.portal.entity;

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
    import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description $文件下载类型内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-02 10:20:31
 */
@Data
@Table(value = "incloud_base_portal_content_files_son",comment = "文件下载类型内容发布子表")
@TableName("incloud_base_portal_content_files_son")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "文件下载类型内容发布子表 Entity")
public class PortalContentFilesSon extends IModel<PortalContentFilesSon> {

    /**
     * link_files_id
     * 主表ID
     */
    @ApiModelProperty(value="主表ID")
    @TableField(value="link_files_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "主表ID")
    private Long linkFilesId;
    /**
     * is_out_link
     * 是否外联文件0否1是
     */
    @ApiModelProperty(value="是否外联文件0否1是")
    @TableField(value="is_out_link")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "是否外联文件0否1是")
    private Integer isOutLink;
    /**
     * file_name
     * 文件名称
     */
    @ApiModelProperty(value="文件名称")
    @TableField(value="file_name")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "文件名称")
    private String fileName;
    /**
     * file_url
     * 文件地址
     */
    @ApiModelProperty(value="文件地址")
    @TableField(value="file_url")
    @Column(type = DataType.VARCHAR, length = 500,  isNull = true, comment = "文件地址")
    private String fileUrl;
    /**
     * file_id
     * 文件id
     */
    @ApiModelProperty(value="文件id")
    @TableField(value="file_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "文件id")
    private Long fileId;
    /**
     * hits
     * 点击量
     */
    @ApiModelProperty(value="点击量")
    @TableField(value="hits")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "点击量")
    private Long hits;
}
