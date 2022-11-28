package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.ScrapApplyDetail;
import com.netwisd.biz.asset.dto.ScrapApplyDetailDto;
import com.netwisd.biz.asset.vo.ScrapApplyDetailVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 报废申请详情 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-08-03 14:54:19
 */
@Mapper
public interface ScrapApplyDetailMapper extends BaseMapper<ScrapApplyDetail> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param scrapApplyDetailDto
     * @return
     */
    Page<ScrapApplyDetailVo> getPageList(Page page, @Param("scrapApplyDetailDto") ScrapApplyDetailDto scrapApplyDetailDto);
}
