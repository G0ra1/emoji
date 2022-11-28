package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.ScrapRegisterDetail;
import com.netwisd.biz.asset.dto.ScrapRegisterDetailDto;
import com.netwisd.biz.asset.vo.ScrapRegisterDetailVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 报废登记详情 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-08-05 10:14:01
 */
@Mapper
public interface ScrapRegisterDetailMapper extends BaseMapper<ScrapRegisterDetail> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param scrapRegisterDetailDto
     * @return
     */
    Page<ScrapRegisterDetailVo> getPageList(Page page, @Param("scrapRegisterDetailDto") ScrapRegisterDetailDto scrapRegisterDetailDto);
}
