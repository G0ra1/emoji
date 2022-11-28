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
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import com.netwisd.common.db.anntation.Fk;
import com.netwisd.common.db.anntation.Map;
/**
 * @Description 专题调整与对象表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-13 11:27:37
 */
@Data
@Map("incloud_biz_study_special_adj_range")
@ApiModel(value = "专题调整与对象表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudySpecialAdjRangeDto extends IDto{

    public StudySpecialAdjRangeDto(Args args){
        super(args);
    }
    /**
     * special_id
     * 培训计划id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Fk(table = "incloud_biz_study_special_adj" ,field = "id")
    @ApiModelProperty(value="培训计划id")
    private Long specialId;
    /**
     * range_type
     * 对象类型
     */
    @ApiModelProperty(value="对象类型")
    private String rangeType;
    /**
     * range_id
     * 对象id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="对象id")
    private Long rangeId;
    /**
     * range_name
     * 对象名称
     */
    @ApiModelProperty(value="对象名称")
    private String rangeName;



}
