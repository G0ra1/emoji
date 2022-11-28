package com.netwisd.base.portal.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description 字典-数据 功能描述...
 * @author 云数网讯 lihongyu@netwisd.com
 * @date 2020-12-16 10:41:22
 */
@Data
@ApiModel(value = "字典-数据 Vo")
public class RowVo extends IVo{

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
     * code
     * 编码
     */
    @ApiModelProperty(value="编码")
    private String code;
    /**
     * name
     * 名称
     */
    @ApiModelProperty(value="名称")
    private String name;
    /**
     * menu_id
     * 关联菜单id
     */
    @ApiModelProperty(value="关联菜单id")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long menuId;
    /**
     * menu_code
     * 关联菜单唯一编码
     */
    @ApiModelProperty(value="关联菜单唯一编码")
    private String menuCode;
    /**
     * menu_name
     * 关联菜单名称
     */
    @ApiModelProperty(value="关联菜单名称")
    private String menuName;
    /**
     * is_perm
     * 是否开启授权 0否  1是
     */
    @ApiModelProperty(value="是否开启授权 0否  1是")
    private Integer isPerm;
    /**
     * commDictPerms
     * 授权信息
     */
    @ApiModelProperty(value="授权信息")
    List<CommDictPermVo> commDictPerms;
}
