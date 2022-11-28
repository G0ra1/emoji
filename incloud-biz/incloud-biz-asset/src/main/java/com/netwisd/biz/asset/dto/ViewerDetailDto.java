package com.netwisd.biz.asset.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.base.common.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import com.netwisd.common.db.anntation.Fk;
import com.netwisd.common.db.anntation.Map;
/**
 * @Description 物资数据权限范围表 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-08-01 10:14:45
 */
@Data
@Map("incloud_biz_asset_viewer_detail")
@ApiModel(value = "物资数据权限范围表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ViewerDetailDto extends IDto{

    public ViewerDetailDto(Args args){
        super(args);
    }
    /**
     * viewer_id
     * 人员表id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Fk(table = "incloud_biz_asset_viewer" ,field = "id")
    @ApiModelProperty(value="人员表id")
    private Long viewerId;
    /**
     * visible_type
     * 可见类型;1人员2部门/机构
     */
    @ApiModelProperty(value="可见类型;1人员2部门/机构")
    private Integer visibleType;
    /**
     * range_id
     * 可见的人或者部门ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="可见的人或者部门ID")
    private Long rangeId;
    /**
     * range_name
     * 可见的人或者部门名称
     */
    @ApiModelProperty(value="可见的人或者部门名称")
    private String rangeName;
    /**
     * create_user_id
     * 创建人ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="创建人ID")
    private Long createUserId;
    /**
     * create_user_name
     * 创建人名称
     */
    @ApiModelProperty(value="创建人名称")
    private String createUserName;
    /**
     * create_user_parent_org_id
     * 创建人父级机构ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="创建人父级机构ID")
    private Long createUserParentOrgId;
    /**
     * create_user_parent_org_name
     * 创建人父级机构名称
     */
    @ApiModelProperty(value="创建人父级机构名称")
    private String createUserParentOrgName;
    /**
     * create_user_parent_dept_id
     * 创建人父级部门ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="创建人父级部门ID")
    private Long createUserParentDeptId;
    /**
     * create_user_parent_dept_name
     * 创建人父级部门名称
     */
    @ApiModelProperty(value="创建人父级部门名称")
    private String createUserParentDeptName;
    /**
     * create_user_org_full_id
     * 创建人父级组织全路径ID
     */
    @ApiModelProperty(value="创建人父级组织全路径ID")
    private String createUserOrgFullId;


}
