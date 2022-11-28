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
 * @Description $浏览记录表 功能描述...
 * @author 云数网讯 sun@netwisd.com
 * @date 2022-05-24 11:10:47
 */
@Data
@Table(value = "incloud_biz_study_browse",comment = "浏览记录表")
@TableName("incloud_biz_study_browse")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "浏览记录表 Entity")
public class StudyBrowse extends IModel<StudyBrowse> {

    /**
    * user_id
    * 人员id
    */
    @ApiModelProperty(value="人员id")
    @TableField(value="user_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "人员id")
    private Long userId;

    /**
    * browse_type
    * 浏览类型(0课程,1专题)
    */
    @ApiModelProperty(value="浏览类型(0课程,1专题)")
    @TableField(value="browse_type")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "浏览类型(0课程,1专题)")
    private Integer browseType;

    /**
    * fk_id
    * 浏览id(课程或专题的id)
    */
    @ApiModelProperty(value="浏览id(课程或专题的id)")
    @TableField(value="fk_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "浏览id(课程或专题的id)")
    private Long fkId;

}
