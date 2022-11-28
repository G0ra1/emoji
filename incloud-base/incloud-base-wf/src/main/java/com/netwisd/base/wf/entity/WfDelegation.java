package com.netwisd.base.wf.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
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
 * @Description $委托待办 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-02 14:45:53
 */
@Data
@Table(value = "incloud_base_wf_delegation",comment = "按钮维护")
@TableName("incloud_base_wf_delegation")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "委托待办 Entity")
public class WfDelegation extends IModel<WfDelegation> {

    /**
     * procdef_type_id
     * 流程定义id
     */
    @ApiModelProperty(value="流程定义id")
    @TableField(value="procdef_type_id")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "流程定义id")
    private Long procdefTypeId;
    /**
     * procdef_type_name
     * 流程定义名称
     */
    @ApiModelProperty(value="流程定义名称")
    @TableField(value="procdef_type_name")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = false, comment = "流程定义名称")
    private String procdefTypeName;
    /**
     * delegation_user_name
     * 被委托人id
     */
    @ApiModelProperty(value="被委托人id")
    @TableField(value="delegation_user_name")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = false, comment = "被委托人id")
    private String delegationUserName;
    /**
     * delegation_user_name_ch
     * 被委托人名称
     */
    @ApiModelProperty(value="被委托人名称")
    @TableField(value="delegation_user_name_ch")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = false, comment = "被委托人名称")
    private String delegationUserNameCh;
    /**
     * designate_user_name
     * 委托人id  例如张三委托李四  张三是委托人  李四是被委托人
     */
    @ApiModelProperty(value="委托人id")
    @TableField(value="designate_user_name")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = false, comment = "委托人id")
    private String designateUserName;
    /**
     * designate_user_name_ch
     * 委托人名称
     */
    @ApiModelProperty(value="委托人名称")
    @TableField(value="designate_user_name_ch")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = false, comment = "委托人名称")
    private String designateUserNameCh;
    /**
     * is_activation
     * 是否激活 0否 1是
     */
    @ApiModelProperty(value="是否激活 0否 1是")
    @TableField(value="is_activation")
    @Column(type = DataType.INT, length = 1,  isNull = false, comment = "是否激活 0否 1是")
    private Integer isActivation;
    /**
     * delegation_start_time
     * 委托开始时间
     */
    @ApiModelProperty(value="委托开始时间")
    @TableField(value="delegation_start_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = false, comment = "委托开始时间")
    private LocalDateTime delegationStartTime;
    /**
     * delegation_end_time
     * 委托结束时间
     */
    @ApiModelProperty(value="委托结束时间")
    @TableField(value="delegation_end_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = false, comment = "委托结束时间")
    private LocalDateTime delegationEndTime;

    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    @TableField(value="remark")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "备注")
    private String remark;
}
