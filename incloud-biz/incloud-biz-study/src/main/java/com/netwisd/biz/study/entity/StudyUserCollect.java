package com.netwisd.biz.study.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.anntation.Table;
import com.netwisd.common.db.data.DataType;
import com.netwisd.common.db.data.IModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
    import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description $人员收藏表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-27 11:16:09
 */
@Data
@Table(value = "incloud_biz_study_user_collect",comment = "人员收藏表")
@TableName("incloud_biz_study_user_collect")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "人员收藏表 Entity")
public class StudyUserCollect extends IModel<StudyUserCollect> {

    /**
    * user_id
    * 用户主键
    */
    @ApiModelProperty(value="用户主键")
    @TableField(value="user_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "用户主键")
    private Long userId;

    /**
    * user_name
    * 用户名
    */
    @ApiModelProperty(value="用户名")
    @TableField(value="user_name")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "用户名")
    private String userName;

    /**
    * user_name_ch
    * 用户中文姓名
    */
    @ApiModelProperty(value="用户中文姓名")
    @TableField(value="user_name_ch")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "用户中文姓名")
    private String userNameCh;

    /**
    * lesson_id
    * 课程主键
    */
    @ApiModelProperty(value="课程主键")
    @TableField(value="lesson_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "课程主键")
    private Long lessonId;

    /**
    * lesson_name
    * 课程名称
    */
    @ApiModelProperty(value="课程名称")
    @TableField(value="lesson_name")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "课程名称")
    private String lessonName;

    /**
    * special_id
    * 专题主键
    */
    @ApiModelProperty(value="专题主键")
    @TableField(value="special_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "专题主键")
    private Long specialId;

    /**
    * special_name
    * 专题名称
    */
    @ApiModelProperty(value="专题名称")
    @TableField(value="special_name")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = true, comment = "专题名称")
    private String specialName;

}
