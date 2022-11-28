package com.netwisd.base.wf.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.data.DataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description 按钮维护 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-02 14:45:53
 */
@Data
@ApiModel(value = "按钮维护 Vo")
public class WfDelegationVo extends IVo{
    /**
     * procdef_type_id
     * 流程定义id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="流程定义id")
    private Long procdefTypeId;
    /**
     * procdef_type_name
     * 流程定义名称
     */
    @ApiModelProperty(value="流程定义名称")
    private String procdefTypeName;
    /**
     * delegation_user_name
     * 被委托人id
     */
    @ApiModelProperty(value="被委托人id")
    private String delegationUserName;
    /**
     * delegation_user_name_ch
     * 被委托人名称
     */
    @ApiModelProperty(value="被委托人名称")
    private String delegationUserNameCh;
    /**
     * designate_user_name
     * 委托人id
     */
    @ApiModelProperty(value="委托人id")
    private String designateUserName;
    /**
     * designate_user_name_ch
     * 委托人名称
     */
    @ApiModelProperty(value="委托人名称")
    private String designateUserNameCh;
    /**
     * is_activation
     * 是否激活 0否 1是
     */
    @ApiModelProperty(value="是否激活 0否 1是")
    private Integer isActivation;
    /**
     * delegation_start_time
     * 委托开始时间
     */
    @ApiModelProperty(value="委托开始时间")
    private LocalDateTime delegationStartTime;
    /**
     * delegation_end_time
     * 委托结束时间
     */
    @ApiModelProperty(value="委托结束时间")
    private LocalDateTime delegationEndTime;

    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
}
