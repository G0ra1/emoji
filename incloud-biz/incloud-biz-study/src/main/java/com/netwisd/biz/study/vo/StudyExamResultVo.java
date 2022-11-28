package com.netwisd.biz.study.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@ApiModel(value = "考试结果 Vo")
public class StudyExamResultVo extends IVo{

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
     * fraction
     * 得分
     */
    @ApiModelProperty(value="得分")
    private BigDecimal fraction;


    /**
     * completionTime
     * 完成时间
     */
    @ApiModelProperty(value="完成时间")
    private LocalDateTime completionTime;

    /**
     * timeUse
     * 用时
     */
    @ApiModelProperty(value="用时")
    private Integer timeUse;

    /**
     * isScoring
     * 是否判分
     */
    @ApiModelProperty(value="是否判分")
    private Integer isScoring;

}
