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
 * @Description 浏览记录表 功能描述...
 * @author 云数网讯 sun@netwisd.com
 * @date 2022-05-24 11:10:47
 */
@Data
@ApiModel(value = "浏览记录表 Vo")
public class StudyBrowseVo extends IVo{

    /**
     * user_id
     * 人员id
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
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
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="浏览id(课程或专题的id)")
    private Long fkId;
}
