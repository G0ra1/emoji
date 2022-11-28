package com.netwisd.biz.study.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.base.common.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import com.netwisd.common.db.anntation.Fk;
import com.netwisd.common.db.anntation.Map;
/**
 * @Description 专题结业设置（子表）;证书 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-13 10:59:05
 */
@Data
@Map("incloud_biz_study_special_jieye")
@ApiModel(value = "专题结业设置（子表）;证书 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudySpecialJieyeDto extends IDto{

    public StudySpecialJieyeDto(Args args){
        super(args);
    }
    /**
     * special_id
     * 专题id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Fk(table = "incloud_biz_study_special" ,field = "id")
    @ApiModelProperty(value="专题id")
    private Long specialId;
    /**
     * certificate_id
     * 证书id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
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
