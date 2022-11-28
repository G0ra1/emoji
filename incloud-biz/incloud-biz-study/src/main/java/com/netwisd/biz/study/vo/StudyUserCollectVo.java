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
 * @Description 人员收藏表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-27 11:16:09
 */
@Data
@ApiModel(value = "人员收藏表 Vo")
public class StudyUserCollectVo extends IVo{

    /**
     * user_id
     * 用户主键
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="用户主键")
    private Long userId;

    /**
     * user_name
     * 用户名
     */
    @ApiModelProperty(value="用户名")
    private String userName;

    /**
     * user_name_ch
     * 用户中文姓名
     */
    @ApiModelProperty(value="用户中文姓名")
    private String userNameCh;

    /**
     * lesson_id
     * 课程主键
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="课程主键")
    private Long lessonId;

    /**
     * lesson_name
     * 课程名称
     */
    @ApiModelProperty(value="课程名称")
    private String lessonName;
    /**
     * special_id
     * 专题主键
     */
    @JsonSerialize(using = IdTypeSerializer.class) 
    @ApiModelProperty(value="专题主键")
    private Long specialId;

    /**
     * special_name
     * 专题名称
     */
    @ApiModelProperty(value="专题名称")
    private String specialName;

}
