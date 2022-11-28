package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.Assets;
import com.netwisd.biz.asset.dto.AssetsDto;
import com.netwisd.biz.asset.vo.AssetsVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 资产台账 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-21 14:54:51
 */
@Mapper
public interface AssetsMapper extends BaseMapper<Assets> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param assetsDto
     * @return
     */
    Page<AssetsVo> getPageList(Page page, @Param("assetsDto") AssetsDto assetsDto);
}
