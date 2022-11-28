package com.netwisd.biz.study.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "未考试结果 Vo")
public class StudyNoExaminationVo extends IVo{

    /**
     * userName
     * 账号
     */
    @ApiModelProperty(value="账号")
    private String userName;

    /**
     * userNameCh
     * 姓名
     */
    @ApiModelProperty(value="姓名")
    private String userNameCh;

    /**
     * cellNumber
     * 手机号
     */
    @ApiModelProperty(value="手机号")
    private String cellNumber;


    /**
     * gender
     * 性别
     */
    @ApiModelProperty(value="性别")
    private Integer gender;

    /**
     * mailBox
     * 邮箱
     */
    @ApiModelProperty(value="邮箱")
    private String mailBox;

    /**
     * describe
     * 描述
     */
    @ApiModelProperty(value="描述")
    private String describe;
}
