package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.MaintainPlan;
import com.netwisd.biz.asset.dto.MaintainPlanDto;
import com.netwisd.biz.asset.vo.MaintainPlanVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 维修计划 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-04-25 14:07:05
 */
@Mapper
public interface MaintainPlanMapper extends BaseMapper<MaintainPlan> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param maintainPlanDto
     * @return
     */
    Page<MaintainPlanVo> getPageList(Page page, @Param("maintainPlanDto") MaintainPlanDto maintainPlanDto);
    /**
    * 查询当天最大维修计划编号
    */
    String getMaxCode();
}
