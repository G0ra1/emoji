package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.MaintainPlanDetail;
import com.netwisd.biz.asset.dto.MaintainPlanDetailDto;
import com.netwisd.biz.asset.vo.MaintainPlanDetailVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 维修计划明细 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-04-25 14:07:05
 */
@Mapper
public interface MaintainPlanDetailMapper extends BaseMapper<MaintainPlanDetail> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param maintainPlanDetailDto
     * @return
     */
    Page<MaintainPlanDetailVo> getPageList(Page page, @Param("maintainPlanDetailDto") MaintainPlanDetailDto maintainPlanDetailDto);
}
