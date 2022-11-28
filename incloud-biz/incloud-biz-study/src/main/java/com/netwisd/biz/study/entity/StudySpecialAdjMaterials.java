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
    import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description $专题调整与学习资料表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-13 11:27:37
 */
@Data
@Table(value = "incloud_biz_study_special_adj_materials",comment = "专题调整与学习资料表")
@TableName("incloud_biz_study_special_adj_materials")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "专题调整与学习资料表 Entity")
public class StudySpecialAdjMaterials extends IModel<StudySpecialAdjMaterials> {

    /**
     * special_id
     * 计划id
     */
    @ApiModelProperty(value="计划id")
    @TableField(value="special_id")
    @Column(type = DataType.BIGINT, length = 19, fkTableName = "incloud_biz_study_special_adj" ,fkFieldName = "id" , isNull = true, comment = "计划id")
    private Long specialId;
    /**
     * materials_id
     * 资料id
     */
    @ApiModelProperty(value="资料id")
    @TableField(value="materials_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "资料id")
    private Long materialsId;
    /**
     * materials_name
     * 资料名称
     */
    @ApiModelProperty(value="资料名称")
    @TableField(value="materials_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "资料名称")
    private String materialsName;
    /**
     * materials_type_code
     * 资料分类编码
     */
    @ApiModelProperty(value="资料分类编码")
    @TableField(value="materials_type_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "资料分类编码")
    private String materialsTypeCode;
    /**
     * materials_type_name
     * 资料分类名称
     */
    @ApiModelProperty(value="资料分类名称")
    @TableField(value="materials_type_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "资料分类名称")
    private String materialsTypeName;
    /**
     * is_download
     * 是否允许下载
     */
    @ApiModelProperty(value="是否允许下载")
    @TableField(value="is_download")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "是否允许下载")
    private Integer isDownload;
    /**
     * create_user_id
     * 创建人ID
     */
    @ApiModelProperty(value="创建人ID")
    @TableField(value="create_user_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "创建人ID")
    private Long createUserId;
    /**
     * create_user_name
     * 创建人名称
     */
    @ApiModelProperty(value="创建人名称")
    @TableField(value="create_user_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "创建人名称")
    private String createUserName;
    /**
     * create_user_parent_org_id
     * 创建人父级机构ID
     */
    @ApiModelProperty(value="创建人父级机构ID")
    @TableField(value="create_user_parent_org_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "创建人父级机构ID")
    private Long createUserParentOrgId;
    /**
     * create_user_parent_org_name
     * 创建人父级机构名称
     */
    @ApiModelProperty(value="创建人父级机构名称")
    @TableField(value="create_user_parent_org_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "创建人父级机构名称")
    private String createUserParentOrgName;
    /**
     * create_user_parent_dept_id
     * 创建人父级部门ID
     */
    @ApiModelProperty(value="创建人父级部门ID")
    @TableField(value="create_user_parent_dept_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "创建人父级部门ID")
    private Long createUserParentDeptId;
    /**
     * create_user_parent_dept_name
     * 创建人父级部门名称
     */
    @ApiModelProperty(value="创建人父级部门名称")
    @TableField(value="create_user_parent_dept_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "创建人父级部门名称")
    private String createUserParentDeptName;
    /**
     * create_user_org_full_id
     * 创建人父级组织全路径ID
     */
    @ApiModelProperty(value="创建人父级组织全路径ID")
    @TableField(value="create_user_org_full_id")
    @Column(type = DataType.VARCHAR, length = 2000,  isNull = true, comment = "创建人父级组织全路径ID")
    private String createUserOrgFullId;

}
