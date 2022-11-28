package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.MaterialSign;
import com.netwisd.biz.asset.dto.MaterialSignDto;
import com.netwisd.biz.asset.vo.MaterialSignVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 签收 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-06-24 11:09:16
 */
@Mapper
public interface MaterialSignMapper extends BaseMapper<MaterialSign> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param materialSignDto
     * @return
     */
    Page<MaterialSignVo> getPageList(Page page, @Param("materialSignDto") MaterialSignDto materialSignDto);
}
