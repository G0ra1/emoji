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
import java.util.List;

import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @Description 仓库 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-04-22 10:19:44
 */
@Data
@ApiModel(value = "仓库 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WarehouseDto extends IDto{

    public WarehouseDto(Args args){
        super(args);
    }
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
    @JsonDeserialize(using = IdTypeDeserializer.class)
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
    @JsonDeserialize(using = IdTypeDeserializer.class)
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
    @JsonDeserialize(using = IdTypeDeserializer.class)
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

    List<ShelfDto> shelfList;
}
