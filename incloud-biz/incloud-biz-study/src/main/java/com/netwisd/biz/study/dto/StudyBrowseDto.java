package com.netwisd.biz.study.dto;

import com.netwisd.base.common.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @Description 浏览记录表 功能描述...
 * @author 云数网讯 sun@netwisd.com
 * @date 2022-05-24 11:10:47
 */
@Data
@ApiModel(value = "浏览记录表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudyBrowseDto extends IDto{

    /**
     * user_id
     * 人员id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="人员id")
    private Long userId;

    /**
     * browse_type
     * 浏览类型(0课程,1专题)
     */
    @ApiModelProperty(value="浏览类型(0课程,1专题)")
    private Integer browseType;

    /**
     * fk_id
     * 浏览id(课程或专题的id)
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="浏览id(课程或专题的id)")
    private Long fkId;

    /**
     * browse_name
     * 浏览名称
     */
    @ApiModelProperty(value="浏览名称")
    private String browseName;
}
