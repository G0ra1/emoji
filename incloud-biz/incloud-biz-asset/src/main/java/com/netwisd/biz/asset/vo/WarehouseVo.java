package com.netwisd.biz.asset.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 仓库 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-04-22 10:19:44
 */
@Data
@ApiModel(value = "仓库 Vo")
public class WarehouseVo extends IVo{

    /**
     * warehouse_name
     * 仓库名称
     */
    
    @ApiModelProperty(value="仓库名称")
    private String warehouseName;
    /**
     * org_id
     * 所属机构
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="所属机构")
    private Long orgId;
    /**
     * org_name
     * 所属机构
     */
    @ApiModelProperty(value="所属机构")
    private String orgName;

    /**
     * org_full_id
     * 父级组织全路径ID
     */

    @Valid(length = 2000)
    @ApiModelProperty(value="父级组织全路径ID")
    private String orgFullId;

    /**
     * org_full_name
     * 父级组织全路径名称
     */

    @Valid(length = 4000)
    @ApiModelProperty(value="父级组织全路径名称")
    private String orgFullName;
    /**
     * dept_id
     * 所属部门
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="所属部门")
    private Long deptId;
    /**
     * dept_name
     * 所属部门
     */
    @ApiModelProperty(value="所属部门")
    private String deptName;
    /**
     * address
     * 仓库地点
     */
    @ApiModelProperty(value="仓库地点")
    private String address;
    /**
     * respond_user_id
     * 责任人
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="责任人")
    private Long respondUserId;
    /**
     * respond_user_name
     * 责任人
     */
    @ApiModelProperty(value="责任人")
    private String respondUserName;

    /**
     * del_flag
     * 删除标识
     */
    
    @ApiModelProperty(value="删除标识")
    private String delFlag;

    /**
     * house_type
     * 类型
     */
    @ApiModelProperty(value="类型")
    private String houseType;

    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;

    List<ShelfVo> shelfList;
}
