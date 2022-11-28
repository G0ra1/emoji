package com.netwisd.biz.study.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 在线学习人员表 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-11-22 09:59:58
 */
@Data
@ApiModel(value = "在线学习人员表 Vo")
public class StudyUserVo extends IVo{

    /**
     * user_name_ch
     * 用户中文姓名
     */
    
    @ApiModelProperty(value="用户中文姓名")
    private String userNameCh;
    /**
     * phone_num
     * 用户手机号
     */
    
    @ApiModelProperty(value="用户手机号")
    private String phoneNum;
    /**
     * user_name
     * 用户名
     */
    
    @ApiModelProperty(value="用户名")
    private String userName;
    /**
     * pass_word
     * 密码
     */
    
    @ApiModelProperty(value="密码")
    private String passWord;
    /**
     * card_type
     * 证件类型 0居民身份证；1港澳居民来往内地通信证；2港澳居民居住证；3台湾居民来往大陆通行证；4台湾居民居住证；5外国护照；6外国人永久居留身份证；7外国人居留证；
     */
    
    @ApiModelProperty(value="证件类型 0居民身份证；1港澳居民来往内地通信证；2港澳居民居住证；3台湾居民来往大陆通行证；4台湾居民居住证；5外国护照；6外国人永久居留身份证；7外国人居留证；")
    private Integer cardType;
    /**
     * id_card
     * 证件号
     */
    
    @ApiModelProperty(value="证件号")
    private String idCard;
    /**
     * email
     * 邮箱
     */
    
    @ApiModelProperty(value="邮箱")
    private String email;
    /**
     * user_type
     * 人员类型 0管理员，1讲师、2学员
     */
    
    @ApiModelProperty(value="人员类型 0管理员，1讲师、2学员")
    private Integer userType;
    /**
     * sex
     * 性别 0男，1女
     */
    
    @ApiModelProperty(value="性别 0男，1女")
    private Integer sex;
    /**
     * description
     * 描述
     */
    
    @ApiModelProperty(value="描述")
    private String description;
    /**
     * user_status
     * 人员审核状态 0未审核，1已审核
     */
    
    @ApiModelProperty(value="人员审核状态 0未审核，1已审核")
    private Integer userStatus;
    /**
     * status
     * 状态 0停用，1启用
     */
    
    @ApiModelProperty(value="状态 0停用，1启用")
    private Integer status;
    /**
     * master_data_id
     * 主数据人员表id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="主数据人员表id")
    private Long masterDataId;
}
