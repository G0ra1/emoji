package com.netwisd.base.mdm.entity;

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
 * @Description $职务 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-09-27 10:45:35
 */
@Data
@Table(value = "incloud_base_mdm_duty_user",comment = "职务与用户关系")
@TableName("incloud_base_mdm_duty_user")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "职务与用户关系 Entity")
public class MdmDutyUser extends IModel<MdmDutyUser> {

    /**
     * org_full_duty_id
     * 组织全路径职务ID
     */
    @ApiModelProperty(value="组织全路径职务ID")
    @TableField(value="org_full_duty_id")
    @Column(type = DataType.VARCHAR, length = 4000,  isNull = false, comment = "组织全路径职务ID")
    private String orgFullDutyId;
    /**
     * org_full_duty_name
     * 组织全路径职务名称
     */
    @ApiModelProperty(value="组织全路径职务名称")
    @TableField(value="org_full_duty_name")
    @Column(type = DataType.VARCHAR, length = 4000,  isNull = false, comment = "组织全路径职务名称")
    private String orgFullDutyName;
    /**
     * duty_id
     * 职务ID
     */
    @ApiModelProperty(value="职务ID")
    @TableField(value="duty_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "职务ID")
    private Long dutyId;
    /**
     * duty_code
     * 职务code
     */
    @ApiModelProperty(value="职务code")
    @TableField(value="duty_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "职务code")
    private String dutyCode;
    /**
     * duty_name
     * 职务名称
     */
    @ApiModelProperty(value="职务名称")
    @TableField(value="duty_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "职务名称")
    private String dutyName;
    /**
     * user_id
     * 用户ID
     */
    @ApiModelProperty(value="用户ID")
    @TableField(value="user_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "用户ID")
    private Long userId;
    /**
     * user_name
     * 用户名称
     */
    @ApiModelProperty(value="用户名称")
    @TableField(value="user_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "用户名称")
    private String userName;
    /**
     * user_name_ch
     * 用户中文名称
     */
    @ApiModelProperty(value="用户中文名称")
    @TableField(value="user_name_ch")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "用户中文名称")
    private String userNameCh;
    /**
     * is_master
     * 是否主岗
     */
    @ApiModelProperty(value="是否主岗")
    @TableField(value="is_master")
    @Column(type = DataType.INT, length = 1,  isNull = false, comment = "是否主岗")
    private Integer isMaster;
}
