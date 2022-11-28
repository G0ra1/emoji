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
 * @Description $在线学习轮播图表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-03-17 14:17:50
 */
@Data
@Table(value = "incloud_biz_study_home_banner",comment = "在线学习轮播图表")
@TableName("incloud_biz_study_home_banner")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "在线学习轮播图表 Entity")
public class StudyHomeBanner extends IModel<StudyHomeBanner> {

    /**
    * home_banner_name
    * 轮播图名称
    */
    @ApiModelProperty(value="轮播图名称")
    @TableField(value="home_banner_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "轮播图名称")
    private String homeBannerName;

    /**
    * home_banner_url
    * 轮播图路径
    */
    @ApiModelProperty(value="轮播图路径")
    @TableField(value="home_banner_url")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "轮播图路径")
    private String homeBannerUrl;

    /**
     * mobile_home_banner_url
     * 移动端轮播图路径
     */
    @ApiModelProperty(value="移动端轮播图路径")
    @TableField(value="mobile_home_banner_url")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "移动端轮播图路径")
    private String mobileHomeBannerUrl;

    /**
    * home_banner_detail
    * 轮播图详情
    */
    @ApiModelProperty(value="轮播图详情")
    @TableField(value="home_banner_detail")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "轮播图详情")
    private String homeBannerDetail;

    /**
     * special_id
     * 专题id
     */
    @ApiModelProperty(value="专题id")
    @TableField(value="special_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "专题id")
    private Long specialId;

    /**
     * special_name
     * 专题名称
     */
    @ApiModelProperty(value="专题名称")
    @TableField(value="special_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "专题名称")
    private String specialName;

    /**
    * home_banner_remarks
    * 备注
    */
    @ApiModelProperty(value="备注")
    @TableField(value="home_banner_remarks")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "备注")
    private String homeBannerRemarks;

    /**
    * home_banner_start_using
    * 是否启用
    */
    @ApiModelProperty(value="是否启用")
    @TableField(value="home_banner_start_using")
    @Column(type = DataType.INT, length = 2,  isNull = true, comment = "是否启用")
    private Integer homeBannerStartUsing;

}
