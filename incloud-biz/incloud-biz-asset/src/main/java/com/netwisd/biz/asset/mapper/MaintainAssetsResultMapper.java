package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.MaintainAssetsResult;
import com.netwisd.biz.asset.dto.MaintainAssetsResultDto;
import com.netwisd.biz.asset.vo.MaintainAssetsResultVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 维修资产明细 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-04-26 14:08:43
 */
@Mapper
public interface MaintainAssetsResultMapper extends BaseMapper<MaintainAssetsResult> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param maintainAssetsResultDto
     * @return
     */
    Page<MaintainAssetsResultVo> getPageList(Page page, @Param("maintainAssetsResultDto") MaintainAssetsResultDto maintainAssetsResultDto);
}
