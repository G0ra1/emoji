package com.netwisd.biz.study.entity;

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
 * @Description $在线学习人员表 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-11-22 09:59:58
 */
@Data
@Table(value = "incloud_biz_study_user",comment = "在线学习人员表")
@TableName("incloud_biz_study_user")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "在线学习人员表 Entity")
public class StudyUser extends IModel<StudyUser> {

    /**
    * user_name_ch
    * 用户中文姓名
    */
    @ApiModelProperty(value="用户中文姓名")
    @TableField(value="user_name_ch")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "用户中文姓名")
    private String userNameCh;

    /**
    * phone_num
    * 用户手机号
    */
    @ApiModelProperty(value="用户手机号")
    @TableField(value="phone_num")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "用户手机号")
    private String phoneNum;

    /**
    * user_name
    * 用户名
    */
    @ApiModelProperty(value="用户名")
    @TableField(value="user_name")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = false, comment = "用户名")
    private String userName;

    /**
    * pass_word
    * 密码
    */
    @ApiModelProperty(value="密码")
    @TableField(value="pass_word")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "密码")
    private String passWord;

    /**
    * card_type
    * 证件类型 0居民身份证；1港澳居民来往内地通信证；2港澳居民居住证；3台湾居民来往大陆通行证；4台湾居民居住证；5外国护照；6外国人永久居留身份证；7外国人居留证；
    */
    @ApiModelProperty(value="证件类型 0居民身份证；1港澳居民来往内地通信证；2港澳居民居住证；3台湾居民来往大陆通行证；4台湾居民居住证；5外国护照；6外国人永久居留身份证；7外国人居留证；")
    @TableField(value="card_type")
    @Column(type = DataType.INT, length = 11,  isNull = true, comment = "证件类型 0居民身份证；1港澳居民来往内地通信证；2港澳居民居住证；3台湾居民来往大陆通行证；4台湾居民居住证；5外国护照；6外国人永久居留身份证；7外国人居留证；")
    private Integer cardType;

    /**
    * id_card
    * 证件号
    */
    @ApiModelProperty(value="证件号")
    @TableField(value="id_card")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "证件号")
    private String idCard;

    /**
    * email
    * 邮箱
    */
    @ApiModelProperty(value="邮箱")
    @TableField(value="email")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "邮箱")
    private String email;

    /**
    * user_type
    * 人员类型 0管理员，1讲师、2学员
    */
    @ApiModelProperty(value="人员类型 0管理员，1讲师、2学员")
    @TableField(value="user_type")
    @Column(type = DataType.INT, length = 2,  isNull = false, comment = "人员类型 0管理员，1讲师、2学员")
    private Integer userType;

    /**
    * sex
    * 性别 0男，1女
    */
    @ApiModelProperty(value="性别 0男，1女")
    @TableField(value="sex")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "性别 0男，1女")
    private Integer sex;

    /**
    * description
    * 描述
    */
    @ApiModelProperty(value="描述")
    @TableField(value="description")
    @Column(type = DataType.VARCHAR, length = 2048,  isNull = true, comment = "描述")
    private String description;

    /**
    * user_status
    * 人员审核状态 0未审核，1已审核
    */
    @ApiModelProperty(value="人员审核状态 0未审核，1已审核")
    @TableField(value="user_status")
    @Column(type = DataType.INT, length = 2,  isNull = true, comment = "人员审核状态 0未审核，1审核通过，2审核不通过")
    private Integer userStatus;

    /**
    * status
    * 状态 0停用，1启用
    */
    @ApiModelProperty(value="状态 0停用，1启用")
    @TableField(value="status")
    @Column(type = DataType.INT, length = 2,  isNull = true, comment = "状态 0停用，1启用")
    private Integer status;

    /**
    * master_data_id
    * 主数据人员表id
    */
    @ApiModelProperty(value="主数据人员表id")
    @TableField(value="master_data_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "主数据人员表id")
    private Long masterDataId;

}
