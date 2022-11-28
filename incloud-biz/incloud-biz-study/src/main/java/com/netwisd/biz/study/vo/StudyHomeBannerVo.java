package com.netwisd.biz.study.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @Description 在线学习轮播图表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-03-17 14:17:50
 */
@Data
@ApiModel(value = "在线学习轮播图表 Vo")
public class StudyHomeBannerVo extends IVo{

    /**
     * home_banner_name
     * 轮播图名称
     */
    
    @ApiModelProperty(value="轮播图名称")
    private String homeBannerName;
    /**
     * home_banner_url
     * 轮播图路径
     */
    
    @ApiModelProperty(value="轮播图路径")
    private String homeBannerUrl;

    /**
     * mobile_home_banner_url
     * 移动端轮播图路径
     */
    @ApiModelProperty(value="移动端轮播图路径")
    private String mobileHomeBannerUrl;

    /**
     * home_banner_detail
     * 轮播图详情
     */
    @ApiModelProperty(value="轮播图详情")
    private String homeBannerDetail;

    /**
     * special_id
     * 专题id
     */
    @ApiModelProperty(value="专题id")
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long specialId;

    /**
     * special_name
     * 专题名称
     */
    @ApiModelProperty(value="专题名称")
    private String specialName;

    /**
     * home_banner_remarks
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String homeBannerRemarks;

    /**
     * home_banner_start_using
     * 是否启用
     */
    @ApiModelProperty(value="是否启用")
    private Integer homeBannerStartUsing;
}
