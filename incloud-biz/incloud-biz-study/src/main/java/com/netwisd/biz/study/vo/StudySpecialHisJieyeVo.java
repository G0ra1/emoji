package com.netwisd.biz.study.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
/**
 * @Description 专题历史与结业设置（子表） 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-13 14:23:33
 */
@Data
@ApiModel(value = "专题历史与结业设置（子表） Vo")
public class StudySpecialHisJieyeVo extends IVo{

    /**
     * special_id
     * 专题id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="专题id")
    private Long specialId;
    /**
     * certificate_id
     * 证书id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="证书id")
    private Long certificateId;
    /**
     * certificate_name
     * 证书名称
     */
    @ApiModelProperty(value="证书名称")
    private String certificateName;
    /**
     * exam_lowest_score
     * 考试最低分
     */
    @ApiModelProperty(value="考试最低分")
    private BigDecimal examLowestScore;
    /**
     * exam_highest_score
     * 考试最高分
     */
    @ApiModelProperty(value="考试最高分")
    private BigDecimal examHighestScore;
    /**
     * exam_rank
     * 考试等级
     */
    @ApiModelProperty(value="考试等级")
    private String examRank;



}
