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
 * @Description $用户详情 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-26 09:09:37
 */
@Data
@Table(value = "incloud_base_mdm_user_detail",comment = "用户详情")
@TableName("incloud_base_mdm_user_detail")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "用户详情 Entity")
public class MdmUserDetail extends IModel<MdmUserDetail> {

    /**
     * user_id
     * 用户id
     */
    @ApiModelProperty(value="用户id")
    @TableField(value="user_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "用户id")
    private Long userId;
    /**
     * contact_name
     * 紧急联系人姓名
     */
    @ApiModelProperty(value="紧急联系人姓名")
    @TableField(value="contact_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "紧急联系人姓名")
    private String contactName;
    /**
     * contact_with_oneself
     * 紧急联系人与本人关系
     */
    @ApiModelProperty(value="紧急联系人与本人关系")
    @TableField(value="contact_with_oneself")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "紧急联系人与本人关系")
    private String contactWithOneself;
    /**
     * contact_num
     * 紧急联系人联系电话
     */
    @ApiModelProperty(value="紧急联系人联系电话")
    @TableField(value="contact_num")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "紧急联系人联系电话")
    private String contactNum;
    /**
     * contact_addr
     * 紧急联系人联系地址
     */
    @ApiModelProperty(value="紧急联系人联系地址")
    @TableField(value="contact_addr")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "紧急联系人联系地址")
    private String contactAddr;
    /**
     * contact_email
     * 紧急联系人电子邮箱
     */
    @ApiModelProperty(value="紧急联系人电子邮箱")
    @TableField(value="contact_email")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "紧急联系人电子邮箱")
    private String contactEmail;
    /**
     * contact_zipcode
     * 紧急联系人邮政编码
     */
    @ApiModelProperty(value="紧急联系人邮政编码")
    @TableField(value="contact_zipcode")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "紧急联系人邮政编码")
    private String contactZipcode;
    /**
     * job_level
     * 职级
     */
    @ApiModelProperty(value="职级")
    @TableField(value="job_level")
    @Column(type = DataType.INT, length = 11,  isNull = true, comment = "职级")
    private Integer jobLevel;
    /**
     * job_level_start_time
     * 职级开始时间
     */
    @ApiModelProperty(value="职级开始时间")
    @TableField(value="job_level_start_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "职级开始时间")
    private LocalDateTime jobLevelStartTime;
    /**
     * job_level_end_time
     * 职级结束时间
     */
    @ApiModelProperty(value="职级结束时间")
    @TableField(value="job_level_end_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "职级结束时间")
    private LocalDateTime jobLevelEndTime;
    /**
     * job_level_year
     * 任职级年限
     */
    @ApiModelProperty(value="任职级年限")
    @TableField(value="job_level_year")
    @Column(type = DataType.INT, length = 11,  isNull = true, comment = "任职级年限")
    private Integer jobLevelYear;
    /**
     * duty_level_start_time
     * 职务开始时间
     */
    @ApiModelProperty(value="职务开始时间")
    @TableField(value="duty_level_start_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "职务开始时间")
    private LocalDateTime dutyLevelStartTime;
    /**
     * duty_level_end_time
     * 职务结束时间
     */
    @ApiModelProperty(value="职务结束时间")
    @TableField(value="duty_level_end_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "职务结束时间")
    private LocalDateTime dutyLevelEndTime;
    /**
     * duty_id
     * 职务ID
     */
    @ApiModelProperty(value="职务ID")
    @TableField(value="duty_id")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "职务ID")
    private String dutyId;
    /**
     * duty_name
     * 职务名称
     */
    @ApiModelProperty(value="职务名称")
    @TableField(value="duty_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "职务名称")
    private String dutyName;
    /**
     * duty_in_charge
     * 主管或从事工作
     */
    @ApiModelProperty(value="主管或从事工作")
    @TableField(value="duty_in_charge")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "主管或从事工作")
    private String dutyInCharge;
    /**
     * duty_is_main
     * 首要职务;0否；1是；
     */
    @ApiModelProperty(value="首要职务;0否；1是；")
    @TableField(value="duty_is_main")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "首要职务;0否；1是；")
    private String dutyIsMain;
    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    @TableField(value="remark")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "备注")
    private String remark;
}
