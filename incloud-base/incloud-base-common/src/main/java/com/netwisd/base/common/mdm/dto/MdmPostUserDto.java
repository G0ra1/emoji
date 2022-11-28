package com.netwisd.base.common.mdm.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.common.core.data.IDto;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description 岗位与用户关系 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-26 11:47:13
 */
@Data
@ApiModel(value = "岗位与用户关系 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MdmPostUserDto extends IDto{

    public MdmPostUserDto(Args args){
        super(args);
    }

    /**
     * org_full_post_id
     * 组织全路径岗位ID
     */
    @ApiModelProperty(value="组织全路径岗位ID")
    @Valid(length = 4000) 
    private String orgFullPostId;

    /**
     * org_full_post_name
     * 组织全路径岗位名称
     */
    @ApiModelProperty(value="组织全路径岗位名称")
    private String orgFullPostName;

    /**
     * post_id
     * 岗位ID
     */
    @ApiModelProperty(value="岗位ID")
    @Valid(length = 20)
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long postId;

    /**
     * post_code
     * 岗位code
     */
    @ApiModelProperty(value="岗位code")
    @Valid(length = 255) 
    private String postCode;

    /**
     * post_name
     * 岗位名称
     */
    @ApiModelProperty(value="岗位名称")
    @Valid(length = 255) 
    private String postName;

    /**
     * user_id
     * 用户ID
     */
    @ApiModelProperty(value="用户ID")
    @Valid(length = 20)
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long userId;

    /**
     * user_name
     * 用户名称
     */
    @ApiModelProperty(value="用户名称")
    @Valid(length = 255) 
    private String userName;

    /**
     * user_name_ch
     * 用户中文名称
     */
    @ApiModelProperty(value="用户中文名称")
    @Valid(length = 255) 
    private String userNameCh;

    /**
     * is_master
     * 是否主岗
     */
    @ApiModelProperty(value="是否主岗")
    @Valid(length = 1) 
    private Integer isMaster;

}
