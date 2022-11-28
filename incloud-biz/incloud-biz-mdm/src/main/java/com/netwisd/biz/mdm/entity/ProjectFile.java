package com.netwisd.biz.mdm.entity;

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
 * @Description $物资 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-25 14:31:13
 */
@Data
@Table(value = "incloud_biz_mdm_project_file",comment = "物资")
@TableName("incloud_biz_mdm_project_file")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "物资 Entity")
public class ProjectFile extends IModel<ProjectFile> {

    /**
     * project_id
     * 项目id
     */
    @ApiModelProperty(value="项目id")
    @TableField(value="project_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "项目id")
    private Long projectId;
    /**
     * project_code
     * 项目编码
     */
    @ApiModelProperty(value="项目编码")
    @TableField(value="project_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "项目编码")
    private String projectCode;
    /**
     * file_name
     * 附件名称
     */
    @ApiModelProperty(value="附件名称")
    @TableField(value="file_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "附件名称")
    private String fileName;
    /**
     * file_add
     * 附件地址
     */
    @ApiModelProperty(value="附件地址")
    @TableField(value="file_url")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "附件地址")
    private String fileUrl;
}
