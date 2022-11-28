package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.ScrapApply;
import com.netwisd.biz.asset.dto.ScrapApplyDto;
import com.netwisd.biz.asset.vo.ScrapApplyVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 报废申请 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-08-03 14:54:19
 */
@Mapper
public interface ScrapApplyMapper extends BaseMapper<ScrapApply> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param scrapApplyDto
     * @return
     */
    Page<ScrapApplyVo> getPageList(Page page, @Param("scrapApplyDto") ScrapApplyDto scrapApplyDto);
}
