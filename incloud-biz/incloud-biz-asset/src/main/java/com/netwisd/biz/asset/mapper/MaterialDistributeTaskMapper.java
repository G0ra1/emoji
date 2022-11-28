package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.MaterialDistributeTask;
import com.netwisd.biz.asset.dto.MaterialDistributeTaskDto;
import com.netwisd.biz.asset.vo.MaterialDistributeTaskVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 资产派发任务表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 16:55:47
 */
@Mapper
public interface MaterialDistributeTaskMapper extends BaseMapper<MaterialDistributeTask> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param materialDistributeTaskDto
     * @return
     */
    Page<MaterialDistributeTaskVo> getPageList(Page page, @Param("materialDistributeTaskDto") MaterialDistributeTaskDto materialDistributeTaskDto);
}
