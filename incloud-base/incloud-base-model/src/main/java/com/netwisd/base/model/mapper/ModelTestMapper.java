package com.netwisd.base.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.model.entity.ModelTest;
import com.netwisd.base.model.dto.ModelTestDto;
import com.netwisd.base.model.vo.ModelTestVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 数据建模测试表 功能描述...
 * @author 云数网讯 sunzhenxi@netwisd.com
 * @date 2021-12-21 10:21:27
 */
@Mapper
public interface ModelTestMapper extends BaseMapper<ModelTest> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param modelTestDto
     * @return
     */
    Page<ModelTestVo> getPageList(Page page, @Param("modelTestDto") ModelTestDto modelTestDto);
}
