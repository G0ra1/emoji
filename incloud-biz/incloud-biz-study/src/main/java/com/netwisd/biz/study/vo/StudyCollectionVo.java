package com.netwisd.biz.study.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author 云数网讯 cr@netwisd.com
 * @Description 收藏表 功能描述...
 * @date 2022-05-06 14:55:57
 */
@Data
@ApiModel(value = "收藏表 Vo")
public class StudyCollectionVo extends IVo {

    /**
     * user_id
     * 人员id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value = "人员id")
    private Long userId;
    /**
     * collection_type
     * 收藏类型（0课程；1专题）
     */

    @ApiModelProperty(value = "收藏类型（0课程；1专题）")
    private Integer collectionType;
    /**
     * collection_id
     * 收藏id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value = "收藏id")
    private Long collectionId;


}
