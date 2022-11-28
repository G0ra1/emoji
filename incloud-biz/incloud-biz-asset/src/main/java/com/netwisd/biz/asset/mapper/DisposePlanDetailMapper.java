package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.DisposePlanDetail;
import com.netwisd.biz.asset.dto.DisposePlanDetailDto;
import com.netwisd.biz.asset.vo.DisposePlanDetailVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 处置计划明细 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-07 17:50:50
 */
@Mapper
public interface DisposePlanDetailMapper extends BaseMapper<DisposePlanDetail> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param disposePlanDetailDto
     * @return
     */
    Page<DisposePlanDetailVo> getPageList(Page page, @Param("disposePlanDetailDto") DisposePlanDetailDto disposePlanDetailDto);
}
