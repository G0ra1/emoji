package com.netwisd.base.portal.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 字典-菜单 功能描述...
 * @author 云数网讯 lihongyu@netwisd.com
 * @date 2020-12-17 17:35:44
 */
@Data
@ApiModel(value = "字典-菜单 Vo")
public class MenuVo extends IVo{

    /**
     * update_user_id
     * 修改人id
     */
    @ApiModelProperty(value="修改人id")
    private String updateUserId;
    /**
     * update_user_name
     * 修改人名称
     */
    @ApiModelProperty(value="修改人名称")
    private String updateUserName;
    /**
     * name
     * 菜单名称
     */
    @ApiModelProperty(value="菜单名称")
    private String name;
    /**
     * name
     * 菜单名称
     */
    @ApiModelProperty(value="菜单唯一标识")
    private String menuCode;
    /**
     * parent_id
     * 父级id
     */
    @ApiModelProperty(value="父级id")
    private String parentId;
    /**
     * parent_code
     * 父级code
     */
    @ApiModelProperty(value="父级code")
    private String parentCode;
    /**
     * parent_name
     * 父级名称
     */
    @ApiModelProperty(value="父级名称")
    private String parentName;
    /**
     * type
     * 是否末级：0否 1是
     */
    @ApiModelProperty(value="是否末级：0否 1是")
    private Integer type;
}
