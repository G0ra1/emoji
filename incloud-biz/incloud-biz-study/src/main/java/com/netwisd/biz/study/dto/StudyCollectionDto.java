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
import org.hibernate.validator.constraints.Range;

/**
 * @Description 收藏表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-06 14:55:57
 */
@Data
@ApiModel(value = "收藏表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudyCollectionDto extends IDto{

    public StudyCollectionDto(Args args){
        super(args);
    }
    /**
     * user_id
     * 人员id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="人员id")
    private Long userId;

    /**
     * collection_type
     * 收藏类型（0课程；1专题）
     */
    @ApiModelProperty(value="收藏类型（0课程；1专题）")
    private Integer collectionType;

    /**
     * collection_id
     * 收藏id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="收藏id")
    private Long collectionId;
    /**
     * collection_name
     * 收藏名称
     */
    @ApiModelProperty(value="收藏名称")
    private String collectionName;

}
