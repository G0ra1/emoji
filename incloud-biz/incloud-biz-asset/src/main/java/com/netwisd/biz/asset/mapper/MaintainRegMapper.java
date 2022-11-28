package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.MaintainReg;
import com.netwisd.biz.asset.dto.MaintainRegDto;
import com.netwisd.biz.asset.vo.MaintainRegVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 维修登记 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-04-28 17:12:23
 */
@Mapper
public interface MaintainRegMapper extends BaseMapper<MaintainReg> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param maintainRegDto
     * @return
     */
    Page<MaintainRegVo> getPageList(Page page, @Param("maintainRegDto") MaintainRegDto maintainRegDto);
    /**
    * 获取当天最大编号
    *
    * */
    String getMaxCode();
}
