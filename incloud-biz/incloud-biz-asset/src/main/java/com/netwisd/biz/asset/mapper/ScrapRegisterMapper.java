package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.ScrapRegister;
import com.netwisd.biz.asset.dto.ScrapRegisterDto;
import com.netwisd.biz.asset.vo.ScrapRegisterVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 报废登记 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-08-05 10:14:01
 */
@Mapper
public interface ScrapRegisterMapper extends BaseMapper<ScrapRegister> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param scrapRegisterDto
     * @return
     */
    Page<ScrapRegisterVo> getPageList(Page page, @Param("scrapRegisterDto") ScrapRegisterDto scrapRegisterDto);
}
