package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.DisposePlan;
import com.netwisd.biz.asset.dto.DisposePlanDto;
import com.netwisd.biz.asset.vo.DisposePlanVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 处置计划 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-07 17:50:50
 */
@Mapper
public interface DisposePlanMapper extends BaseMapper<DisposePlan> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param disposePlanDto
     * @return
     */
    Page<DisposePlanVo> getPageList(Page page, @Param("disposePlanDto") DisposePlanDto disposePlanDto);
}
