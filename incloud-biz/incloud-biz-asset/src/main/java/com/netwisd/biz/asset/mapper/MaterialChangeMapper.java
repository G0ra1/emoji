package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.MaterialChange;
import com.netwisd.biz.asset.dto.MaterialChangeDto;
import com.netwisd.biz.asset.vo.MaterialChangeVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 资产信息变更 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 17:11:54
 */
@Mapper
public interface MaterialChangeMapper extends BaseMapper<MaterialChange> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param materialChangeDto
     * @return
     */
    Page<MaterialChangeVo> getPageList(Page page, @Param("materialChangeDto") MaterialChangeDto materialChangeDto);
}
