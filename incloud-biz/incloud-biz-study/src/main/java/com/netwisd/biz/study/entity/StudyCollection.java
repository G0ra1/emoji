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
 * @Description $收藏表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-06 14:55:57
 */
@Data
@Table(value = "incloud_biz_study_collection",comment = "收藏表")
@TableName("incloud_biz_study_collection")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "收藏表 Entity")
public class StudyCollection extends IModel<StudyCollection> {

    /**
    * user_id
    * 人员id
    */
    @ApiModelProperty(value="人员id")
    @TableField(value="user_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "人员id")
    private Long userId;

    /**
    * collection_type
    * 收藏类型（0课程；1专题）
    */
    @ApiModelProperty(value="收藏类型（0课程；1专题）")
    @TableField(value="collection_type")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "收藏类型（0课程；1专题）")
    private Integer collectionType;

    /**
    * collection_id
    * 收藏id
    */
    @ApiModelProperty(value="收藏id")
    @TableField(value="collection_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "收藏id")
    private Long collectionId;

}
