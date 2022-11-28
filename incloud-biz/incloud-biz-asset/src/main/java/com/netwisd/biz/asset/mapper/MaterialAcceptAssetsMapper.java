package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.dto.MaterialAcceptAssetsDto;
import com.netwisd.biz.asset.entity.MaterialAcceptAssets;
import com.netwisd.biz.asset.entity.MaterialDistributeDetail;
import com.netwisd.biz.asset.vo.MaterialAcceptAssetsVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 资产领用资产明细 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:45:59
 */
@Mapper
public interface MaterialAcceptAssetsMapper extends BaseMapper<MaterialAcceptAssets> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param materialAcceptAssetsDto
     * @return
     */
    Page<MaterialAcceptAssetsVo> getPageList(Page page, @Param("materialAcceptAssetsDto") MaterialAcceptAssetsDto materialAcceptAssetsDto);

    List<MaterialDistributeDetail> selectList(LambdaQueryWrapper<MaterialDistributeDetail> queryWrapper);
}
