package com.netwisd.biz.mdm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.anntation.Table;
import com.netwisd.common.db.data.DataType;
import com.netwisd.common.db.data.IModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description 集采项目数据传送 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-19 15:18:00
 */
@Data
@Table(value = "incloud_biz_mdm_projectjc",comment = "集采项目")
@TableName("incloud_biz_mdm_projectjc")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "集采项目 Entity")
public class Projectjc  extends IModel<Projectjc> {
    /**
     * project_id
     * 项目编码
     */
    @ApiModelProperty(value="项目id")
    @TableField(value="project_id")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "项目id")
    private String projectId;//项目ID
    /**
     * project_code
     * 项目编码
     */
    @ApiModelProperty(value="项目编码")
    @TableField(value="project_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "项目编码")
    private String projectCode;//项目编码
    /**
     * project_name
     * 项目编码
     */
    @ApiModelProperty(value="项目名称")
    @TableField(value="project_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "项目名称")
    private String projectName;//项目名称

    /**
     * orgnization_id
     * 所属组织机构ID
     */
    @ApiModelProperty(value="所属组织机构ID")
    @TableField(value="org_id")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "所属组织机构ID")
    private String orgId;//所属组织机构ID

    /**
     * org_code
     * 所属组织机构编码
     */
    @ApiModelProperty(value="所属组织机构编码")
    @TableField(value="org_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "所属组织机构编码")
    private String orgCode;//所属组织机构编码

    /**
     * org_name
     * 所属组织机构名称
     */
    @ApiModelProperty(value="所属组织机构名称")
    @TableField(value="org_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "所属组织机构名称")
    private String orgName;//所属组织机构名称
    /**
     * suborg_ids
     * 二级单位ID
     */
    @ApiModelProperty(value="二级单位ID")
    @TableField(value="suborg_ids")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "二级单位ID")
    private String suborgIds;//二级单位ID
    /**
     * suborg_names
     * 二级单位名称
     */
    @ApiModelProperty(value="二级单位名称")
    @TableField(value="suborg_names")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "二级单位名称")
    private String suborgNames;//二级单位名称
    /**
     * is_live
     * 是否在建（0：非在建，1：在建）
     */
    @ApiModelProperty(value="是否在建（0：非在建，1：在建）")
    @TableField(value="is_live")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "是否在建（0：非在建，1：在建）")
    private String isLive;//是否在建（0：非在建，1：在建）


}
